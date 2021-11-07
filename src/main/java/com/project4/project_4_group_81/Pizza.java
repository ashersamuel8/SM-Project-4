package com.project4.project_4_group_81;

public abstract class Pizza {
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;
    public abstract double price();
}
