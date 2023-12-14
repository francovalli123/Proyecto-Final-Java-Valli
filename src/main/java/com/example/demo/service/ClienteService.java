package com.example.demo.service;

import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.model.ClienteModel;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    //Get
    public List<ClienteModel> findAll() {
        return this.clienteRepository.findAll();
    }

    //Get por id
    public ClienteModel findById(Integer id) {
        //Los errores se muestran en consola
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<ClienteModel> opCliente = clienteRepository.findById(id);

        if (opCliente.isPresent()) {
            return opCliente.get();
        } else {
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id);
        }
    }

    //Post
    public ClienteModel create(ClienteModel cliente){
        //Validaciones
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente proporcionado no puede ser nulo.");
        }

        if (cliente.getDni() == null || cliente.getDni() <= 0) {
            throw new IllegalArgumentException("El DNI del cliente no es válido.");
        }

        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }

        return clienteRepository.save(cliente);
    }

    //Update
    public ClienteModel update(Integer id, ClienteModel clienteActualizado) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<ClienteModel> opCliente = clienteRepository.findById(id);

        if (opCliente.isPresent()) {
            ClienteModel clienteExistente = opCliente.get();
            clienteExistente.setDni(clienteActualizado.getDni());
            clienteExistente.setNombre(clienteActualizado.getNombre());

            return clienteRepository.save(clienteExistente);
        } else {
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id);
        }
    }

    //Delete
    @Transactional
    public void delete(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<ClienteModel> opCliente = clienteRepository.findById(id);

        if (opCliente.isPresent()) {
            ClienteModel cliente = opCliente.get();
            facturaRepository.deleteByCliente(cliente);
            clienteRepository.deleteById(id);
        } else {
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id);
        }
    }

}



