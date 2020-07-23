package com.example.mpo2;

import java.util.List;

/**
 * Import log of ProductLot come in to store.
 *
 * @author Refresh Team
 *
 */
public class StockExtension {

    private StockDao stockDao;

    /**
     * Constructs Data Access Object of inventory in ProductCatalog.
     * @param stockDao of inventory.
     */
    public StockExtension(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * Constructs ProductLot and adds ProductLot to inventory.
     * @param dateAdded date added of ProductLot.
     * @param quantity quantity of ProductLot.
     * @param product product of ProductLot.
     * @param cost cost of ProductLot.
     * @return
     */
    public boolean addProductLot(String dateAdded, int quantity, Item product, double cost) {
        ItemLot productLot = new ItemLot(ItemLot. UNDEFINED_ID, dateAdded, quantity, product, cost);
        int id = stockDao.addProductLot(productLot);
        return id != -1;
    }

    /**
     * Returns list of ProductLot in inventory finds by id.
     * @param id id of ProductLot.
     * @return list of ProductLot in inventory finds by id.
     */
    public List<ItemLot> getProductLotByProductId(int id) {
        return stockDao.getProductLotByProductId(id);
    }

    /**
     * Returns all ProductLots in inventory.
     * @return all ProductLots in inventory.
     */
    public List<ItemLot> getAllItemLot() {
        return StockDao.getAllItemLot();
    }

    /**
     * Returns Stock in inventory finds by id.
     * @param id id of Stock.
     * @return Stock in inventory finds by id.
     */
    public int getStockSumById(int id) {
        return StockDao.getStockSumById(id);
    }

    /**
     * Updates quantity of product.
     * @param productId id of product.
     * @param quantity quantity of product.
     */
    public static void updateStockSum(int productId, int quantity) {
        StockDao.updateStockSum(productId,quantity);

    }

    /**
     * Clear Stock.
     */
    public void clearStock() {
        stockDao.clearStock();

    }


}
