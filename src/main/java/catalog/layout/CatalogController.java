package catalog.layout;

import catalog.data.Option;
import catalog.data.boundary.AmountBoundary;
import catalog.data.boundary.ItemBoundary;
import catalog.data.entity.ItemEntity;
import catalog.infra.CatalogService;
import catalog.utils.FinalStrings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
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
    ItemEntity addItem(
            @RequestBody ItemBoundary boundary) {
        return this.catalogService.create(boundary);
    }

    /**
     * Get all items, List of the inventory items list -- GET
     */
    @ApiOperation(
            value = "Get all items",
            nickname = "getAll")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(
            name = "List of the inventory items list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemEntity> getAllItems(
            @RequestParam(name = "sortBy", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return this.catalogService.getAllItems(page, size, sort)
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Get item details by itemNo-- GET
     *
     */
    @ApiOperation(
            value = "Get specific item",
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
    public ItemBoundary getItemById(@PathVariable("itemNo") Long itemNo) {
        return this.fromEntity(this.catalogService.getItemByItemNo(itemNo));
    }

    /**
     * Withdraw/Deposit quantity of a specific item in stock -- PATCH
     */
    @ApiOperation(
            value = "Update quantity - Withdraw/Deposit",
            nickname = "Withdraw/Deposit Quantity")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @PatchMapping(value = "/quantity/{inventoryCode}/{option}",
            name = "Update quantity - Withdraw/Deposit")
    ItemEntity withdrawDepositQuantity(
            @PathVariable(value = "inventoryCode") String inventoryCode,
            @PathVariable(value = "option") Option option,
            @RequestBody AmountBoundary itemAmount) {

        ItemEntity item=null;
        switch (option) {
            case WITHDRAW:
                item = catalogService.withdrawQuantity(inventoryCode, itemAmount.getAmount());
                break;
            case DEPOSIT:
                item = catalogService.depositQuantity(inventoryCode, itemAmount.getAmount());
                break;
        }
        return item;
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

    private ItemBoundary fromEntity(ItemEntity item) {
        ItemBoundary rv = new ItemBoundary();
        rv.setName(item.getName());
        rv.setInventoryCode(item.getInventoryCode());
        rv.setAmount(item.getAmount());
        return rv;
    }
}