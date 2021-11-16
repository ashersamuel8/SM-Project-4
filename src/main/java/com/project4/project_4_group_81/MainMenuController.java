package com.project4.project_4_group_81;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * This is the controller class for main menu
 * @author Bhavya Patel
 * @author Samuel Asher Kappala
 */
public class MainMenuController implements Initializable {

    @FXML ImageView deluxeView;
    @FXML ImageView pepperoniView;
    @FXML ImageView hawaiianView;
    @FXML ImageView currentOrderView;
    @FXML ImageView storeOrderView;
    @FXML Button deluxeButton;
    @FXML Button peperoniButton;
    @FXML Button hawaiianButton;
    @FXML Button currentOrderButton;
    @FXML Button storeOrdersButton;
    @FXML TextField customerPhoneField;
    private long customerPhone;
    private final Image deluxeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Deluxe.jpg")));
    private final Image pepperoniImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Pepperoni.jpg")));
    private final Image hawaiianImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Hawaiian.jpg")));
    private final Image cartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Cart.png")));
    private final Image storeOrdersImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("StoreOrders.png")));
    private Order order;
    private static StoreOrders storeOrders = new StoreOrders();

    /**
     * This method is automatically trigger when the MainMenu.fxml is loaded
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deluxeView.setImage(deluxeImage);
        pepperoniView.setImage(pepperoniImage);
        hawaiianView.setImage(hawaiianImage);
        currentOrderView.setImage(cartImage);
        storeOrderView.setImage(storeOrdersImage);
        order = new Order();
    }

    /**
     * Method that describes actions to be performed when one of the pizza buttons is clicked.
     * Checks for phone number format, enforces unique order IDs
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickPizzaButton(ActionEvent event) throws IOException {
        Alert phoneAlert = new Alert(Alert.AlertType.ERROR);
        phoneAlert.setTitle("ERROR");
        phoneAlert.setHeaderText("Enter a Valid Phone Number");
        phoneAlert.setContentText("Phone Number must be 10 digits");
        try {
            if (customerPhoneField.getText().isBlank() || customerPhoneField.getText().length() != 10){
                phoneAlert.showAndWait();
                return;
            }
            if(customerPhone != 0){
                if(customerPhone != Long.parseLong(customerPhoneField.getText())){
                    Alert orderAlert = new Alert(Alert.AlertType.WARNING, "Current order in progress; Would you like to " +
                            "start a new order with this ID?", ButtonType.YES, ButtonType.NO);
                    orderAlert.setTitle("WARNING");
                    Optional<ButtonType> action = orderAlert.showAndWait();
                    if(action.get().equals(ButtonType.YES)){
                        order = new Order();
                        customerPhone = Long.parseLong(customerPhoneField.getText());
                    }
                    else {
                        customerPhoneField.setText(String.valueOf(customerPhone));
                        return;
                    }
                }
            }
            else customerPhone = Long.parseLong(customerPhoneField.getText());
            if(orderExists(customerPhone)) {
                Alert orderAlert = new Alert(Alert.AlertType.ERROR);
                orderAlert.setTitle("ERROR");
                orderAlert.setHeaderText("Order already exists; Try a new number");
                orderAlert.showAndWait();
                return;
            }
            order.setPhoneNumber(customerPhone);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PizzaCustomization.fxml"));
            Scene scene = new Scene(loader.load());
            PizzaCustomizationController pizzaCustomizationController = loader.getController();
            String pizzaType = ((Button) event.getSource()).getText();
            pizzaCustomizationController.initialize(pizzaType, order);
            Stage stage = new Stage();
            stage.setTitle("Pizza Customization");
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e){
            phoneAlert.showAndWait();
            return;
        }
    }

    /**
     * Method that describes actions to be performed when "current orders" button is clicked.
     * Creates and shows a new stage that displays current order.
     * @throws IOException
     */
    @FXML
    public void onClickCurrentOrder() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainMenuController.class.getResource("CurrentOrder.fxml"));
        Scene scene = new Scene(loader.load());
        CurrentOrderController currentOrderController = loader.getController();
        currentOrderController.initialize(order);
        Stage stage = new Stage();
        stage.setTitle("Current Order Details");
        stage.setScene(scene);
        stage.showAndWait();
        if(storeOrders.getOrders().contains(order)){
            order = new Order();
            customerPhone = 0;
            customerPhoneField.setText("");
        }
    }

    /**
     * Method that describes actions to be performed when "store orders" button is clicked.
     * Creates and shows a new stage that displays store orders.
     * @throws IOException
     */
    @FXML
    public void onClickStoreOrders() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreOrders.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Store Orders");
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Getter method that returns store orders
     * @return storeOrders
     */
    public static StoreOrders getStoreOrders(){
        return storeOrders;
    }

    /**
     * Method that adds order to store orders (places the order)
     * @param order
     */
    public static void placeOrder(Order order){
        storeOrders.addOrder(order);
    }

    /**
     * Private Method that checks if the order (phone) # already exists in store orders
     * @param customerPhone
     * @return true if exists, false otherwise
     */
    private boolean orderExists(long customerPhone){
        ArrayList<Order> orders = storeOrders.getOrders();
        Iterator<Order> iterator = orders.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getPhoneNumber() == customerPhone) return true;
        }
        return false;
    }
}
