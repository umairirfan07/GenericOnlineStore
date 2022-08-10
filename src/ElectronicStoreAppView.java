import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import models.ElectronicStore;
import models.Product;

import java.net.ProxySelector;

public class ElectronicStoreAppView extends Pane {

    //region GUI components
    private ListView<Product> popularList, stockList;
    private ListView<String> cartList;
    private ListView<String> cartDisplayList;
    private Button resetButton, addButton, removeButton, completeButton;
    private Label currentCart;
    private TextField salesField, revenueField, perSaleField;
    //endregion

    //region getters
    public Button getAddButton() { return addButton; }
    public Button getRemoveButton() { return removeButton; }
    public Button getCompleteButton() { return completeButton; }
    public Button getResetButton() { return resetButton; }
    public ListView<Product> getStockList() { return stockList; }
    public ListView<String> getCartList() { return cartList; }
    //endregion

    public ElectronicStoreAppView() {

        //region text fields
        // creates text fields (sets their text, position, size)
        salesField = new TextField("0");
        salesField.relocate(70, 35);
        salesField.setPrefSize(100, 20);
        salesField.setEditable(false);

        revenueField = new TextField("0.00");
        revenueField.relocate(70, 65);
        revenueField.setPrefSize(100, 20);
        revenueField.setEditable(false);

        perSaleField = new TextField("N/A");
        perSaleField.relocate(70, 95);
        perSaleField.setPrefSize(100,20);
        perSaleField.setEditable(false);
        //endregion

        //region labels
        // creates labels (sets their text, position, size, font)
        currentCart =  new Label("Current Cart: $0.00");
        Label label1 = new Label("Store Summary:");
        Label label2 = new Label("Store Stock:");
        Label label4 = new Label("# Sales:");
        Label label5 = new Label("Revenue:");
        Label label6 = new Label("$ / Sale:");
        Label label7 = new Label("Most Popular Items");

        label1.relocate(10, 10);
        label1.setPrefSize(110, 15);
        label1.setFont(new Font("Arial", 14));
        label2.relocate(220, 10);
        label2.setPrefSize(90, 15);
        label2.setFont(new Font("Arial", 14));
        currentCart.relocate(510, 10);
        currentCart.setPrefSize(250, 15);
        currentCart.setFont(new Font("Arial", 14));
        label4.relocate(10, 40);
        label4.setPrefSize(90, 15);
        label5.relocate(10, 70);
        label5.setPrefSize(90, 15);
        label6.relocate(10, 100);
        label6.setPrefSize(90, 15);
        label7.relocate(10, 130);
        label7.setPrefSize(130, 15);
        label7.setFont(new Font("Arial", 14));
        //endregion

        //region lists
        // creates lists (sets their text, position, size)
        popularList = new ListView<>();
        popularList.relocate(10, 160);
        popularList.setPrefSize(195, 170);

        stockList = new ListView<>();
        stockList.relocate(220, 30);
        stockList.setPrefSize(275, 300);

        cartList = new ListView<>();
        cartList.relocate(510, 30);
        cartList.setPrefSize(275, 300);
        //endregion

        //region buttons
        // creates buttons (sets their text, position, size, style)
        resetButton = new Button("Reset Store");
        resetButton.relocate(30, 340);
        resetButton.setPrefSize(150, 40);
        resetButton.setStyle("-fx-font: 15 arial; -fx-base: rgb(255,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        addButton = new Button("Add to Cart");
        addButton.relocate(282.5, 340);
        addButton.setPrefSize(150, 40);
        addButton.setStyle("-fx-font: 15 arial; -fx-base: rgb(255,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        removeButton = new Button("Remove from Cart");
        removeButton.relocate(510, 340);
        removeButton.setPrefSize(137.5, 40);
        removeButton.setStyle("-fx-font: 14 arial; -fx-base: rgb(255,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        completeButton = new Button("Complete Sale");
        completeButton.relocate(647, 340);
        completeButton.setPrefSize(137.5, 40);
        completeButton.setStyle("-fx-font: 14 arial; -fx-base: rgb(255,255,255); " + "-fx-text-fill: rgb(0,0,0);");
        //endregion

        // adds all the components to the window
        getChildren().addAll(salesField, revenueField, perSaleField, label1, label2, currentCart, label4, label5, label6, label7, resetButton, addButton, removeButton, completeButton, popularList, stockList, cartList);

        // sets up GUI
        setPrefSize(800,400);
    }

    //region update method
    public void update(ElectronicStore model) {

        // sets all the store stock to a list
        Product[] stock = new Product[model.getStock().size()];
        for(int i = 0; i < model.getStock().size(); i++)
            stock[i] = model.getStock().get(i);
        stockList.setItems(FXCollections.observableArrayList(stock));

        // sets all the cart products to a list
        String[] cart = new String[model.getCart().size()];
        for(int i = 0; i < model.getCart().size(); i++)
            cart[i] = model.getCart().get(i).getCartAmount() + " x " + model.getCart().get(i);
        cartList.setItems(FXCollections.observableArrayList(cart));

        // sets most popular items to a list
        popularList.setItems(FXCollections.observableArrayList(model.getTopProducts(3)));

        // sets labels/text fields
        currentCart.setText("Current Cart: (" + String.format("$%.2f", model.getCartValue()) + ")");
        revenueField.setText(String.format("%.2f", model.getRevenue()));
        salesField.setText(model.getSales() + "");
        if (model.getSales() == 0)
            perSaleField.setText("N/A");
        else
            perSaleField.setText(String.format("%.2f", model.getRevenue()/model.getSales()));

        // enables or disables buttons when appropriate
        if (stockList.getSelectionModel().getSelectedIndex() >= 0)
            addButton.setDisable(false);
        else
            addButton.setDisable(true);

        if (cartList.getSelectionModel().getSelectedIndex() >= 0)
            removeButton.setDisable(false);
        else
            removeButton.setDisable(true);

        if (model.getCart().size() == 0)
            completeButton.setDisable(true);
        else
            completeButton.setDisable(false);
    }
    //endregion
}
