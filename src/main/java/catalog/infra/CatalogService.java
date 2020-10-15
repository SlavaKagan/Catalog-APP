package catalog.infra;

import catalog.data.boundary.ItemBoundary;
import catalog.data.entity.ItemEntity;

import java.util.List;

public interface CatalogService {
    void deleteByCode(String inventoryCode);
    ItemEntity create(ItemBoundary boundary);
    ItemEntity getItemByItemNo(Long itemNo);
    List<ItemEntity> getAllItems(int page, int size, String sort);
    ItemEntity withdrawQuantity(String inventoryCode, int amount);
    ItemEntity depositQuantity(String inventoryCode, int amount);
}
