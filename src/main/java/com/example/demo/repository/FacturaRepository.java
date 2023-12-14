package com.example.demo.repository;

import com.example.demo.model.ClienteModel;
import com.example.demo.model.FacturaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;

public interface FacturaRepository extends JpaRepository<FacturaModel, Integer> {

    @Transactional
    void deleteByCliente(ClienteModel cliente);
}
