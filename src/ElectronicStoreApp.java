import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.ElectronicStore;
import models.Product;

public class ElectronicStoreApp extends Application {
    ElectronicStore model;

    // sets the model
    public ElectronicStoreApp() { model = ElectronicStore.createStore(); }

    public void start(Stage primaryStage) {
        Pane aPane = new Pane();

        //region view
        // creates and sets the view
        ElectronicStoreAppView view = new ElectronicStoreAppView();
        aPane.getChildren().add(view);

        primaryStage.setTitle(model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();

        view.update(model);
        //endregion

        //region event handling

        // checks if item in stock list has been selected
        view.getStockList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e){
                view.update(model);
            }
        });

        // checks if item in cart list has been selected
        view.getCartList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e){
                view.update(model);
            }
        });

        // reset button
        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                // updates the model to the original store
                model = ElectronicStore.createStore();
                view.update(model);
            }
        });

        // add button
        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                // gets the index of the selected product, calls method to handle button
                int index = view.getStockList().getSelectionModel().getSelectedIndex();
                handleAdd(index);
                view.update(model);
            }
        });

        // remove button
        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                // gets the index of the selected product, calls method to handle button
                int index = view.getCartList().getSelectionModel().getSelectedIndex();
                handleRemove(index);
                view.update(model);
            }
        });

        // complete sales button
        view.getCompleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                // calls method to handle button
                handleComplete();
                view.update(model);
            }
        });
        //endregion
    }

    //region methods to handle buttons
    // handles add button
    public void handleAdd(int i) {
        // if a product has been selected...
        if (i!=-1) {
            Product p = model.getStock().get(i);                        // saves product
            model.setCartValue(model.getCartValue() + p.getPrice());    // increases cart value
            p.setStockQuantity(p.getStockQuantity() - 1);               // decrements stock
            p.setCartAmount(p.getCartAmount() + 1);                     // increments cart amount

            // removes product from store if out of stock, otherwise adds to cart
            if (p.outOfStock())
                model.removeStock(p);
            else
                model.addCart(p);
        }
    }

    // handles remove button
    public void handleRemove(int i) {
        // if a product has been selected...
        if (i!=-1) {
            Product p = model.getCart().get(i);                            // saves product
            model.setCartValue(model.getCartValue() - p.getPrice());       // decreases cart value
            if (p.outOfStock()) model.addStock(p);                         // add product back to stock if it was out
            p.setStockQuantity(p.getStockQuantity() + 1);                  // increments stock
            p.setCartAmount(p.getCartAmount() - 1);                        // decrements cart amount

            // removes products from cart if fully removed from cart
            if (p.getCartAmount() == 0)
                model.removeCart(p);
        }
    }

    // handles complete button
    public void handleComplete() {
        // loops thru products in stock
        for (Product p: model.getCart()) {
            model.setRevenue(model.getRevenue() + p.sellUnits(p.getCartAmount()));   // increases store revenue
            p.setCartAmount(0);                                                      // sets cart amount to 0
            model.removeCart(p);                                                     // removes product from cart
        }
        model.setCartValue(0.00);                                                    // resets cart value
        model.setSales(model.getSales() + 1);                                        // increments sales
    }
    //endregion

    public static void main(String[] args) { launch(args); }
}
