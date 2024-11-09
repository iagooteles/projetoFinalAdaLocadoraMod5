package com.projetoFinalWeb.projetoWeb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
public class ResponsePagingDTO<T> {

    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<T> list;
    private int pageNumber;
    private long offset;

}
