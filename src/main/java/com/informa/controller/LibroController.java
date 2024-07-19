package com.informa.controller;

import com.informa.dto.LibroDto;
import com.informa.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping("/crear")
    public ResponseEntity<LibroDto> crearLibro(@RequestBody LibroDto libroDto) {
        LibroDto nuevoLibro = libroService.addLibro(libroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLibro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDto> obtenerLibro(@PathVariable Long id) {
        LibroDto libro = libroService.getLibro(id);
        if (libro != null) {
            return ResponseEntity.ok(libro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LibroDto>> obtenerLibros() {
        List<LibroDto> libros = libroService.getLibros();
        return ResponseEntity.ok(libros);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<LibroDto> actualizarLibro(@RequestBody LibroDto libroDto) {
        LibroDto libroActualizado = libroService.updateLibro(libroDto);
        if (libroActualizado != null) {
            return ResponseEntity.ok(libroActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }
}
