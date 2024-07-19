package com.informa.service.impl;

import com.informa.Entity.Autor;
import com.informa.dto.AutorDto;
import com.informa.dto.LibroDto;
import com.informa.repository.AutorRepository;
import com.informa.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public AutorDto addAutor(AutorDto autorDto) {
        Autor autor = new Autor();
        autor.setNombre(autorDto.getNombre());
        autor.setNacionalidad(autorDto.getNacionalidad());

        autor = autorRepository.save(autor);
        return convertirEntidadADto(autor);
    }

    @Override
    public AutorDto getAutor(Long id) {
        Optional<Autor> optionalAutor = autorRepository.findById(id);
        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
            // Forzar la inicialización de la lista de libros
            autor.getLibros().size(); // Esto inicializa la lista de libros
            return convertirEntidadADto(autor);
        }
        return null;
    }


    @Override
    public List<AutorDto> getAllAutor() {
        List<Autor> autores = autorRepository.findAll();
        return autores.stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    @Override
    public AutorDto updateAutor(AutorDto autorDto) {
        Optional<Autor> optionalAutor = autorRepository.findById(autorDto.getId());
        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
            autor.setNombre(autorDto.getNombre());
            autor.setNacionalidad(autorDto.getNacionalidad());
            autor = autorRepository.save(autor);
            return convertirEntidadADto(autor);
        }
        return null;
    }

    @Override
    public void deleteAutor(Long id) {
        autorRepository.deleteById(id);
    }

    private AutorDto convertirEntidadADto(Autor autor) {
        AutorDto autorDto = new AutorDto();
        autorDto.setId(autor.getId());
        autorDto.setNombre(autor.getNombre());
        autorDto.setNacionalidad(autor.getNacionalidad());

        // Convertir la lista de libros
        Set<LibroDto> librosDto = autor.getLibros().stream()
                .map(libro -> {
                    LibroDto libroDto = new LibroDto();
                    libroDto.setId(libro.getId());
                    libroDto.setTitulo(libro.getTitulo());
                    libroDto.setDescripcion(libro.getDescripcion());
                    libroDto.setAutorId(libro.getAutor().getId());
                    return libroDto;
                })
                .collect(Collectors.toSet());
        autorDto.setLibros(librosDto);

        return autorDto;
    }
}
