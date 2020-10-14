package catalog.data.boundary;

import catalog.data.entity.ProductEntity;

public class ProductBoundary {

    private String name;
    private int amount;
    private String inventoryCode;

    public ProductBoundary() {
        super();
    }

    public ProductBoundary(String name, int amount, String inventoryCode) {
        super();
        this.name=name;
        this.amount=amount;
        this.inventoryCode=inventoryCode;
    }

    public ProductBoundary(ProductEntity withdrawQuantity) {
    }

    public String getName(){
        return name;
    }

    public void setName(String name) { this.name=name; }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount=amount;
    }

    public String getInventoryCode() { return inventoryCode; }

    public void setInventoryCode(String inventoryCode) { this.inventoryCode=inventoryCode; }
}