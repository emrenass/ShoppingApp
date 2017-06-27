package com.shopping.model;

import java.util.Comparator;
import org.primefaces.model.SortOrder;
 
public class LazySorter implements Comparator<Product> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(Product product1, Product product2) {
        try {
            Object value1 = Product.class.getField(this.sortField).get(product1);
            Object value2 = Product.class.getField(this.sortField).get(product2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
