package com.example.mpo2;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;






/**
 * DAO used by android for Inventory.
 *
 * @author Refresh Team
 *
 */
public class StockDaoAndroid implements StockDao {

    private Database database;

    /**
     * Constructs InventoryDaoAndroid.
     * @param database database for use in InventoryDaoAndroid.
     */
    public StockDaoAndroid(Database database) {
        this.database = database;
    }

    @Override
    public int addProduct(Item product) {
        ContentValues content = new ContentValues();
        content.put("name", product.getName());
        content.put("barcode", product.getBarcode());
        content.put("unit_price", product.getUnitPrice());
        content.put("status", "ACTIVE");

        int id = database.insert(DatabaseContents.TABLE_PRODUCT_CATALOG.toString(), content);


        ContentValues content2 = new ContentValues();
        content2.put("_id", id);
        content2.put("quantity", 0);
        database.insert(DatabaseContents.TABLE_STOCK_SUM.toString(), content2);

        return id;
    }

    /**
     * Converts list of object to list of product.
     * @param objectList list of object.
     * @return list of product.
     */
    private List<Item> toProductList(List<Object> objectList) {
        List<Item> list = new ArrayList<Item>();
        for (Object object: objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new Item(
                    content.getAsInteger("_id"),
                    content.getAsString("name"),
                    content.getAsString("barcode"),
                    content.getAsDouble("unit_price"))
            );
        }
        return list;
    }

    @Override
    public List<Item> getAllProduct() {
        return getAllProduct(" WHERE status = 'ACTIVE'");
    }

    /**
     * Returns list of all products in inventory.
     * @param condition specific condition for getAllProduct.
     * @return list of all products in inventory.
     */
    private List<Item> getAllProduct(String condition) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG.toString() + condition + " ORDER BY name";
        List<Item> list = toProductList(database.select(queryString));
        return list;
    }

    /**
     * Returns product from inventory finds by specific reference.
     * @param reference reference value.
     * @param value value for search.
     * @return list of product.
     */
    private List<Item> getProductBy(String reference, String value) {
        String condition = " WHERE " + reference + " = " + value + " ;";
        return getAllProduct(condition);
    }

    /**
     * Returns product from inventory finds by similar name.
     * @param reference reference value.
     * @param value value for search.
     * @return list of product.
     */
    private List<Item> getSimilarProductBy(String reference, String value) {
        String condition = " WHERE " + reference + " LIKE '%" + value + "%' ;";
        return getAllProduct(condition);
    }

    @Override
    public Item getProductByBarcode(String barcode) {
        List<Item> list = getProductBy("barcode", barcode);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Item getProductById(int id) {
        return getProductBy("_id", id+"").get(0);
    }

    @Override
    public boolean editProduct(Item product) {
        ContentValues content = new ContentValues();
        content.put("_id", product.getId());
        content.put("name", product.getName());
        content.put("barcode", product.getBarcode());
        content.put("status", "ACTIVE");
        content.put("unit_price", product.getUnitPrice());
        return database.update(DatabaseContents.TABLE_PRODUCT_CATALOG.toString(), content);
    }


    @Override
    public int addProductLot(ItemLot productLot) {
        ContentValues content = new ContentValues();
        content.put("date_added", productLot.getDateAdded());
        content.put("quantity",  productLot.getQuantity());
        content.put("product_id",  productLot.getItem().getId());
        content.put("cost",  productLot.unitCost());
        int id = database.insert(DatabaseContents.TABLE_STOCK.toString(), content);

        int productId = productLot.getItem().getId();
        ContentValues content2 = new ContentValues();
        content2.put("_id", productId);
        content2.put("quantity", getStockSumById(productId) + productLot.getQuantity());
        Log.d("inventory dao android","" + getStockSumById(productId) + " " + productId + " " +productLot.getQuantity() );
        database.update(DatabaseContents.TABLE_STOCK_SUM.toString(), content2);

        return id;
    }


    @Override
    public List<Item> getProductByName(String name) {
        return getSimilarProductBy("name", name);
    }

    @Override
    public List<Item> searchProduct(String search) {
        String condition = " WHERE name LIKE '%" + search + "%' OR barcode LIKE '%" + search + "%' ;";
        return getAllProduct(condition);
    }

    /**
     * Returns list of all ProductLot in inventory.
     * @param condition specific condition for get ProductLot.
     * @return list of all ProductLot in inventory.
     */
    private List<ItemLot> getAllProductLot(String condition) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_STOCK.toString() + condition;
        List<ItemLot> list = toProductLotList(database.select(queryString));
        return list;
    }

    /**
     * Converts list of object to list of ProductLot.
     * @param objectList list of object.
     * @return list of ProductLot.
     */
    private List<ItemLot> toProductLotList(List<Object> objectList) {
        List<ItemLot> list = new ArrayList<ItemLot>();
        for (Object object: objectList) {
            ContentValues content = (ContentValues) object;
            int productId = content.getAsInteger("product_id");
            Item product = getProductById(productId);
            list.add(
                    new ItemLot(content.getAsInteger("_id"),
                            content.getAsString("date_added"),
                            content.getAsInteger("quantity"),
                            product,
                            content.getAsDouble("cost"))
            );
        }
        return list;
    }

    @Override
    public List<ItemLot> getProductLotByProductId(int id) {
        return getAllProductLot(" WHERE product_id = " + id);
    }

    @Override
    public List<ItemLot> getProductLotById(int id) {
        return getAllProductLot(" WHERE _id = " + id);
    }

    //@Override NIKNIK
    public List<ItemLot> getAllProductLot() {
        return getAllProductLot("");
    }

    //@Override NIKNIK
    public int getStockSumById(int id) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_STOCK_SUM + " WHERE _id = " + id;
        List<Object> objectList = (database.select(queryString));
        ContentValues content = (ContentValues) objectList.get(0);
        int quantity = content.getAsInteger("quantity");
        Log.d("inventoryDaoAndroid", "stock sum of "+ id + " is " + quantity);
        return quantity;
    }

    //@Override NIKNIK
    public void updateStockSum(int productId, double quantity) {
        ContentValues content = new ContentValues();
        content.put("_id", productId);
        content.put("quantity", getStockSumById(productId) - quantity);
        database.update(DatabaseContents.TABLE_STOCK_SUM.toString(), content);
    }

    @Override
    public void clearProductCatalog() {
        database.execute("DELETE FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG);
    }

    @Override
    public void clearStock() {
        database.execute("DELETE FROM " + DatabaseContents.TABLE_STOCK);
        database.execute("DELETE FROM " + DatabaseContents.TABLE_STOCK_SUM);
    }

    //@Override NIKNIK
    public void suspendProduct(Item product) {
        ContentValues content = new ContentValues();
        content.put("_id", product.getId());
        content.put("name", product.getName());
        content.put("barcode", product.getBarcode());
        content.put("status", "INACTIVE");
        content.put("unit_price", product.getUnitPrice());
        database.update(DatabaseContents.TABLE_PRODUCT_CATALOG.toString(), content);
    }




}
