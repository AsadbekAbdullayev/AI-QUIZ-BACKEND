package uz.genesis.aiquest.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.admin.dto.projection.ConductedTestProjection;
import uz.genesis.aiquest.admin.repository.AdminConductedTestRepository;
import uz.genesis.aiquest.webserver.models.dto.CustomPage;
import uz.genesis.aiquest.webserver.models.dto.req.PaginationRequest;

@Service
@RequiredArgsConstructor
public class TalentsConductedTestsService {
    private final AdminConductedTestRepository adminConductedTestRepository;

    public CustomPage<ConductedTestProjection> getConductedTestsOfTalents(PaginationRequest paginationRequest, Boolean scoreAsc) {
        Sort.Order orderByDate = paginationRequest.isAscOrder() ? Sort.Order.asc("created_at") : Sort.Order.desc("created_at");
        Sort.Order orderByScore = scoreAsc ? Sort.Order.asc("final_score") : Sort.Order.desc("final_score");
        PageRequest pageRequest = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), Sort.by(orderByScore, orderByDate));
        Page<ConductedTestProjection> conductedTestsPage = adminConductedTestRepository.getConductedTestsList(pageRequest);
        return CustomPage.of(conductedTestsPage, conductedTestsPage.getContent());
    }
}
