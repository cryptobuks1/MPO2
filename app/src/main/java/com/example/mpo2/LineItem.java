package com.example.mpo2;

import java.util.HashMap;
import java.util.Map;

/**
 * LineItem of Sale.
 *
 * @author Refresh Team
 *
 */
public class LineItem {

    private final Item item;
    private int quantity;
    private int id;
    private double unitPriceAtSale;

    /**
     * Static value for UNDEFINED ID.
     */
    public static final int UNDEFINED = -1;

    /**
     * Constructs a new LineItem.
     * @param item product of this LineItem.
     * @param quantity product quantity of this LineItem.
     */
    public LineItem(Item item, int quantity) {
        this(UNDEFINED, item, quantity, item.getUnitPrice());
    }

    /**
     * Constructs a new LineItem.
     * @param id ID of this LineItem, This value should be assigned from database.
     * @param product product of this LineItem.
     * @param quantity product quantity of this LineItem.
     * @param unitPriceAtSale unit price at sale time. default is price from ProductCatalog.
     */
    public LineItem(int id, Item item, int quantity,
                    double unitPriceAtSale) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.unitPriceAtSale = unitPriceAtSale;
    }

    /**
     * Returns product in this LineItem.
     * @return product in this LineItem.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Return quantity of product in this LineItem.
     * @return quantity of product in this LineItem.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity of product in this LineItem.
     * @param quantity quantity of product in this LineItem.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Adds quantity of product in this LineItem.
     * @param amount amount for add in quantity.
     */
    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    /**
     * Returns total price of this LineItem.
     * @return total price of this LineItem.
     */
    public double getTotalPriceAtSale() {
        return unitPriceAtSale * quantity;
    }

    /**
     * Returns the description of this LineItem in Map format.
     * @return the description of this LineItem in Map format.
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", item.getName());
        map.put("quantity", quantity + "");
        map.put("price", getTotalPriceAtSale() + "");
        return map;

    }

    /**
     * Returns id of this LineItem.
     * @return id of this LineItem.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of this LineItem.
     * @param id of this LineItem.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets price product of this LineItem.
     * @param unitPriceAtSale price product of this LineItem.
     */
    public void setUnitPriceAtSale(double unitPriceAtSale) {
        this.unitPriceAtSale = unitPriceAtSale;
    }

    /**
     * Returns price product of this LineItem.
     * @return unitPriceAtSale price product of this LineItem.
     */
    public Double getPriceAtSale() {
        return unitPriceAtSale;
    }

    /**
     * Determines whether two objects are equal or not.
     * @return true if Object is a LineItem with same ID ; otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (!(object instanceof LineItem))
            return false;
        LineItem lineItem = (LineItem) object;
        return lineItem.getId() == this.getId();
    }
}
