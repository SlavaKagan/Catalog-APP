package catalog.dao;
import catalog.data.entity.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends PagingAndSortingRepository<ProductEntity, String>{
    Optional<ProductEntity> findByItemNo(@Param("itemNo") Long itemNo);
    Optional<ProductEntity> findByInventoryCode(@Param("inventoryCode") String inventoryCode);
    Optional<ProductEntity> deleteByInventoryCode(@Param("inventoryCode") String inventoryCode);
}
