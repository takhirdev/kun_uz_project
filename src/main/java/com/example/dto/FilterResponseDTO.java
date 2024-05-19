package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class FilterResponseDTO<T> {
    private List<T> content;
    private Long totalCount;
}
