package catalog.dao;
import catalog.data.entity.ItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CRUD database
 */

@Repository
public interface CatalogRepository extends PagingAndSortingRepository<ItemEntity, String>{
    Optional<ItemEntity> findByItemNo(@Param("itemNo") Long itemNo);
    Optional<ItemEntity> findByInventoryCode(@Param("inventoryCode") String inventoryCode);
    Optional<ItemEntity> deleteByInventoryCode(@Param("inventoryCode") String inventoryCode);
}
