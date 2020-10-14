package catalog.layout;

import catalog.data.boundary.ProductBoundary;
import catalog.data.entity.ProductEntity;
import catalog.exceptions.BadReqException;
import catalog.exceptions.NotFoundException;
import catalog.infra.CatalogService;
import catalog.utils.FinalStrings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Catalog Controller methods
 */

@RestController
@RequestMapping("/catalog")
@Api(value = "Catalog controller")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    /**
     * Add item to stock
     */
    @ApiOperation(
            value = "Add item to stock",
            notes = "Add item to stock in catalog")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = FinalStrings.CREATED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = FinalStrings.CONFLICT),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @PostMapping(
            name = "Add item to stock",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ProductEntity addItem(
            @RequestBody ProductBoundary boundary) {
        return this.catalogService.create(boundary);
    }

    /**
     * Get all items -- GET
     */
    @ApiOperation(
            value = "Get all items",
            notes = "default sort is id",
            nickname = "getAll")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(
            name = "Get all the items in the system using pagination",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductEntity> getAllItems(
            @RequestParam(name = "sortBy", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return this.catalogService.getAllProducts(page, size, sort)
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Get item -- GET
     *
     */
    @ApiOperation(value = "Get specific item",
            nickname = "getItem")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(
            value = "/{itemNo}",
            name = "Get specific item",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductBoundary getProductById(@PathVariable("itemNo") Long itemNo) {
        return this.fromEntity(this.catalogService.getProductByItemNo(itemNo)
                .orElseThrow(() -> new NotFoundException("could not find product by itemNo: " + itemNo)));
    }

    /**
     * Withdraw/Deposit quantity of a specific item in stock -- PATCH
     */
    @ApiOperation(
            value = "Update quantity - Withdraw",
            nickname = "Withdraw Quantity")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @PatchMapping(value = "/withdraw/{inventoryCode}",
            name = "Update quantity - Withdraw")
    ProductBoundary withdrawQuantity(
            @PathVariable(value = "inventoryCode") String inventoryCode,
            @RequestParam(name = "amount", required = false, defaultValue = "0") int amount) {
        return new ProductBoundary(
                this.catalogService.withdrawQuantity(inventoryCode,amount));
    }

    /**
     * Delete specific item from stock -- DELETE
     */
    @ApiOperation(
            value = "Delete specific item from stock",
            notes = "Delete")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })

    @DeleteMapping(path = "/{inventoryCode}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteItem(
            @PathVariable(name = "inventoryCode") String inventoryCode) {
        this.catalogService.deleteByCode(inventoryCode);
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
