package com.informa;

import com.informa.Entity.Autor;
import com.informa.Entity.Libro;
import com.informa.dto.LibroDto;
import com.informa.repository.AutorRepository;
import com.informa.repository.LibroRepository;
import com.informa.service.impl.LibroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLibro() {
        LibroDto libroDto = new LibroDto();
        libroDto.setTitulo("Test Title");
        libroDto.setDescripcion("Test Description");
        libroDto.setAutorId(1L);

        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Test Author");
        autor.setNacionalidad("Test Nationality");
        autor.setLibros(new HashSet<>());

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(libroRepository.save(any(Libro.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LibroDto createdLibro = libroService.addLibro(libroDto);

        assertNotNull(createdLibro);
        assertEquals("Test Title", createdLibro.getTitulo());
        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    @Test
    void testGetLibro() {
        Libro libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Test Title");
        libro.setDescripcion("Test Description");
        libro.setAutor(new Autor());

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        LibroDto libroDto = libroService.getLibro(1L);

        assertNotNull(libroDto);
        assertEquals("Test Title", libroDto.getTitulo());
        verify(libroRepository, times(1)).findById(1L);
    }

    @Test
    void testGetLibros() {
        List<Libro> libros = new ArrayList<>();
        Libro libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Test Title");
        libro.setDescripcion("Test Description");
        libro.setAutor(new Autor());
        libros.add(libro);

        when(libroRepository.findAll()).thenReturn(libros);

        List<LibroDto> libroDtos = libroService.getLibros();

        assertFalse(libroDtos.isEmpty());
        assertEquals(1, libroDtos.size());
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    void testUpdateLibro() {
        LibroDto libroDto = new LibroDto();
        libroDto.setId(1L);
        libroDto.setTitulo("Updated Title");
        libroDto.setDescripcion("Updated Description");

        Libro libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Test Title");
        libro.setDescripcion("Test Description");
        libro.setAutor(new Autor());

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LibroDto updatedLibro = libroService.updateLibro(libroDto);

        assertNotNull(updatedLibro);
        assertEquals("Updated Title", updatedLibro.getTitulo());
        verify(libroRepository, times(1)).findById(1L);
        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    @Test
    void testDeleteLibro() {
        doNothing().when(libroRepository).deleteById(1L);

        libroService.deleteLibro(1L);

        verify(libroRepository, times(1)).deleteById(1L);
    }
}
