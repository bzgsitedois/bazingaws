package com.bazinga.bases;

public record MetadadosResponse(
        long totalElements,
        int total,
        int perPage,
        int currentPage,
        int nextPage,
        int previusPage,
        String currentPageUrl,
        String firsPageUrl,
        String lastPageUrl,
        String nextPageUrl,
        String previousPageUrl
) {
}
