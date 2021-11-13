package com.project4.project_4_group_81;

import java.util.ArrayList;

/**
 * This class defines a pizza and its properties (size, toppings, and price)
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public abstract class Pizza {
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    /**
     * Abstract method that calculates the price, it must be implemented in all subclasses.
     * @return double
     */
    public abstract double price();

    /**
     * Method to add a topping to the pizza.
     * @param topping
     */
    public void addTopping(Topping topping) {
        this.toppings.add(topping);
    }

    /**
     * Method to clear the toppings from the pizza.
     */
    public void clearToppings() {
        this.toppings.clear();
    }

    /**
     * Method to set the size of the pizza.
     * @param size
     */
    public void setSize(Size size) {
        this.size = size;
    }
}
