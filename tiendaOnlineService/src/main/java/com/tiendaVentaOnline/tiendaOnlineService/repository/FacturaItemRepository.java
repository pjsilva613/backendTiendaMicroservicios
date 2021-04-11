package com.tiendaVentaOnline.tiendaOnlineService.repository;

import com.tiendaVentaOnline.tiendaOnlineService.entity.FacturaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaItemRepository extends JpaRepository<FacturaItem, Long> {

}
