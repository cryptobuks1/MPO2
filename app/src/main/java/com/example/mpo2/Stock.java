package com.example.mpo2;

/**
 * This class is service locater for Product Catalog and Stock.
 *
 * @author Refresh Team
 *
 */
public class Stock {

    private StockExtension stock;
    private ItemCatalog itemCatalog;
    private static Stock instance = null;
    private static StockDao stockDao = null;

    /**
     * Constructs Data Access Object of inventory.
     * @throws DaoNoSetException if DAO is not exist.
     */
    private Stock() throws DaoNoSetException {
        if (!isDaoSet()) {
            throw new DaoNoSetException();
        }
        stock = new StockExtension(stockDao);
        itemCatalog = new ItemCatalog(stockDao);
    }

    /**
     * Determines whether the DAO already set or not.
     * @return true if the DAO already set; otherwise false.
     */
    public static boolean isDaoSet() {
        return stockDao != null;
    }

    /**
     * Sets the database connector.
     * @param dao Data Access Object of inventory.
     */
    public static void setInventoryDao(StockDao dao) {
        stockDao = dao;
    }

    /**
     * Returns product catalog using in this inventory.
     * @return product catalog using in this inventory.
     */
    public ItemCatalog getProductCatalog() {
        return itemCatalog;
    }

    /**
     * Returns stock using in this inventory.
     * @return stock using in this inventory.
     */
    public StockExtension getStock() {
        return stock;
    }

    /**
     * Returns the instance of this singleton class.
     * @return instance of this class.
     * @throws DaoNoSetException if DAO was not set.
     */
    public static Stock getInstance() throws DaoNoSetException {
        if (instance == null)
            instance = new Stock();
        return instance;
    }

}
