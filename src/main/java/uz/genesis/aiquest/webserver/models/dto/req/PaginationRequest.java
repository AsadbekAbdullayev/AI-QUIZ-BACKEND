package uz.genesis.aiquest.webserver.models.dto.req;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {
    private int page;

    private int size = 10;

    private String search;

    private LocalDate fromDate;

    private LocalDate toDate;

    private boolean ascOrder = false;

    public static PaginationRequest of(Integer size, Integer page) {
        return new PaginationRequest(page, size, null, null , null, false);
    }

    public int getPage() {
        return Math.max(page, 0);
    }

    public Pageable getPageRequest() {
        var sort = Sort.by(ascOrder? Direction.ASC: Direction.DESC,"createdAt");
        return PageRequest.of(getPage(), getSize(),sort);
    }

    public Pageable getPageRequest(Sort customSort) {
        return PageRequest.of(getPage(), getSize(),customSort);
    }

    public Pageable getPageRequestForNativeQuery() {
        var sort = Sort.by(ascOrder? Direction.ASC: Direction.DESC,"created_at");
        return PageRequest.of(getPage(), getSize(),sort);
    }
}
