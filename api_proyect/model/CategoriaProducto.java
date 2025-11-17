package com.universidadcartagena.api_proyect.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entidad que representa la clasificación de los productos en la tienda.
 * Es la parte 'Uno' en la relación Uno-a-Muchos con Producto.
 */
@Entity
@Table(name = "categoria_producto")
@Data // Genera getters, setters, toString, equals y hashCode (Lombok)
@NoArgsConstructor // Genera constructor sin argumentos (Lombok)
@AllArgsConstructor // Genera constructor con todos los argumentos (Lombok)
public class CategoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave Primaria (PK)

    @Column(nullable = false, unique = true)
    private String nombre; // Ej: "Abarrotes", "Aseo Personal"

    private String descripcion;
}