package com.project4.project_4_group_81;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class displays all orders of the store. Allows client to remove an order from the list, export orders to a file.
 * @author Bhavya Patel
 * @author Samuel Asher Kappala
 */
public class StoreOrdersController  {

    @FXML private ComboBox<Long> customerNumberBox;
    @FXML private Label orderTotalLabel;
    @FXML private Button cancelOrderButton;
    @FXML private Button exportOrdersButton;
    @FXML private ListView<Pizza> ordersList;
    private ObservableList<Long> customerNumbers;
    private ObservableList<Pizza> pizzas;
    private ArrayList<Order> orders;
    private ArrayList<Long> orderNumbers = new ArrayList<>();
    private ArrayList<Pizza> pizzaList = new ArrayList<>();
    private DecimalFormat decimalFormat = new DecimalFormat();

    /**
     * Method that is automatically called when this class is instantiated
     */
    public void initialize(){

        decimalFormat.setMaximumFractionDigits(2);
        orders = MainMenuController.getStoreOrders().getOrders();
        Iterator<Order> orderIterator = orders.iterator();
        orderNumbers.clear();
        while(orderIterator.hasNext()){
            orderNumbers.add(orderIterator.next().getPhoneNumber());
        }
        customerNumbers = FXCollections.observableArrayList(orderNumbers);
        customerNumberBox.getItems().clear();
        customerNumberBox.setItems(customerNumbers);
        if(orders.size() > 0 && customerNumbers.size() > 0) {
            customerNumberBox.setValue(customerNumbers.get(0));
            pizzaList = orders.get(0).getPizzas();
            pizzas = FXCollections.observableArrayList(pizzaList);
            ordersList.setItems(pizzas);
            orderTotalLabel.setText(String.valueOf(decimalFormat.format(orders.get(0).getOrderTotal())) + " $");
        }
        else{
            ordersList.getItems().clear();
            orderTotalLabel.setText("");
        }
    }

    /**
     * Method that allows client to select the order number from the combo box
     */
    @FXML
    public void onClickCustomerNumberBox(){
        Long orderID = customerNumberBox.getSelectionModel().getSelectedItem();
        if(orderID == null) return;
        int orderIndex = orderNumbers.indexOf(orderID);
        if(orderIndex == -1) return;
        pizzaList = orders.get(orderIndex).getPizzas();
        pizzas = FXCollections.observableArrayList(pizzaList);
        ordersList.setItems(pizzas);
        orderTotalLabel.setText(String.valueOf(decimalFormat.format(orders.get(orderIndex).getOrderTotal()))+" $");
    }

    /**
     * Method that removes the selected order from store orders
     */
    @FXML
    public void onClickCancelOrder(){
        if(orders.size() == 0){
            return;
        }
        Long orderID = customerNumberBox.getSelectionModel().getSelectedItem();
        int orderIndex = orderNumbers.indexOf(orderID);
        orders.remove(orderIndex);
        initialize();
    }

    /**
     * Method that exports store orders to an external file
     * @throws IOException
     */
    @FXML
    public void onClickExport() throws IOException {
        if(orders.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("No orders to export");
            alert.showAndWait();
            return;
        }
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Target File for the Export");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File targetFile = chooser.showSaveDialog(stage);
        Path path = Path.of(targetFile.getPath());
        String output = "";
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            output += iterator.next().toString() + "\n";
        }
        Files.writeString(path, output);
    }

}
