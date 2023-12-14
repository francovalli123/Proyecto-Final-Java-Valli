package com.example.demo.controller;


import com.example.demo.model.ClienteModel;
import com.example.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //Obtener todos los clientes
    @GetMapping
    public List<ClienteModel> findAll() {
        return this.clienteService.findAll();
    }

    //Obtener clientes por id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(this.clienteService.findById(id), HttpStatus.OK);
    }

    //Crear clientes
    @PostMapping("/")
    public ClienteModel create(@RequestBody ClienteModel cliente) {
        return this.clienteService.create(cliente);
    }

    //Modificar clientes
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> update(@PathVariable Integer id, @RequestBody ClienteModel cliente) {
        ClienteModel clienteActualizado = clienteService.update(id, cliente);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    //Borrar clientes ya existentes
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
