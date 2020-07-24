package com.example.mpo2;

import java.util.List;

/**
 * DAO for Inventory.
 *
 * @author Refresh Team
 *
 */
public interface InventoryDao {

    /**
     * Adds product to inventory.
     * @param product the product to be added.
     * @return id of this product that assigned from database.
     */
    int addProduct(Item product);

    /**
     * Adds ProductLot to inventory.
     * @param productLot the ProductLot to be added.
     * @return id of this ProductLot that assigned from database.
     */
    int addProductLot(ItemLot productLot);

    /**
     * Edits product.
     * @param product the product to be edited.
     * @return true if product edits success ; otherwise false.
     */
    boolean editProduct(Item product);

    /**
     * Returns product from inventory finds by id.
     * @param id id of product.
     * @return product
     */
    Item getProductById(int id);

    /**
     * Returns product from inventory finds by barcode.
     * @param barcode barcode of product.
     * @return product
     */
    Item getProductByBarcode(String barcode);

    /**
     * Returns list of all products in inventory.
     * @return list of all products in inventory.
     */
    List<Item> getAllProduct();

    /**
     * Returns list of product in inventory finds by name.
     * @param name name of product.
     * @return list of product in inventory finds by name.
     */
    List<Item> getProductByName(String name);

    /**
     * Search product from string in inventory.
     * @param search string for searching.
     * @return list of product.
     */
    List<Item> searchProduct(String search);

    /**
     * Returns list of all products in inventory.
     * @return list of all products in inventory.
     */
    List<ItemLot> getAllProductLot();

    /**
     * Returns list of product in inventory finds by id.
     * @param id id of product.
     * @return list of product in inventory finds by id.
     */
    List<ItemLot> getProductLotById(int id);

    /**
     * Returns list of ProductLot in inventory finds by id.
     * @param id id of ProductLot.
     * @return list of ProductLot in inventory finds by id.
     */
    List<ItemLot> getProductLotByProductId(int id);

    /**
     * Returns Stock in inventory finds by id.
     * @param id id of Stock.
     * @return Stock in inventory finds by id.
     */
    int getStockSumById(int id);

    /**
     * Updates quantity of product.
     * @param productId id of product.
     * @param quantity quantity of product.
     */
    void updateStockSum(int productId, double quantity);

    /**
     * Clears ProductCatalog.
     */
    void clearProductCatalog();

    /**
     * Clear Stock.
     */
    void clearStock();

    /**
     * Hidden product from inventory.
     * @param product The product to be hidden.
     */
    void suspendProduct(Item product);

}
