package uz.genesis.aiquest.webserver.commons.basecontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.genesis.aiquest.webserver.commons.baseservice.GenericService;
import uz.genesis.aiquest.webserver.models.dto.CustomPage;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.models.dto.req.PaginationRequest;
import uz.genesis.aiquest.webserver.repository.BaseRepository;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

import java.util.List;

@RequiredArgsConstructor
public abstract class GenericController<E, ID, DTO extends BaseDTO<ID>, R extends BaseRepository<E, ID>, T extends GenericMapper<E, DTO>> {

    protected  final GenericService<E, ID, DTO, R, T> service;

    @PostMapping
    public Header<DTO> create(@RequestBody @Valid Header<DTO> dto) {
        return Header.ok(service.create(dto.getData()));
    }

    @PutMapping
    public Header<DTO> edit(@RequestBody @Valid Header<DTO> dto) {
        return Header.ok(service.edit(dto.getData(), dto.getData().getId()));
    }

    @DeleteMapping("/{id}")
    public Header<?> delete(@PathVariable ID id) {
        service.delete(id);
        return Header.ok();
    }

    @GetMapping("/{id}")
    public Header<DTO> getById(@PathVariable ID id) {
        return Header.ok(service.get(id));
    }

    @GetMapping("/all")
    public Header<List<DTO>> getList() {
        return Header.ok(service.getList());
    }

    @GetMapping
    public Header<List<DTO>> getListPagination(PaginationRequest request) {
        CustomPage<DTO> page = service.getListPagination(request);
        return Header.ok(page.getData(), page.getPaginationData());
    }

    @GetMapping(value = "/filter")
    public Header<List<DTO>> getByFilter(E entity, PaginationRequest paginationRequest) {
        CustomPage<DTO> page = service.getFiltered( paginationRequest, entity);
        return Header.ok(page.getData(), page.getPaginationData());
    }
}
