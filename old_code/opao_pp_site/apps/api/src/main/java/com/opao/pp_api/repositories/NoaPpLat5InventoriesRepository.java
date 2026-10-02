package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.NoaPpLat5InventoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoaPpLat5InventoriesRepository extends JpaRepository<NoaPpLat5InventoriesEntity, Integer> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT n FROM NoaPpLat5Inventories n WHERE n.noaPpLat5InventoriesId = :noaPpLat5InventoriesId
    Optional<NoaPpLat5InventoriesEntity> findByNoaPpLat5InventoriesId(Integer noaPpLat5InventoriesId);

    // SELECT n FROM NoaPpLat5Inventories n WHERE n.jur = :jur
    List<NoaPpLat5InventoriesEntity> findByJur(String jur);

    // SELECT n FROM NoaPpLat5Inventories n WHERE n.parid = :parid
    List<NoaPpLat5InventoriesEntity> findByParid(String parid);

    // SELECT n FROM NoaPpLat5Inventories n WHERE n.taxyr = :taxyr
    List<NoaPpLat5InventoriesEntity> findByTaxyr(Integer taxyr);

    // SELECT n FROM NoaPpLat5Inventories n WHERE n.fileyr = :fileyr
    List<NoaPpLat5InventoriesEntity> findByFileyr(Integer fileyr);

    // SELECT n FROM NoaPpLat5Inventories n WHERE n.inventoryType = :inventoryType
    List<NoaPpLat5InventoriesEntity> findByInventoryType(String inventoryType);

    // SELECT n FROM NoaPpLat5Inventories n WHERE n.inventoryMonth = :inventoryMonth
    List<NoaPpLat5InventoriesEntity> findByInventoryMonth(String inventoryMonth);

    // SELECT n FROM NoaPpLat5Inventories n WHERE n.inventoryAmt = :inventoryAmt
    List<NoaPpLat5InventoriesEntity> findByInventoryAmt(BigDecimal inventoryAmt);
}
