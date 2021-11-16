package com.project4.project_4_group_81;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * This is the controller class for Pizza Customization.
 * Controls actions like adding/removing toppings for the pizza, setting the size of the pizza, and adding it to cart.
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */
public class PizzaCustomizationController {

    @FXML ComboBox<Size> pizzaSizeBox;
    @FXML ListView<Topping> additionalToppingsList;
    @FXML ListView<Topping> currentToppingsList;
    @FXML Button addButton;
    @FXML Button removeButton;
    @FXML Button addToOrderButton;
    @FXML ImageView pizzaImageView;
    @FXML TextField pizzaPriceText;
    @FXML Label pizzaTypeLabel;
    @FXML Label orderLabel;
    private String pizzaType;
    private Pizza pizza;
    private final ObservableList<Size> size = FXCollections.observableArrayList(Size.SMALL, Size.MEDIUM, Size.LARGE);
    private Topping addTopping;
    private Topping removeTopping;
    private Order order;
    private DecimalFormat decimalFormat = new DecimalFormat();

    /**
     * Initialize method that is called when a new instance of this class is created.
     * sets the pizza type, and displays the appropriate image and toppings corresponding to the type.
     * @param pizzaType
     */
    public void initialize(String pizzaType, Order order){

        Image pizzaImage = null;
        this.pizzaType = pizzaType;
        this.order = order;

        switch(this.pizzaType){
            case "Deluxe Pizza" -> {
                pizzaImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Deluxe.jpg")));
                pizza = PizzaMaker.createPizza("Deluxe");
            }
            case "Pepperoni Pizza" -> {
                pizzaImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Pepperoni.jpg")));
                pizza = PizzaMaker.createPizza("Pepperoni");
            }
            case "Hawaiian Pizza" -> {
                pizzaImage =  new Image(Objects.requireNonNull(getClass().getResourceAsStream("Hawaiian.jpg")));
                pizza = PizzaMaker.createPizza("Hawaiian");
            }
        }

        pizzaImageView.setImage(pizzaImage);
        pizzaTypeLabel.setText(this.pizzaType);
        pizza.size = Size.SMALL;
        pizzaSizeBox.setValue(Size.SMALL);
        pizzaSizeBox.setItems(size);
        long orderNumber = order.getPhoneNumber();
        orderLabel.setText(String.valueOf(orderNumber));
        decimalFormat.setMaximumFractionDigits(2);
        setParameters();

    }

    /**
     * Private method that creates Observable lists for toppings of pizza and adds them to the List View.
     */
    private void setParameters(){

        ObservableList<Topping> currentToppings = FXCollections.observableArrayList(pizza.toppings);
        ObservableList<Topping> additionalToppings = FXCollections.observableArrayList(pizza.getAdditionalToppings());
        currentToppingsList.setItems(currentToppings);
        additionalToppingsList.setItems(additionalToppings);
        pizzaPriceText.setText(String.valueOf(decimalFormat.format(pizza.price())) + " $");

    }

    /**
     * Method that sets the size of the pizza depending on the selected value.
     */
    @FXML
    public void onClickSize(){

            switch (pizzaSizeBox.getSelectionModel().getSelectedItem()) {
                case SMALL -> {
                    pizzaSizeBox.setValue(Size.SMALL);
                    pizza.size = Size.SMALL;
                }
                case MEDIUM -> {
                    pizzaSizeBox.setValue(Size.MEDIUM);
                    pizza.size = Size.MEDIUM;
                }
                case LARGE -> {
                    pizzaSizeBox.setValue(Size.LARGE);
                    pizza.size = Size.LARGE;
                }
            }
        pizzaPriceText.setText(String.valueOf(decimalFormat.format(pizza.price())) + " $");

    }

    /**
     * Method that adds a topping to the pizza.
     */
    @FXML
    public void onClickAdd(){

        addTopping = additionalToppingsList.getSelectionModel().getSelectedItem();

        additionalToppingsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Topping>() {
            @Override
            public void changed(ObservableValue<? extends Topping> observableValue, Topping topping, Topping t1) {
                addTopping =  additionalToppingsList.getSelectionModel().getSelectedItem();
            }
        });

        if(addTopping != null) {
            pizza.toppings.add(addTopping);
            pizza.getAdditionalToppings().remove(addTopping);
            setParameters();
        }
    }

    /**
     * Method that removes a topping from the pizza.
     */
    @FXML
    public void onClickRemove(){

        removeTopping =  currentToppingsList.getSelectionModel().getSelectedItem();

        currentToppingsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Topping>() {
            @Override
            public void changed(ObservableValue<? extends Topping> observableValue, Topping topping, Topping t1) {
                removeTopping =  currentToppingsList.getSelectionModel().getSelectedItem();
            }
        });

        if(removeTopping != null) {
            pizza.toppings.remove(removeTopping);
            pizza.getAdditionalToppings().add(removeTopping);
            setParameters();
        }
        //Create an alert to tell the client that price doesnt decrease when toppings drop below default
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning: Low toppings");
        alert.setContentText("Pizza prices do not decrease if the number of toppings drop below " +
                "the number of store customized toppings. ");
        switch(pizzaType){
            case "Deluxe Pizza" -> {
                if(pizza.toppings.size() < 5) alert.showAndWait();
            }
            case "Pepperoni Pizza" -> {
                if(pizza.toppings.size() < 1) alert.showAndWait();
            }
            case "Hawaiian Pizza" -> {
                if(pizza.toppings.size() < 2) alert.showAndWait();
            }
        }
    }

    /**
     * Method that adds the pizza to the cart (current order)
     * @param event
     */
    @FXML
    public void onClickAddToOrder(Event event) {
        order.addPizza(pizza);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Added to Order");
        alert.showAndWait();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}


