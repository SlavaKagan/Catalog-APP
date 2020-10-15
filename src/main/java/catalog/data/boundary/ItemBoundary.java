package catalog.data.boundary;

/**
 * Item object details
 */
public class ItemBoundary {

    private String name;
    private int amount;
    private String inventoryCode;

    public ItemBoundary() {
        super();
    }

    public ItemBoundary(String name, int amount, String inventoryCode) {
        super();
        this.name=name;
        this.amount=amount;
        this.inventoryCode=inventoryCode;
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