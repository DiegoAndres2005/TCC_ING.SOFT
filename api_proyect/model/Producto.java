package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad principal que gestiona el inventario y stock de la tienda.
 * Contiene el stock en tiempo real y precios.
 */
@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave Primaria (PK)

    @Column(nullable = false, unique = true)
    private String codigoBarra; // Código único para identificación rápida

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Integer stock = 0; // Cantidad disponible. Inicializado en 0.

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta; // Usamos BigDecimal para precisión monetaria.

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costoCompra;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo = 5;

    private LocalDate fechaVencimiento;

    // RELACIÓN: Muchos productos pertenecen a una sola categoría (N:1)
    // El @JoinColumn define la Clave Foránea (FK) 'categoria_id' en esta tabla.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaProducto categoria;

    // RELACIÓN: Muchos productos son suministrados por un proveedor (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;
}