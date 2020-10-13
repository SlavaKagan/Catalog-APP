package catalog.data.entity;

import org.springframework.data.annotation.Id;

public class ProductEntity {
    private String id;
    private int price;

    public ProductEntity(){

    }

    public ProductEntity(int price) {
     super();
     this.price=price;
    }

    @Id
    public String getID(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price=price;
    }
}
