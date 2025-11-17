package com.universidadcartagena.api_proyect.controller;

import com.universidadcartagena.api_proyect.model.OrdenCompra;
import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.model.EstadoOrdenCompra;
import com.universidadcartagena.api_proyect.model.Proveedor;
import com.universidadcartagena.api_proyect.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    // POST: RF-06.1 Crear una Orden de Compra (Estado PENDIENTE por defecto)
    @PostMapping
    public ResponseEntity<OrdenCompra> crearOrden(@RequestBody OrdenCompra ordenCompra) {
        OrdenCompra nuevaOrden = ordenCompraService.crearOrden(ordenCompra);
        return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED); // 201 Created
    }

    // PUT: RF-06.3 Marcar como RECIBIDA y actualizar stock (Lógica CRÍTICA)
    @PutMapping("/{id}/recibir")
    public ResponseEntity<?> recibirOrden(@PathVariable Long id) {
        try {
            OrdenCompra ordenRecibida = ordenCompraService.recibirOrden(id);
            return new ResponseEntity<>(ordenRecibida, HttpStatus.OK); // 200 OK
        } catch (RuntimeException e) {
            // Captura errores como "Orden no encontrada" o "Orden ya recibida"
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    // GET: RF-06.2 Sugerencia de Compra (Productos por debajo del stock mínimo)
    @GetMapping("/sugerencia")
    public ResponseEntity<List<Producto>> obtenerSugerencia() {
        List<Producto> sugerencias = ordenCompraService.obtenerSugerenciaCompra();
        return new ResponseEntity<>(sugerencias, HttpStatus.OK); // 200 OK
    }

    // GET: RF-06.4 Listar por estado y proveedor
    // Ejemplo: /api/ordenes-compra?estado=PENDIENTE
    @GetMapping
    public ResponseEntity<List<OrdenCompra>> listarOrdenes(
            @RequestParam(value = "estado", required = false) EstadoOrdenCompra estado,
            @RequestParam(value = "proveedorId", required = false) Long proveedorId) {

        // Nota: El servicio solo usa 'estado' en el MVP
        Proveedor proveedor = null;
        if (proveedorId != null) {
            proveedor = new Proveedor();
            proveedor.setId(proveedorId);
        }

        List<OrdenCompra> ordenes = ordenCompraService.listarPorEstadoYProveedor(estado, proveedor);
        return new ResponseEntity<>(ordenes, HttpStatus.OK); // 200 OK
    }
}