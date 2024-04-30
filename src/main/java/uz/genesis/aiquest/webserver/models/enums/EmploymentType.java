package uz.genesis.aiquest.webserver.models.enums;

public enum EmploymentType {
    FULL_TIME("Full time"), PART_TIME("Part time"), FREELANCE("Freelance"), CONTRACT("Contract");


    private final String title;


    EmploymentType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static EmploymentType getEmploymentType(String employmentType) {
        if (employmentType.toLowerCase().contains("full"))
            return FULL_TIME;
        else if (employmentType.toLowerCase().contains("part"))
            return PART_TIME;
        else if (employmentType.toLowerCase().contains("contract"))
            return CONTRACT;
        else if (employmentType.toLowerCase().contains("freelance"))
            return FREELANCE;

        return null;

    }
}
