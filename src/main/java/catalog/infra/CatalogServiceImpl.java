package catalog.infra;

import catalog.dao.CatalogRepository;
import catalog.data.boundary.ItemBoundary;
import catalog.data.entity.ItemEntity;
import catalog.exceptions.AlreadyExistsException;
import catalog.exceptions.NotFoundException;
import catalog.exceptions.WithdrawException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Catalog Service
 */

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogCrud;

    /**
     * Delete specific item from stock
     */
    @Override
    @Transactional
    public void deleteByCode(String inventoryCode) {
        if (this.catalogCrud.findByInventoryCode(inventoryCode).equals(null)) {
            throw new NotFoundException();
        }
        this.catalogCrud.deleteByInventoryCode(inventoryCode);
    }

    @Override
    public ItemEntity create(ItemBoundary item) {
        String inventory_code = item.getInventoryCode();
        if (this.catalogCrud.findByInventoryCode(inventory_code).isPresent())
            throw new AlreadyExistsException("item already exists with code: " + inventory_code);

        return this.catalogCrud.save(toEntity(item));
    }

    @Override
    public ItemEntity getItemByItemNo(Long itemNo)
    {
        return this.catalogCrud.findByItemNo(itemNo).orElseThrow(()-> new NotFoundException("No such item in stock"));
    }

    @Override
    public List<ItemEntity> getAllItems(int page, int size, String sort) {
        return this.catalogCrud.findAll(PageRequest.of(page, size, Sort.Direction.ASC, sort)).getContent();
    }

    @Override
    public ItemEntity withdrawQuantity(String inventoryCode, int amount) {
        ItemEntity itemEntity = this.catalogCrud.findByInventoryCode(inventoryCode).orElseThrow(() -> new NotFoundException("No such item in stock"));
        if (itemEntity.getAmount()<amount)
            throw new WithdrawException("Can't withdraw this amount from stock");
        itemEntity.setAmount(itemEntity.getAmount()-amount);
        return this.catalogCrud.save(itemEntity);
}

    @Override
    public ItemEntity depositQuantity(String inventoryCode, int amount) {
        ItemEntity itemEntity = this.catalogCrud.findByInventoryCode(inventoryCode).orElseThrow(() -> new NotFoundException("No such item in stock"));
        itemEntity.setAmount(itemEntity.getAmount()+amount);
        return this.catalogCrud.save(itemEntity);
    }

    private ItemEntity toEntity(ItemBoundary item) {
        try {
            ItemEntity rv = new ItemEntity();
            rv.setName(item.getName());
            rv.setInventoryCode(item.getInventoryCode());
            rv.setAmount(item.getAmount());
            return rv;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
