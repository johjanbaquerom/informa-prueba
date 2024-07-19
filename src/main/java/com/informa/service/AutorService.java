package com.informa.service;

import com.informa.dto.AutorDto;

import java.util.List;

public interface AutorService {

    AutorDto addAutor(AutorDto autorDto);
    AutorDto getAutor(Long id);
    List<AutorDto> getAllAutor();
    AutorDto updateAutor(AutorDto autorDto);
    void deleteAutor(Long id);
}
