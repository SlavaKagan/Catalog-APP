package catalog.infra;

import catalog.dao.CatalogRepository;
import catalog.data.boundary.ProductBoundary;
import catalog.data.entity.ProductEntity;
import catalog.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    public ProductEntity create(ProductBoundary product) {
        String inventory_code = product.getInventoryCode();
        //if (this.catalogCrud. {
        //    throw new AlreadyExistsException("product already exists with code: " + code);
       // }
        return this.catalogCrud.save(toEntity(product));
    }

    @Override
    public Optional<ProductEntity> getProductByItemNo(Long itemNo)
    {
        return this.catalogCrud.findByItemNo(itemNo);
    }

    @Override
    public List<ProductEntity> getAllProducts(int page, int size, String sort) {
        return this.catalogCrud.findAll(PageRequest.of(page, size, Sort.Direction.ASC, sort)).getContent();
    }

    @Override
    public ProductEntity withdrawQuantity(String inventoryCode,int amount) {
        ProductEntity productEntity = this.catalogCrud.findByInventoryCode(inventoryCode).orElseThrow(() -> new NotFoundException());
        productEntity.setAmount(amount);
        return this.catalogCrud.save(productEntity);
    }

    @Override
    public ProductEntity depositQuantity(String inventoryCode,int amount) {
        ProductEntity productEntity = this.catalogCrud.findByInventoryCode(inventoryCode).orElseThrow(() -> new NotFoundException());
        productEntity.setAmount(amount);
        return this.catalogCrud.save(productEntity);
    }

    private ProductBoundary fromEntity(ProductEntity product) {
        ProductBoundary rv = new ProductBoundary();
        rv.setName(product.getName());
        rv.setInventoryCode(product.getInventoryCode());
        rv.setAmount(product.getAmount());
        return rv;
    }

    private ProductEntity toEntity(ProductBoundary product) {
        try {
            ProductEntity rv = new ProductEntity();
            rv.setName(product.getName());
            rv.setInventoryCode(product.getInventoryCode());
            rv.setAmount(product.getAmount());
            return rv;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
