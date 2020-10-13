package catalog.infra;

import catalog.dao.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Catalog Service
 */

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogCrud;

    /**
     * Delete all
     */
    @Override
    public void deleteAll() {
        this.catalogCrud.deleteAll();
    }
}
