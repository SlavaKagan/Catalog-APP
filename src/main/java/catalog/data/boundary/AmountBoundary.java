package catalog.data.boundary;

public class AmountBoundary {

    private int amountToAddOrReduce;

    public AmountBoundary() {
        super();
    }

    public AmountBoundary(int amountToAddOrReduce) {
        super();
        this.amountToAddOrReduce=amountToAddOrReduce;
    }

    public int getAmount(){
        return amountToAddOrReduce;
    }

    public void setAmount(int amountToAddOrReduce){
        this.amountToAddOrReduce=amountToAddOrReduce;
    }
}
