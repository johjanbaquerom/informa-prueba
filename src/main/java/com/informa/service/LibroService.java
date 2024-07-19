package com.informa.service;

import com.informa.dto.LibroDto;

import java.util.List;

public interface LibroService {

    LibroDto addLibro(LibroDto libroDto);
    LibroDto getLibro(Long id);
    List<LibroDto> getLibros();
    LibroDto updateLibro(LibroDto libroDto);
    void deleteLibro(Long id);
}
