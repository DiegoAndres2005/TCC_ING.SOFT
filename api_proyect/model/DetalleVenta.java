package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RF-05.2: Relación con el Encabezado de Venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    // RF-05.2: Producto vendido (Relación con Producto, solo para referencia/consulta)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // RF-05.2: Cantidad vendida
    @Column(nullable = false)
    private Integer cantidad;

    // RF-05.2: Precio unitario AL MOMENTO de la venta (importante para reportes)
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // RF-05.2: Subtotal del ítem (cantidad * precio_unitario)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}