package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RF-05.1: Fecha y hora de la venta (se genera automáticamente)
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    // RF-05.1: Subtotal de la venta
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // RF-05.1: Total de la venta
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // RF-05.1: Método de pago (Usando el Enum)
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    // RF-05.1: Lista de productos vendidos (Relación con DetalleVenta)
    // CascadeType.ALL: Si se borra la Venta, se borran sus DetalleVenta.
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (fechaHora == null) {
            fechaHora = LocalDateTime.now();
        }
    }
}