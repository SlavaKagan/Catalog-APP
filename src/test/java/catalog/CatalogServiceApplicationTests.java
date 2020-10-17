package catalog;

import catalog.data.boundary.ItemBoundary;
import catalog.data.entity.ItemEntity;
import catalog.exceptions.NotFoundException;
import catalog.infra.CatalogService;
import catalog.utils.FinalStrings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static de.flapdoodle.embed.process.runtime.AbstractProcess.TIMEOUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CatalogServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CatalogServiceApplicationTests {

    @Autowired
    private CatalogService catalogService;

    private int port;
    private String baseUrl;
    private RestTemplate restTemplate;
    private ObjectMapper jacksonMapper;

    /**
     * Constructor
     */
    public CatalogServiceApplicationTests(){

    }

    /**
     * Initialize port
     */
    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Initialize for all the tests
     */
    @BeforeAll
    @PostConstruct
    public void init(){
        this.restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);
        restTemplate.setRequestFactory(requestFactory);
        this.baseUrl = "http://localhost:" + port + "/catalog";

        System.err.println(this.baseUrl);
        this.jacksonMapper = new ObjectMapper();
    }

    /**
     * Given nothing
     * When nothing
     * Then the server is loading properly
     */
    @Test
    public void testServerInitializesProperly() {
    }

    @Test
    public void contextLoads() {
        assertThat(catalogService).isNotNull();
    }

    @Test
    public void addItem() throws JsonProcessingException {

        ItemBoundary item=new ItemBoundary("sprite", 67, "nh78i");

        Object response= this.restTemplate.postForObject(
                this.baseUrl,
                item,
                Object.class
        );

        assertThat(jacksonMapper.writeValueAsString(response))
                .isNotNull();

        assertThat(this.catalogService.getItemByItemNo(1L))
                .isNotNull();

        assertThat(this.catalogService.getItemByItemNo(1L)
                .equals(1L));
    }

    @Test
    public void getSpecificItem() throws JsonProcessingException {

        Long itemNo=1L;
        this.catalogService.create(new ItemBoundary("cola", 34, "ty78i"));

        ItemBoundary response = this.restTemplate.getForObject(
                baseUrl + "/{itemNo}",
                ItemBoundary.class,
                itemNo
        );

        assertThat(jacksonMapper.writeValueAsString(response))
                .isNotNull();

        assertEquals("ty78i",response.getInventoryCode());
    }

    @Test
    public void deleteItemSuccessfully() throws JsonProcessingException {

        String inventoryCode = "32er6";
        Long itemNo=1L;
        ItemBoundary item=new ItemBoundary("cola1", 343, inventoryCode);

        this.catalogService.create(item);

        this.restTemplate.delete(
                this.baseUrl+ "/{inventoryCode}",
                inventoryCode
        );

        try {
            this.catalogService.getItemByItemNo(itemNo);
        } catch (NotFoundException e) {
            assertEquals(FinalStrings.NOT_FOUND, e.getMessage());
        }
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
