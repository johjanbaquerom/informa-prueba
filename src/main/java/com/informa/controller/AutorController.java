package com.informa.controller;

import com.informa.dto.AutorDto;
import com.informa.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping("/crear")
    public ResponseEntity<AutorDto> crearAutor(@RequestBody AutorDto autorDto) {
        AutorDto nuevoAutor = autorService.addAutor(autorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAutor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDto> obtenerAutor(@PathVariable Long id) {
        AutorDto autor = autorService.getAutor(id);
        if (autor != null) {
            return ResponseEntity.ok(autor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDto>> obtenerAutores() {
        List<AutorDto> autores = autorService.getAllAutor();
        return ResponseEntity.ok(autores);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<AutorDto> actualizarAutor(@RequestBody AutorDto autorDto) {
        AutorDto autorActualizado = autorService.updateAutor(autorDto);
        if (autorActualizado != null) {
            return ResponseEntity.ok(autorActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }
}
