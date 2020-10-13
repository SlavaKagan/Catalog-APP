package catalog.dao;
import catalog.data.entity.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends PagingAndSortingRepository<ProductEntity, String>{
}
