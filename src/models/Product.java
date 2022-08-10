package models;

public abstract class Product {

    // instance variables
    private double price;
    private int stockQuantity, soldQuantity, cartAmount;

    // constructor
    public Product(double initPrice, int initQuantity) {
        this.price = initPrice;
        this.stockQuantity = initQuantity;
        this.cartAmount = 0;
    }

    public boolean outOfStock() {
        return (this.stockQuantity == 0);
    }

    // simulates selling product units
    public double sellUnits(int amount) {
            this.stockQuantity -= amount;                       // decreases stock
            this.soldQuantity += amount;                        // increases sold amount
            return this.price * (double)amount;                 // returns revenue
    }

    // returns hash code of a product
    public int hashCode() {
        return Double.toString(this.price).hashCode();
    }

    //region getters, setters
    public int getStockQuantity() { return this.stockQuantity; }
    public int getSoldQuantity() { return this.soldQuantity; }
    public double getPrice() { return this.price; }
    public int getCartAmount() { return this.cartAmount; }
    public void setStockQuantity(int value) { this.stockQuantity = value; }
    public void setCartAmount(int value) { this.cartAmount = value; }
    //endregion
}