package catalog.data.entity;


import javax.persistence.*;

@Entity
@Table(name="STOCK")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long itemNo;
    private String name;
    private int amount;
    private String inventoryCode;

    public ItemEntity(){
    }

    public ItemEntity(String name, int amount, String inventoryCode) {
        super();
        this.name=name;
        this.amount=amount;
        this.inventoryCode=inventoryCode;
    }

    public Long getItemNo(){
        return itemNo;
    }

    public void setItemNo(Long itemNo){
        this.itemNo=itemNo;
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