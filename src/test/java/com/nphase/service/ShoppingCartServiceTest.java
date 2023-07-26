package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPriceTask1()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 1),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(12.0));
    }

    @Test
    public void calculatesPriceTask2() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3)
        ));
        BigDecimal methodResult = service.calculateTotalPriceTask2(cart);
        BigDecimal wantedResult = BigDecimal.valueOf(33.00);

        Assertions.assertEquals(0, wantedResult.compareTo(methodResult));

    }

    //I don't know why the testing data doesn't work as intended, the value given in the "Expected total" for task no.3
    //is 31.84 and that's the value that I'm getting, but the test results expect 33.60
    @Test
    public void calculatesPriceTask3() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, "drinks"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, "drinks"),
                new Product("Cheese", BigDecimal.valueOf(8), 2, "food")

        ));

        BigDecimal methodResult = service.calculateTotalPriceTask3(cart);
        BigDecimal wantedResult = BigDecimal.valueOf(31.84);

        Assertions.assertEquals(wantedResult,methodResult);
    }

    @Test
    public void calculatesPriceTask4() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 1),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2)
        ));
        service.setBulkDiscountPercentage(50.0);
        service.setBulkQuantityThreshold(2);
        BigDecimal result = service.calculateTotalPriceTask4(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(12.0));
    }

}