package uz.genesis.aiquest.webserver.commons.baseservice;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;
import uz.genesis.aiquest.webserver.repository.BaseRepository;

@Service
@Log4j2
public abstract class GenericService<E, ID, DTO extends BaseDTO<ID>, R extends BaseRepository<E, ID>, T extends GenericMapper<E, DTO>>
        extends FindableGenericService<E, ID, DTO, R, T> {

    protected final R repository;

    protected final T mapper;
    protected final Class<E> entityTypeClass;

    public GenericService(R repository, T mapper, Class<E> entityTypeClass) {
        super(repository, mapper, entityTypeClass);
        this.repository = repository;
        this.mapper = mapper;
        this.entityTypeClass = entityTypeClass;
    }

    public DTO create(DTO dto) {
        E entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public DTO edit(DTO dto, ID id) {
        E entity = findById(id);
        mapper.updateEntityFromDto(entity, dto);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(ID id) {
        if (this instanceof Deletable) {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                log.error(e);
                throw new GeneralApiException("You cant delete this");
            }
        } else {
            throw new GeneralApiException("You cant delete this");
        }
    }
}
