package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Entidad que registra el movimiento de salida de productos (ventas, mermas, etc.).
 * Este registro disminuye el campo 'stock' en la entidad Producto.
 */
@Entity
@Table(name = "salida_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalidaInventario {

    // Enumeración interna para el tipo de salida (Mejora en el código limpio)
    public enum TipoSalida {
        VENTA, MERMA, VENCIMIENTO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave Primaria (PK)

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDateTime fechaSalida = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSalida tipo; // El tipo de salida (Venta, Merma, etc.)

    @Column(precision = 10, scale = 2)
    private BigDecimal precioSalida; // Precio al que salió (si es venta)

    // RELACIÓN: Una salida corresponde a un solo producto (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}