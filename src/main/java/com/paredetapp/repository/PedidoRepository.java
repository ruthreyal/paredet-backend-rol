package com.paredetapp.repository;

import com.paredetapp.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    List<Pedido> findByUsuario_Id(UUID idUsuario);

    @Query("SELECT SUM(p.total) FROM Pedido p WHERE MONTH(p.fechaCreacion) = MONTH(CURRENT_DATE) AND YEAR(p.fechaCreacion) = YEAR(CURRENT_DATE)")
    Double obtenerTotalVentasMesActual();


}
