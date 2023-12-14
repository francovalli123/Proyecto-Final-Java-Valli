package com.example.demo.controller;


import com.example.demo.model.ProductoModel;
import com.example.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //Obtener todos los productos
    @GetMapping
    public List<ProductoModel> findAll() {
        return this.productoService.findAll();
    }

    //Obtener productos por id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(this.productoService.findById(id), HttpStatus.OK);
    }

    //Crear productos
    @PostMapping("/")
    public ProductoModel create(@RequestBody ProductoModel producto) {
        return this.productoService.create(producto);
    }

    //Actualizar productos ya existentes
    @PutMapping("/{id}")
    public ResponseEntity<ProductoModel> update(@PathVariable Integer id, @RequestBody ProductoModel producto) {
        ProductoModel productoActualizado = productoService.update(id, producto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    //Borrar productos ya existentes
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
