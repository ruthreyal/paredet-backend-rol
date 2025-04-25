package com.paredetapp.model;

import javax.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int cantidad;
    private double precioUnitario;
}
