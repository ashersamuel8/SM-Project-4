package com.project4.project_4_group_81;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.Iterator;

/**
 * This is the controller class for Current Order; Performs actions like editing the order, placing order
 * @author Bhavya Patel
 * @author Samuel Asher Kappala
 */
public class CurrentOrderController {

    @FXML ListView<Pizza> orderList;
    @FXML Label subTotalLabel;
    @FXML Label taxLabel;
    @FXML Label totalLabel;
    @FXML Button removePizzaButton;
    @FXML Button placeOrderButton;
    @FXML Label orderNumberLabel;
    private ObservableList<Pizza> pizzas;
    private Pizza removePizza;
    private Order order;
    private long orderNumber;
    private double total;

    /**
     * Method that is called when this class is instantiated.
     * @param order
     */
    public void initialize(Order order) {
        orderList.getItems().clear();
        this.order = order;
        pizzas = FXCollections.observableArrayList(this.order.getPizzas());
        double subTotal = 0;
        double tax;

        Iterator<Pizza> iterator = pizzas.iterator();
        while(iterator.hasNext()){
            Pizza buffPizza = iterator.next();
            orderList.getItems().add(buffPizza);
            subTotal += buffPizza.price();
        }
        tax = 0.06625 * subTotal;
        total = tax + subTotal;

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        subTotalLabel.setText(String.valueOf(decimalFormat.format(subTotal)) + " $");
        taxLabel.setText(String.valueOf(decimalFormat.format(tax)) + " $");
        totalLabel.setText(String.valueOf(decimalFormat.format(total)) + " $");
        orderNumber = order.getPhoneNumber();
        orderNumberLabel.setText(String.valueOf(orderNumber));
    }

    /**
     * Method that removes the selected pizza from the order
     */
    @FXML
    public void onClickRemovePizza(){

        removePizza = orderList.getSelectionModel().getSelectedItem();

        orderList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pizza>() {
            @Override
            public void changed(ObservableValue<? extends Pizza> observableValue, Pizza s, Pizza t1) {
                removePizza =  orderList.getSelectionModel().getSelectedItem();
            }
        });

        if(removePizza != null) {
            order.removePizza(removePizza);
            orderList.getItems().remove(removePizza);
            initialize(this.order);
        }

    }

    /**
     * Method that places order (adds to store orders)
     * @param event
     */
    @FXML
    public void onClickPlaceOrder(Event event) {
        if (total == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Cant Place Empty Orders");
            alert.showAndWait();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            return;
        }
        order.setOrderTotal(total);
        MainMenuController.placeOrder(order);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Placed");
        alert.setHeaderText("Order Placed");
        alert.showAndWait();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

    }
}
