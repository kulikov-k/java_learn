package attestation;

public class Person {
    private String name;
    private int sumMoney;
    private String product;

    public Person(String name, int sumMoney, String product) {
        this.name = name;
        this.sumMoney = sumMoney;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(int sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
