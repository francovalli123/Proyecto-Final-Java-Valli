package com.example.demo.repository;

import com.example.demo.model.FacturaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<FacturaModel, Integer> {
}
