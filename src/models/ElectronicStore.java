package models;
import java.util.*;

public class ElectronicStore {

    //region instance variables
    private String name;
    private double revenue, cartValue;
    private int sales;
    private ArrayList<Product> stock, products;
    private Set<Product> cart;
    //endregion

    //region constructor
    public ElectronicStore(String iName){
        this.name = iName;
        this.revenue = 0.0;
        this.cartValue = 0.0;
        this.sales = 0;
        this.stock = new ArrayList<>();
        this.products = new ArrayList<>();
        this.cart = new HashSet<>();
    }
    //endregion

    public void addCart(Product p) { cart.add(p); }             // adds a product to the cart
    public void removeCart(Product p) {
        cart.remove(p);
    }       // removes a product from the cart
    public void addStock(Product p) { stock.add(p); }           // adds a product to the store stock
    public void removeStock(Product p) {
        stock.remove(p);
    }     // removes a product from the store stock

    // adds product to store (if it is not already in the stock)
    public boolean addProduct(Product newProduct){
        if (stock.contains(newProduct))
            return false;
        return stock.add(newProduct) && products.add(newProduct);
    }

    // returns a list of the most popular products
    public List<Product> getTopProducts(int x) {

        List<Product> list = new ArrayList<>(products);    // creates a list from products (set to list)

        // uses Collections Comparator
        Comparator<Product> productComparator = Comparator.comparingDouble(Product::getSoldQuantity);
        Collections.sort(list, productComparator);
        Collections.reverse(list);                      // reverses the list (so it is from greatest to least)

        List<Product> topProducts = new ArrayList<>();
        for (int i = 0; i<x; i++)
            topProducts.add(list.get(i));
        return topProducts;
    }

    //region creates an instance of a store
    public static ElectronicStore createStore(){
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        store1.cartValue = 0;
        return store1;
    }
    //endregion

    //region getters, setters
    public List<Product> getStock() { List<Product> list = new ArrayList<>(stock); return list; }
    public List<Product> getCart() { List<Product> list = new ArrayList<>(cart); return list; }
    public String getName() { return this.name; }
    public double getCartValue() { return this.cartValue; }
    public double getRevenue() { return this.revenue; }
    public int getSales() { return this.sales; }
    public void setCartValue(double value) { this.cartValue = value; }
    public void setRevenue(double value) { this.revenue = value; }
    public void setSales(int value) { this.sales = value; }
    //endregion
} 