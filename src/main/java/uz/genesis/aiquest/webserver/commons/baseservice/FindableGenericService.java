package uz.genesis.aiquest.webserver.commons.baseservice;



import jakarta.persistence.Entity;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.commons.annotations.SearchableField;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.dto.CustomPage;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.models.dto.req.PaginationRequest;
import uz.genesis.aiquest.webserver.repository.BaseRepository;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntitySequentialID;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;


@Service
@AllArgsConstructor
@Log4j2
public abstract class FindableGenericService<E, ID, DTO extends BaseDTO<ID>, R extends BaseRepository<E, ID>, T extends GenericMapper<E, DTO>> {

    protected final R repository;

    protected final T mapper;

    protected final Class<E> entityTypeClass;

    public E findById(ID id) {
        return repository.findById(id).orElseThrow(() ->
                new GeneralApiException(ErrorMessages.notFound(entityTypeClass.getSimpleName())));
    }


    public DTO get(ID id) {
        final E entity = findById(id);
        return mapper.toDto(entity);
    }


    public List<DTO> getList() {
        return mapper.toDtoList(repository.findAll());
    }


    public CustomPage<DTO> getListPagination(PaginationRequest paginationRequest) {
        String keyword = paginationRequest.getSearch();
        if (Objects.nonNull(keyword)) {
            Specification<E> spec = getSearchable(keyword);
            return getPage(spec, paginationRequest);
        } else {
            return getPage(paginationRequest);
        }
    }



    private CustomPage<DTO> getPage(PaginationRequest request) {
        var savedPage = repository.findAll(isDataBetweenFromAndToDate(request.getFromDate(), request.getToDate()), request.getPageRequest());
        return CustomPage.of(savedPage, mapper.toDtoList(savedPage.getContent()));
    }

    private CustomPage<DTO> getPage(Specification<E> specification, PaginationRequest request) {
        Pageable pageable = request.getPageRequest();
        var savedPage = repository.findAll(specification, pageable);
        return CustomPage.of(savedPage, mapper.toDtoList(savedPage.getContent()));
    }

    protected Specification<E> getSearchable(String keyword) {
        Field[] fields = entityTypeClass.getDeclaredFields();
        return (root, query, builder) -> {
            List<Predicate> joinPredicates = new ArrayList<>();
            Arrays.stream(fields).forEach(
                    field -> {
                        SearchableField fieldAnnotation = field.getAnnotation(SearchableField.class);
                        if (Objects.nonNull(fieldAnnotation) && fieldAnnotation.value()) {
                            boolean isTable = false;
                            for (Annotation annotation : field.getType().getAnnotations()) {
                                if (annotation.annotationType().equals(Entity.class)) {
                                    isTable = true;
                                }
                            }
                            if (isTable) {
                                if (!fieldAnnotation.innerSearField().isEmpty() || !fieldAnnotation.innerSearField().isBlank()) {
                                    Join<Object, Object> join = root.join(field.getName(), JoinType.INNER);
                                    joinPredicates.add(builder.like(builder.lower(join.get(fieldAnnotation.innerSearField())), "%" + keyword.toLowerCase() + "%"));
                                }
                            } else {
                                joinPredicates.add(builder.like(builder.lower(root.get(field.getName())), "%" + keyword.toLowerCase() + "%"));
                            }
                        }
                    }
            );
            return joinPredicates.isEmpty() ? builder.disjunction() : builder.or(joinPredicates.toArray(new Predicate[]{}));
        };
    }

    public CustomPage<DTO> getFiltered(PaginationRequest paginationRequest, E entity) {
        if (Objects.nonNull(paginationRequest.getSearch())) {
            Page<E> searchedResult = repository.findAll(getSearchable(paginationRequest.getSearch()), paginationRequest.getPageRequest());
            return CustomPage.of(searchedResult, mapper.toDtoList(searchedResult.getContent()));
        }
        Example<E> example = Example.of(entity, ExampleMatcher.matchingAll().withIgnoreNullValues());
        Page<E> filtered = repository.findAll(example,paginationRequest.getPageRequest());
        return CustomPage.of(filtered,mapper.toDtoList(filtered.getContent()));
    }


    private Specification<E> isDataBetweenFromAndToDate(LocalDate from, LocalDate to) {
        return (root, query, criteriaBuilder) -> {
            var truePredicate = criteriaBuilder.conjunction();
            //if to date is given but from date is not
            if (Objects.isNull(from) && Objects.nonNull(to)) {
                return isLessThanTo(to, root, criteriaBuilder);
            } else if (Objects.isNull(to) && Objects.nonNull(from)) {   //if to date is NOT given but from date IS
                return isGreaterThanFrom(from, root, criteriaBuilder);
            } else if (Objects.nonNull(from)) {
                return criteriaBuilder.and(isGreaterThanFrom(from, root, criteriaBuilder), isLessThanTo(to, root, criteriaBuilder));
            }
            return truePredicate;
        };
    }

    private Predicate isGreaterThanFrom(LocalDate from, Root<E> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEntitySequentialID._createdAt).as(Timestamp.class), Timestamp.valueOf(from.atStartOfDay()));
    }

    private Predicate isLessThanTo(LocalDate to, Root<E> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(root.get(BaseEntitySequentialID._createdAt).as(Timestamp.class), Timestamp.valueOf(to.atStartOfDay()));
    }
}
