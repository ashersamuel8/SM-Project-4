package com.project4.project_4_group_81;

import org.junit.Assert;
import org.junit.Test;

public class DeluxeTest {

    @Test
    public void price() {
        Pizza testPizza1 = PizzaMaker.createPizza("Deluxe");
        Pizza testPizza2 = PizzaMaker.createPizza("Deluxe");
        Pizza testPizza3 = PizzaMaker.createPizza("Deluxe");
        Pizza testPizza4 = PizzaMaker.createPizza("Deluxe");
        Pizza testPizza5 = PizzaMaker.createPizza("Deluxe");
        Pizza testPizza6 = PizzaMaker.createPizza("Deluxe");

        //TEST 1
        testPizza1.toppings.add(Topping.PINEAPPLE);
        testPizza1.toppings.add(Topping.OLIVES);
        testPizza1.size = Size.MEDIUM;
        Assert.assertEquals(testPizza1.price(), 17.97, 0.0001);

        //TEST 2
        testPizza2.toppings.add(Topping.OLIVES);
        testPizza2.size = Size.LARGE;
        Assert.assertEquals(testPizza2.price(), 18.48, 0.001);

        //TEST 3
        testPizza3.size = Size.SMALL;
        Assert.assertEquals(testPizza3.price(), 12.99,0.001);

        //TEST 4
        testPizza4.toppings.remove(Topping.HAM);
        testPizza4.size = Size.SMALL;
        Assert.assertEquals(testPizza4.price(), 12.99,0.001);

        //TEST 5
        testPizza5.toppings.remove(Topping.TOMATOES);
        testPizza5.toppings.remove(Topping.PEPPERONI);
        testPizza5.toppings.remove(Topping.SAUSAGE);
        testPizza5.size = Size.MEDIUM;
        Assert.assertEquals(testPizza5.price(),14.99,0.001 );

        //TEST 6
        testPizza6.toppings.remove(Topping.PEPPERONI);
        testPizza6.toppings.remove(Topping.HAM);
        testPizza6.toppings.remove(Topping.MUSHROOMS);
        testPizza6.toppings.remove(Topping.SAUSAGE);
        testPizza6.toppings.remove(Topping.TOMATOES);
        testPizza6.size = Size.LARGE;
        Assert.assertEquals(testPizza6.price(),16.99,0.001);
    }
}