package com.example.mpo2;

import java.util.List;

/**
 * Book that keeps list of Item.
 *
 * @author Refresh Team
 *
 */
public class ItemCatalog {

    private StockDao stockDao;

    /**
     * Constructs Data Access Object of inventory in ItemCatalog.
     * @param stockDao DAO of inventory.
     */
    public ItemCatalog(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * Constructs Item and adds Item to inventory.
     * @param name name of Item.
     * @param barcode barcode of Item.
     * @param salePrice price of Item.
     * @return true if Item adds in inventory success ; otherwise false.
     */
    public boolean addItem(String name, String barcode, double salePrice) {
        Item Item = new Item(name, barcode, salePrice);
        int id = stockDao.addProduct(Item);
        return id != -1;
    }

    /**
     * Edits Item.
     * @param
     * @return true if Item edits success ; otherwise false.
     */
    public boolean editItem(Item item) {
        boolean respond = stockDao.editProduct(item);
        return respond;
    }

    /**
     * Returns Item from inventory finds by barcode.
     * @param barcode barcode of Item.
     * @return Item
     */
    public Item getItemByBarcode(String barcode) {
        return stockDao.getProductByBarcode(barcode);
    }

    /**
     * Returns Item from inventory finds by id.
     * @param id id of Item.
     * @return Item
     */
    public Item getItemById(int id) {
        return stockDao.getProductById(id);
    }

    /**
     * Returns list of all Items in inventory.
     * @return list of all Items in inventory.
     */
    /** public List<Item> getAllProduct() {
     return StockDao.getAllItem();
     NIKNIK
     }**/

    /**
     * Returns list of Item in inventory finds by name.
     * @param name name of Item.
     * @return list of Item in inventory finds by name.
     */
    public List<Item> getItemByName(String name) {
        return stockDao.getProductByName(name);
    }

    /**
     * Search Item from string in inventory.
     * @param search string for searching.
     * @return list of Item.
     */
    /**public static List<Item> searchItem(String search) {
     return StockDao.searchItem(search);
     }**/

    /**
     * Clears ItemCatalog.
     */
    public void clearItemCatalog() {
        stockDao.clearProductCatalog();
    }

    /**
     * Hidden Item from inventory.
     * @param Item The Item to be hidden.
     */
    public static void suspendItem(Item Item) {
        StockDao.suspendProduct(Item);
    }


}
