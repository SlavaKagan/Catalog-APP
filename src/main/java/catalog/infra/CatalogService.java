package catalog.infra;

import catalog.data.boundary.ProductBoundary;
import catalog.data.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface CatalogService {
    void deleteByCode(String inventoryCode);
    ProductEntity create(ProductBoundary boundary);
    Optional<ProductEntity> getProductByItemNo(Long itemNo);
    List<ProductEntity> getAllProducts(int page, int size, String sort);
    ProductEntity withdrawQuantity(String inventoryCode,int amount);
    ProductEntity depositQuantity(String inventoryCode,int amount);
}
