package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_orden_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el Encabezado de la Orden
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenCompra ordenCompra;

    // RF-06.1: Producto a comprar
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // RF-06.1: Cantidad a comprar
    @Column(nullable = false)
    private Integer cantidad;

    // RF-06.1: Precio unitario de compra (fijado al momento de la orden)
    @Column(name = "precio_unitario_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitarioCompra;

    // Subtotal del ítem (cantidad * precio_unitario_compra)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}