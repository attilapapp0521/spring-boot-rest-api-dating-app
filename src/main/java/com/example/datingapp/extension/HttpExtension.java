package com.example.datingapp.extension;

import com.example.datingapp.helpers.PaginationHeader;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpHeaders;


public class HttpExtension {


    public static void addPaginationHeader(HttpHeaders httpHeaders, int currentPage,
                                           int itemsPerPage, long totalItems,
                                           int totalPages) {

        PaginationHeader paginationHeader = new PaginationHeader(currentPage, itemsPerPage,
                totalItems, totalPages);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
        Gson gson = gsonBuilder.create();

        httpHeaders.add("Pagination", gson.toJson(paginationHeader));
        httpHeaders.add("Access-Control-Expose-Hearers", "Pagination");
    }
}
