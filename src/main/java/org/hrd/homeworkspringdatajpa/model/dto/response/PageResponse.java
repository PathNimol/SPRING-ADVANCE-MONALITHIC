package org.hrd.homeworkspringdatajpa.model.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private Pagination pagination;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pagination {
        private long totalElements;
        private int currentPage;
        private int pageSize;
        private int totalPages;
    }
}
