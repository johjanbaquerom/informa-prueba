package com.informa.service.impl;

import com.informa.Entity.Autor;
import com.informa.Entity.Libro;
import com.informa.dto.LibroDto;
import com.informa.repository.AutorRepository;
import com.informa.repository.LibroRepository;
import com.informa.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public LibroDto addLibro(LibroDto libroDto) {
        Libro libro = new Libro();
        libro.setTitulo(libroDto.getTitulo());
        libro.setDescripcion(libroDto.getDescripcion());

        Optional<Autor> optionalAutor = autorRepository.findById(libroDto.getAutorId());
        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
            libro.setAutor(autor);
            libro = libroRepository.save(libro);

            // Actualizar la lista de libros del autor
            autor.getLibros().add(libro);
            autorRepository.save(autor);

            return convertirEntidadADto(libro);
        } else {
            throw new RuntimeException("Autor no encontrado con ID: " + libroDto.getAutorId());
        }
    }

    @Override
    public LibroDto getLibro(Long id) {
        Optional<Libro> optionalLibro = libroRepository.findById(id);
        return optionalLibro.map(this::convertirEntidadADto).orElse(null);
    }

    @Override
    public List<LibroDto> getLibros() {
        List<Libro> libros = libroRepository.findAll();
        return libros.stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    @Override
    public LibroDto updateLibro(LibroDto libroDto) {
        Long id = libroDto.getId();
        if (id != null) {
            Optional<Libro> optionalLibro = libroRepository.findById(id);
            if (optionalLibro.isPresent()) {
                Libro libro = optionalLibro.get();
                libro.setTitulo(libroDto.getTitulo());
                libro.setDescripcion(libroDto.getDescripcion());
                libro = libroRepository.save(libro);
                return convertirEntidadADto(libro);
            }
        }
        return null;
    }

    @Override
    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }

    private LibroDto convertirEntidadADto(Libro libro) {
        LibroDto libroDto = new LibroDto();
        libroDto.setId(libro.getId());
        libroDto.setTitulo(libro.getTitulo());
        libroDto.setDescripcion(libro.getDescripcion());
        if (libro.getAutor() != null) {
            libroDto.setAutorId(libro.getAutor().getId());
        }
        return libroDto;
    }
}
