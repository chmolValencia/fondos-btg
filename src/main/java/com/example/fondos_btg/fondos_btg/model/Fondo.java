// src/main/java/com/fondos_btg/model/Fondo.java
package com.example.fondos_btg.fondos_btg.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor // ← Añade esta anotación
@Document(collection = "fondos")
public class Fondo {

    @Id
    private String id;
    private Integer fondoId;
    private String nombre;
    private Double montoMinimo;
    private String categoria;
    private boolean activo = true;
}