package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que registra el movimiento de entrada de productos (compras a proveedor).
 * Este registro aumenta el campo 'stock' en la entidad Producto.
 */
@Entity
@Table(name = "entrada_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntradaInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave Primaria (PK)

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDateTime fechaEntrada = LocalDateTime.now(); // Marca de tiempo del registro.

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costoUnitario;

    // RELACIÓN: Una entrada corresponde a un solo producto (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // RELACIÓN: Una entrada se asocia a un proveedor (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;
}
