package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RF-04.2: Producto afectado (Relación con la tabla Producto)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // RF-04.2: Tipo de movimiento (Usando el Enum)
    @Enumerated(EnumType.STRING) // Guarda el tipo como String (ENTRADA, SALIDA, etc.)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    // RF-04.2: Cantidad (cuánto se movió)
    @Column(nullable = false)
    private Integer cantidad;

    // RF-04.2: Fecha y hora (se genera automáticamente en el código)
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    // RF-04.2: Motivo/Observaciones
    @Column(length = 500) // Un campo más largo para la explicación
    private String observaciones;


    // Se usa @PrePersist para asegurar que la fecha se establezca antes de guardar
    @PrePersist
    protected void onCreate() {
        if (fechaHora == null) {
            fechaHora = LocalDateTime.now();
        }
    }
}