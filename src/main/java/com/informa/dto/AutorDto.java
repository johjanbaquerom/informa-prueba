package com.informa.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorDto {

    private Long id;
    private String nombre;
    private String nacionalidad;
    private Set<LibroDto> libros;
}
