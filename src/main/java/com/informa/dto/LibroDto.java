package com.informa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroDto {

    private Long id;
    private String titulo;
    private String descripcion;
    private Long autorId;
}
