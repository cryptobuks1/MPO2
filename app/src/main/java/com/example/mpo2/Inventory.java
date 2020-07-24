package com.example.mpo2;

/**
 * This class is service locater for Product Catalog and Stock.
 *
 * @author Refresh Team
 *
 */
public class Inventory {

    private Stock stock;
    private ItemCatalog productCatalog;
    private static Inventory instance = null;
    private static InventoryDao inventoryDao = null;

    /**
     * Constructs Data Access Object of inventory.
     * @throws DaoNoSetException if DAO is not exist.
     */
    private Inventory() throws DaoNoSetException {
        if (!isDaoSet()) {
            throw new DaoNoSetException();
        }
        stock = new Stock(inventoryDao);
        productCatalog = new ItemCatalog(inventoryDao);
    }

    /**
     * Determines whether the DAO already set or not.
     * @return true if the DAO already set; otherwise false.
     */
    public static boolean isDaoSet() {
        return inventoryDao != null;
    }

    /**
     * Sets the database connector.
     * @param dao Data Access Object of inventory.
     */
    public static void setInventoryDao(InventoryDao dao) {
        inventoryDao = dao;
    }

    /**
     * Returns product catalog using in this inventory.
     * @return product catalog using in this inventory.
     */
    public ItemCatalog getProductCatalog() {
        return productCatalog;
    }

    /**
     * Returns stock using in this inventory.
     * @return stock using in this inventory.
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * Returns the instance of this singleton class.
     * @return instance of this class.
     * @throws DaoNoSetException if DAO was not set.
     */
    public static Inventory getInstance() throws DaoNoSetException {
        if (instance == null)
            instance = new Inventory();
        return instance;
    }

}