package com.paredetapp.repository;

import com.paredetapp.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    List<Pedido> findByUsuario_Id(UUID idUsuario);

    @Query("SELECT SUM(p.total) FROM Pedido p WHERE EXTRACT(MONTH FROM p.fechaCreacion) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM p.fechaCreacion) = EXTRACT(YEAR FROM CURRENT_DATE)")
    Double obtenerTotalVentasMesActual();



}
