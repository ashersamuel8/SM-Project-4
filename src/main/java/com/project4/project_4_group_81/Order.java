package com.project4.project_4_group_81;

import java.util.ArrayList;

/**
 * This class takes a customer's order and stores the customer's phone number
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Order {
    private int phoneNumber;
    private ArrayList<Pizza> pizzas;

    /**
     * This is the default constructor.
     */
    public Order() {
        // default
    }

    /**
     * Constructor for when only the phone number is provided.
     * @param phoneNumber
     */
    public Order(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor for when both a phone number and list of pizzas is provided
     * @param phoneNumber
     * @param pizzas
     */
    public Order(int phoneNumber, ArrayList<Pizza> pizzas) {
        this.phoneNumber = phoneNumber;
        this.pizzas = pizzas;
    }

    /**
     * Method to clear the list of pizzas in this order.
     */
    public void clearOrder() {
        this.pizzas.clear();
    }

    /**
     * Method to add a pizza to the order.
     * @param pizza
     */
    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
    }
}
