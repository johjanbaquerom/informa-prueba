package com.informa;

import com.informa.Entity.Autor;
import com.informa.dto.AutorDto;
import com.informa.repository.AutorRepository;
import com.informa.service.impl.AutorServiceImpl;
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
public class AutorServiceImplTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorServiceImpl autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAutor() {
        AutorDto autorDto = new AutorDto();
        autorDto.setNombre("Test Author");
        autorDto.setNacionalidad("Test Nationality");

        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Test Author");
        autor.setNacionalidad("Test Nationality");
        autor.setLibros(new HashSet<>());

        when(autorRepository.save(any(Autor.class))).thenAnswer(invocation -> {
            Autor savedAutor = invocation.getArgument(0);
            savedAutor.setId(1L);
            return savedAutor;
        });

        AutorDto createdAutor = autorService.addAutor(autorDto);

        assertNotNull(createdAutor);
        assertEquals("Test Author", createdAutor.getNombre());
        verify(autorRepository, times(1)).save(any(Autor.class));
    }

    @Test
    void testGetAutor() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Test Author");
        autor.setNacionalidad("Test Nationality");
        autor.setLibros(new HashSet<>());

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        AutorDto autorDto = autorService.getAutor(1L);

        assertNotNull(autorDto);
        assertEquals("Test Author", autorDto.getNombre());
        verify(autorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllAutor() {
        List<Autor> autores = new ArrayList<>();
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Test Author");
        autor.setNacionalidad("Test Nationality");
        autor.setLibros(new HashSet<>());
        autores.add(autor);

        when(autorRepository.findAll()).thenReturn(autores);

        List<AutorDto> autorDtos = autorService.getAllAutor();

        assertFalse(autorDtos.isEmpty());
        assertEquals(1, autorDtos.size());
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void testUpdateAutor() {
        AutorDto autorDto = new AutorDto();
        autorDto.setId(1L);
        autorDto.setNombre("Updated Author");
        autorDto.setNacionalidad("Updated Nationality");

        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Test Author");
        autor.setNacionalidad("Test Nationality");
        autor.setLibros(new HashSet<>());

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(autorRepository.save(any(Autor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AutorDto updatedAutor = autorService.updateAutor(autorDto);

        assertNotNull(updatedAutor);
        assertEquals("Updated Author", updatedAutor.getNombre());
        verify(autorRepository, times(1)).findById(1L);
        verify(autorRepository, times(1)).save(any(Autor.class));
    }

    @Test
    void testDeleteAutor() {
        doNothing().when(autorRepository).deleteById(1L);

        autorService.deleteAutor(1L);

        verify(autorRepository, times(1)).deleteById(1L);
    }
}
