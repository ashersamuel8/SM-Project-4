package com.project4.project_4_group_81;

/**
 * This class stores all of the orders that have been placed
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class StoreOrders {
    private ArrayList<Order> orders;

    /**
     * This is the default constructor.
     */
    public StoreOrders() {
        // default
    }

    /**
     * This is the constructor for when a list of orders is provided.
     * @param orders
     */
    public StoreOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * This method clears the list of orders.
     */
    public void clearOrders() {
        this.orders.clear();
    }

    /**
     * This method adds an order to the list.
     * @param order
     */
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    /**
     * This method exports the current list of orders to a file.
     * @throws FileNotFoundException
     */
    public void export() throws FileNotFoundException {
        //PrintWriter out = new PrintWriter("filename.txt");
    }
}
