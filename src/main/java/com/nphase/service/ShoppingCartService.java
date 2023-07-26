package com.nphase.service;

import com.nphase.entity.ShoppingCart;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartService {
    //Task 4: Adding the additional parameters
    private int bulkQuantityThreshold;
    private double bulkDiscountPercentage;


    //Task 1
    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    //Task 2
    public BigDecimal calculateTotalPriceTask2(ShoppingCart shoppingCart) {
        Map<String, BigDecimal> productTotalMap = new HashMap<>();

        for (var product : shoppingCart.getProducts()) {
            BigDecimal pricePerUnit = product.getPricePerUnit();
            int quantity = product.getQuantity();

            if (quantity > 3) {
                BigDecimal discountedPrice = pricePerUnit.multiply(BigDecimal.valueOf(0.9));
                productTotalMap.put(product.getName(), discountedPrice.multiply(BigDecimal.valueOf(quantity)));
            } else {
                productTotalMap.put(product.getName(), pricePerUnit.multiply(BigDecimal.valueOf(quantity)));
            }
        }

        BigDecimal totalPrice = productTotalMap.values()
                .stream()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        totalPrice = totalPrice.setScale(2);

        return totalPrice;
    }

    //Task 3
     public BigDecimal calculateTotalPriceTask3(ShoppingCart shoppingCart) {
        Map<String, Integer> categoryQuantityMap = new HashMap<>();
        Map<String, BigDecimal> categoryTotalMap = new HashMap<>();

        for (var product : shoppingCart.getProducts()) {
            BigDecimal pricePerUnit = product.getPricePerUnit();
            int quantity = product.getQuantity();
            String category = product.getCategory();

            // Add the quantity of the product to the categoryQuantityMap
            int currentQuantity = categoryQuantityMap.getOrDefault(category, 0);
            categoryQuantityMap.put(category, currentQuantity + quantity);
        }

        for (var product : shoppingCart.getProducts()) {
            BigDecimal pricePerUnit = product.getPricePerUnit();
            int quantity = product.getQuantity();
            String category = product.getCategory();

            // Check if the total quantity for the category is more than 3
            int totalCategoryQuantity = categoryQuantityMap.getOrDefault(category, 0);
            if (totalCategoryQuantity > 3) {
                BigDecimal discountedPrice = pricePerUnit.multiply(BigDecimal.valueOf(0.9));
                BigDecimal categoryTotal = categoryTotalMap.getOrDefault(category, BigDecimal.ZERO);
                categoryTotal = categoryTotal.add(discountedPrice.multiply(BigDecimal.valueOf(quantity)));
                categoryTotalMap.put(category, categoryTotal);
            } else {
                BigDecimal categoryTotal = categoryTotalMap.getOrDefault(category, BigDecimal.ZERO);
                categoryTotal = categoryTotal.add(pricePerUnit.multiply(BigDecimal.valueOf(quantity)));
                categoryTotalMap.put(category, categoryTotal);
            }
        }

        return categoryTotalMap.values()
                .stream()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    //Task 4
    public BigDecimal calculateTotalPriceTask4(ShoppingCart shoppingCart) {
        Map<String, BigDecimal> categoryTotalMap = new HashMap<>();

        for (var product : shoppingCart.getProducts()) {
            BigDecimal pricePerUnit = product.getPricePerUnit();
            int quantity = product.getQuantity();
            String category = product.getCategory();

            if (quantity > bulkQuantityThreshold) {
                BigDecimal discountedPrice = pricePerUnit.multiply(BigDecimal.valueOf(1 - (bulkDiscountPercentage / 100)));
                BigDecimal categoryTotal = categoryTotalMap.getOrDefault(category, BigDecimal.ZERO);
                categoryTotal = categoryTotal.add(discountedPrice.multiply(BigDecimal.valueOf(quantity)));
                categoryTotalMap.put(category, categoryTotal);
            } else {
                BigDecimal categoryTotal = categoryTotalMap.getOrDefault(category, BigDecimal.ZERO);
                categoryTotal = categoryTotal.add(pricePerUnit.multiply(BigDecimal.valueOf(quantity)));
                categoryTotalMap.put(category, categoryTotal);
            }
        }

        return categoryTotalMap.values()
                .stream()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }


    public int getBulkQuantityThreshold() {
        return bulkQuantityThreshold;
    }

    public double getBulkDiscountPercentage() {
        return bulkDiscountPercentage;
    }

    public void setBulkQuantityThreshold(int bulkQuantityThreshold) {
        this.bulkQuantityThreshold = bulkQuantityThreshold;
    }

    public void setBulkDiscountPercentage(double bulkDiscountPercentage) {
        this.bulkDiscountPercentage = bulkDiscountPercentage;
    }
}
