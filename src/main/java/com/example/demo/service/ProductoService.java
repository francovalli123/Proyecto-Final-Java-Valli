package com.example.demo.service;

import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.model.ProductoModel;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //Get
    public List<ProductoModel> findAll() {
        return this.productoRepository.findAll();
    }

    //Post
    public ProductoModel create(ProductoModel producto) {
        return this.productoRepository.save(producto);
    }

    //Get por id
    public ProductoModel findById(Integer productoid) {
        if (productoid == null || productoid <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<ProductoModel> opProducto = productoRepository.findById(productoid);

        if (opProducto.isPresent()) {
            return opProducto.get();
        } else {
            throw new RecursoNoEncontradoException("Producto no encontrado con ID: " + productoid);
        }
    }

    //Update, no se modifican las facturas ya existentes
    public ProductoModel update(Integer productoid, ProductoModel productoActualizado) {
        if (productoid == null || productoid <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<ProductoModel> opProducto = productoRepository.findById(productoid);

        if (opProducto.isPresent()) {
            ProductoModel productoExistente = opProducto.get();
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setCantidad(productoActualizado.getCantidad());

            return productoRepository.save(productoExistente);
        } else {
            throw new RecursoNoEncontradoException("Producto no encontrado con ID: " + productoid);
        }
    }

    //Delete, no se borran las facturas ya existentes
    public void delete(Integer productoid) {
        if (productoid == null || productoid <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Optional<ProductoModel> opProducto = productoRepository.findById(productoid);

        if (opProducto.isPresent()) {
            productoRepository.deleteById(productoid);
        } else {
            throw new RecursoNoEncontradoException("Producto no encontrado con ID: " + productoid);
        }
    }


}

