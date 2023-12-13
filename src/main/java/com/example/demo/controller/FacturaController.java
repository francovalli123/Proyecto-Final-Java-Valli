package com.example.demo.controller;

import com.example.demo.dto.FacturaDTO;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    //Obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        List<FacturaDTO> facturas = facturaService.getAllFacturas();
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    //Obtener facturas por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getFacturaById(@PathVariable Integer id) {
        try {
            FacturaDTO factura = facturaService.getFacturaById(id);
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //Crear facturas
    @PostMapping("/")
    public ResponseEntity<?> createFactura(@RequestBody FacturaDTO facturaDTO) {
        try {
            FacturaDTO createdFactura = facturaService.createFactura(facturaDTO);
            return new ResponseEntity<>(createdFactura, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
