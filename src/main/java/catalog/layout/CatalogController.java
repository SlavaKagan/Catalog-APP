package catalog.layout;

import catalog.infra.CatalogService;
import catalog.utils.FinalStrings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

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
     * Delete all -- DELETE
     */
    @ApiOperation(
            value = "Delete all",
            notes = "Delete")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAll() {
        this.catalogService.deleteAll();
    }
}
