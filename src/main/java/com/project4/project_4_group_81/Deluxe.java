package com.project4.project_4_group_81;

/**
 * This class defines a deluxe pizza.
 * @author Samuel Asher Kappala
 * @author Bhavya Patel
 */

public class Deluxe extends Pizza {

    /**
     * This is the default constructor.
     */
    public Deluxe() {
        this.addTopping(Topping.HAM);
        this.addTopping(Topping.PINEAPPLE);
        this.addTopping(Topping.MUSHROOMS);
        this.addTopping(Topping.PEPPERONI);
        this.addTopping(Topping.SAUSAGE);
    }

    /**
     * This method calculates and returns the price of the pizza.
     * @return double
     */
    public double price() {
        double price = 10.99;

        switch(this.size) {
            case SMALL:     price += 0;
                break;
            case MEDIUM:    price += 2;
                break;
            case LARGE:     price += 4;
                break;
            default:        break;
        }

        if(this.toppings.size() > 5) {
            price += (this.toppings.size() - 5) * 1.49;
        }

        return price;
    }
}
