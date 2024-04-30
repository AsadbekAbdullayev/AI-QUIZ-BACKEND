package uz.genesis.aiquest.admin.component;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.genesis.aiquest.webserver.models.entity.Question;
import uz.genesis.aiquest.webserver.models.entity.QuestionOption;
import uz.genesis.aiquest.webserver.repository.QuestionRepository;
import uz.genesis.aiquest.webserver.models.enums.QuestionLanguage;
import uz.genesis.aiquest.webserver.models.enums.QuestionType;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class QuestionsImportExcelService {

    private final QuestionRepository questionRepository;

    private final byte[][] GREEN_COLOR_CODES = {
            {106, -88, 79},
            {39, 78, 19},
            {56, 118, 29},
            {-109, -60, 125}
    };


    public void importFromExcel(MultipartFile multipartFile, QuestionLanguage questionLanguage, Long subDirectionId, TestType testType) throws IOException {
        if (!multipartFile.getOriginalFilename().endsWith("xlsx")) {
            return;
        }
        final var excelFileStream = multipartFile.getInputStream();
        Workbook workbook = new XSSFWorkbook(excelFileStream);
        List<Question> questions = new ArrayList<>();
        var firstSheet = workbook.getSheetAt(0);
        int index = 0;
        for (Row row : firstSheet) {
            if (index == 0) {
                index++;
                continue;
            }


            if (row.getCell(0) == null) {
                break;
            }
            processEachRow(row, questionLanguage, subDirectionId, testType).filter(question -> question.getQuestionOptions().size() >= 4).ifPresent(questions::add);
            index++;
        }

        System.out.println(index);
        questionRepository.saveAll(questions);
    }

    private Optional<Question> processEachRow(Row row, QuestionLanguage questionLanguage, Long subDirectionId, TestType testType) {
        int index = 0;
        Question question = new Question();
        List<QuestionOption> questionOptions = new ArrayList<>();
        for (Cell cell : row) {

            if (index == 0) {
                index++;
                continue;
            }
            question.setQuestionLanguage(questionLanguage);
            question.setDirectionId(subDirectionId);
            question.setForTestType(testType);
            if (!cell.getCellType().equals(CellType.BLANK) && Objects.nonNull(checkCellTypeAndGetValue(cell))) {

                switch (index) {
                    case 1 -> question.setQuestionCaption(checkCellTypeAndGetValue(cell));
                    case 2 -> question.setCodeExample(checkCellTypeAndGetValue(cell));
                    case 3, 4, 5, 6 -> {
                        var color = (XSSFColor) cell.getCellStyle().getFillForegroundColorColor();
                        byte[] rgb = null;
                        if (color != null)
                            rgb = color.getRGB();
                        QuestionOption questionOption = new QuestionOption();
                        questionOption.setQuestion(question);
                        questionOption.setOptionCaption(checkCellTypeAndGetValue(cell));
                        questionOption.setIsCorrect(false);
                        for (byte[] greenColorCode : GREEN_COLOR_CODES) {
                            if (Arrays.equals(greenColorCode, rgb)) {
                                questionOption.setIsCorrect(true);
                            }
                        }
                        questionOptions.add(questionOption);
                    }
                }
            }

            index++;
        }
        question.setQuestionOptions(questionOptions);
        var questionType = detectQuestionType(question.getQuestionOptions());
        if (questionType == null) {
            //we dont add this question because this question no correct answer exists
            return Optional.empty();
        }
        question.setQuestionType(questionType);
        return Optional.of(question);
    }

    private String checkCellTypeAndGetValue(Cell cell) {
        return switch (cell.getCellType()) {
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case STRING -> cell.getStringCellValue();
            case BLANK, _NONE, FORMULA, BOOLEAN, ERROR -> "";
        };
    }

    private QuestionType detectQuestionType(List<QuestionOption> questionOptions) {
        var count = questionOptions.stream()
                .filter(QuestionOption::getIsCorrect)
                .count();
        if (count == 1) return QuestionType.ONE_CHOICE;
        else if (count >= 2) return QuestionType.MULTIPLE_CHOICE;

        return null;
    }
}
