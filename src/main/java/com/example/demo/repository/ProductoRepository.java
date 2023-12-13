package com.example.demo.repository;

import com.example.demo.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoModel, Integer> {
}
