
package com.example.demo.service;

import com.example.demo.dto.FacturaDTO;
import com.example.demo.dto.LineaDTO;
import com.example.demo.dto.ProductoDTO;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.model.ClienteModel;
import com.example.demo.model.FacturaModel;
import com.example.demo.model.LineaModel;
import com.example.demo.model.ProductoModel;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.FacturaRepository;
import com.example.demo.repository.LineaRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.wordclock.WordClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FacturaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private LineaRepository lineaRepository;

    @Autowired
    private RestTemplate restTemplate;

    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        // Validar existencia del cliente
        ClienteModel cliente = clienteRepository.findById(facturaDTO.getCliente().getClienteid())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));


        // Inicializar variables para calcular el total y la cantidad de productos
        BigDecimal total = BigDecimal.ZERO;
        int cantidadProductos = 0;

        // Validar existencia de productos y reducir stock
        Set<LineaDTO> lineasDTO = facturaDTO.getLineas();
        Set<LineaModel> lineasModel = new HashSet<>();

        for (LineaDTO lineaDTO : lineasDTO) {
            ProductoModel producto = productoRepository.findById(lineaDTO.getProducto().getProductoid())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

            // Obtener la descripción del producto si la descripción en la línea no está especificada
            String descripcion = (lineaDTO.getDescripcion() != null && !lineaDTO.getDescripcion().isEmpty())
                    ? lineaDTO.getDescripcion()
                    : producto.getDescripcion();

            LineaModel lineaModel = new LineaModel();
            lineaModel.setCantidad(lineaDTO.getCantidad());
            lineaModel.setDescripcion(descripcion);
            lineaModel.setPrecio(producto.getPrecio());
            lineaModel.setProducto(producto);
            lineaModel.setFactura(null);

            lineasModel.add(lineaModel);

            // Calcular total y cantidad de productos
            total = total.add(producto.getPrecio().multiply(BigDecimal.valueOf(lineaDTO.getCantidad())));
            cantidadProductos += lineaDTO.getCantidad();

            // Reducir stock
            producto.setCantidad(producto.getCantidad() - lineaDTO.getCantidad());
            productoRepository.save(producto);
        }

        // Obtener la fecha del servicio REST o, en caso de fallo, usar la fecha actual
        Date fecha = obtenerFecha();

        // Crear la factura
        FacturaModel facturaModel = new FacturaModel();
        facturaModel.setCantidad(cantidadProductos);
        facturaModel.setFecha(fecha);
        facturaModel.setCliente(cliente);
        facturaModel.setTotal(total);

        // Guardar la factura
        facturaModel = facturaRepository.save(facturaModel);

        // Crear y asociar las líneas
        if (!lineasModel.isEmpty()) {
            for (LineaModel lineaModel : lineasModel) {
                lineaModel.setFactura(facturaModel);
                facturaModel.addLinea(lineaModel);
            }

            // Actualizar la factura con las líneas asociadas
            facturaRepository.save(facturaModel);

            // Actualizar el stock después de guardar la factura (fuera del bucle)
            actualizarStock(lineasModel);
        } else {
            // Manejar el caso en el que no hay líneas (opcional, según tus requisitos)
            System.out.println("No hay líneas en la factura");
        }

        // Convertir la factura a DTO y retornarla
        return convertirFacturaModelADTO(facturaModel);

    }

    private Date obtenerFecha() {
        WordClock worldClock = restTemplate.getForObject("http://worldclockapi.com/api/json/utc/now", WordClock.class);

        String currentDateTime = worldClock.getCurrentDateTime();

        try {
            // Parse the date string using the appropriate format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.parse(currentDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            // If parsing fails, return the current date
            return new Date();
        }
    }


    private FacturaDTO convertirFacturaModelADTO(FacturaModel facturaModel) {
        FacturaDTO facturaDTO = new FacturaDTO();
        facturaDTO.setFacturaid(facturaModel.getFacturaid());
        facturaDTO.setCantidad(facturaModel.getCantidad());
        facturaDTO.setFecha(facturaModel.getFecha());
        facturaDTO.setTotal(facturaModel.getTotal());
        facturaDTO.setCliente(facturaModel.getCliente());
        facturaDTO.setLineas(convertirLineasModelADTO(facturaModel.getLineas()));
        return facturaDTO;
    }

    private Set<LineaDTO> convertirLineasModelADTO(Set<LineaModel> lineasModel) {
        Set<LineaDTO> lineasDTO = new HashSet<>();
        for (LineaModel lineaModel : lineasModel) {
            LineaDTO lineaDTO = new LineaDTO();
            lineaDTO.setLineaid(lineaModel.getLineaid());
            lineaDTO.setCantidad(lineaModel.getCantidad());
            lineaDTO.setDescripcion(lineaModel.getDescripcion());
            lineaDTO.setPrecio(lineaModel.getPrecio());

            lineasDTO.add(lineaDTO);
        }
        return lineasDTO;
    }


    public List<FacturaDTO> getAllFacturas() {
        List<FacturaModel> facturas = facturaRepository.findAll();
        return convertirFacturasModelADTO(facturas);
    }

    public FacturaDTO getFacturaById(Integer id) {
        FacturaModel facturaModel = facturaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Factura no encontrada con ID: " + id));

        // Cargar las líneas junto con la factura
        facturaModel.getLineas().size();  // Esto carga las líneas si aún no están cargadas

        return convertirFacturaModelADTO(facturaModel);
    }
    // ... (otros métodos existentes)

    private List<FacturaDTO> convertirFacturasModelADTO(List<FacturaModel> facturasModel) {
        List<FacturaDTO> facturasDTO = new ArrayList<>();
        for (FacturaModel facturaModel : facturasModel) {
            facturasDTO.add(convertirFacturaModelADTO(facturaModel));
        }
        return facturasDTO;
    }

    private void actualizarStock(Set<LineaModel> lineasModel) {
        Set<ProductoModel> productosActualizados = new HashSet<>();

        for (LineaModel lineaModel : lineasModel) {
            ProductoModel producto = lineaModel.getProducto();

            // Verificar si ya se ha actualizado el stock para este producto
            if (!productosActualizados.contains(producto)) {
                producto.setCantidad(producto.getCantidad() - lineaModel.getCantidad());
                productoRepository.save(producto);

                // Agregar el producto al conjunto de productos actualizados
                productosActualizados.add(producto);
            }
        }
    }
}

