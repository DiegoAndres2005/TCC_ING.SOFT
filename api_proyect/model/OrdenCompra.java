package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orden_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RF-06.1: Proveedor (Relación N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    // RF-06.1: Fecha de la orden
    @Column(name = "fecha_orden", nullable = false)
    private LocalDateTime fechaOrden;

    // RF-06.1: Estado
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoOrdenCompra estado = EstadoOrdenCompra.PENDIENTE; // Por defecto

    // RF-06.1: Total de la orden (Calculado)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    // RF-06.1: Lista de productos (Relación con DetalleOrdenCompra)
    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrdenCompra> items;

    @PrePersist
    protected void onCreate() {
        if (fechaOrden == null) {
            fechaOrden = LocalDateTime.now();
        }
    }
}