package com.projetoFinalWeb.projetoWeb.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePagingDTO<T> {

    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private List<T> list;
    private int pageNumber;
    private long offset;

}
