package com.example.mpo2;

import java.util.HashMap;
import java.util.Map;

/**
 * Lot or bunch of Item that import to inventory.
 *
 * @author Refresh Team
 *
 */
public class ItemLot {

    private int id;
    private String dateAdded;
    private int quantity;
    private Item Item;
    private double unitCost;

    /**
     * Static value for UNDEFINED ID.
     */
    public static final int UNDEFINED_ID = -1;

    /**
     * Constructs a new ItemLot.
     * @param id ID of the ItemLot, This value should be assigned from database.
     * @param dateAdded date and time of adding this lot.
     * @param quantity quantity of Item.
     * @param Item a Item of this lot.
     * @param unitCost cost (of buy) of each unit in this lot.
     */
    public ItemLot(int id, String dateAdded, int quantity, Item Item, double unitCost) {
        this.id = id;
        this.dateAdded = dateAdded;
        this.quantity = quantity;
        this.Item = Item;
        this.unitCost = unitCost;
    }

    /**
     * Returns date added of this ItemLot.
     * @return date added of this ItemLot.
     */
    public String getDateAdded() {
        return dateAdded;
    }

    /**
     * Returns quantity of this ItemLot.
     * @return quantity of this ItemLot.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns cost of this ItemLot.
     * @return cost of this ItemLot.
     */
    public double unitCost() {
        return unitCost;
    }

    /**
     * Returns id of this ItemLot.
     * @return id of this ItemLot.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns Item in this ItemLot.
     * @return Item in this ItemLot.
     */
    public Item getItem() {
        return Item;
    }

    /**
     * Returns the description of this ItemLot in Map format.
     * @return the description of this ItemLot in Map format.
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id + "");
        map.put("dateAdded", DateTimeSettings.format(dateAdded));
        map.put("quantity", quantity + "");
        map.put("ItemName", Item.getName());
        map.put("cost", unitCost + "");
        return map;
    }
}
