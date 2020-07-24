package com.example.mpo2;

/**
 * Handles all Sale processes.
 *
 * @author Refresh Team
 *
 */
public class Registers {
    private static Registers instance = null;
    private static SalesDao saleDao = null;
    private static Stock stock = null;

    private Sales currentSale;

    private Registers() throws DaoNoSetException {
        if (!isDaoSet()) {
            throw new DaoNoSetException();
        }
        stock = Inventory.getInstance().getStock();

    }

    /**
     * Determines whether the DAO already set or not.
     * @return true if the DAO already set; otherwise false.
     */
    public static boolean isDaoSet() {
        return saleDao != null;
    }

    public static Registers getInstance() throws DaoNoSetException {
        if (instance == null) instance = new Registers();
        return instance;
    }

    /**
     * Injects its sale DAO
     * @param dao DAO of sale
     */
    public static void setSaleDao(SalesDao dao) {
        saleDao = dao;
    }

    /**
     * Initiates a new Sale.
     * @param startTime time that sale created.
     * @return Sale that created.
     */
    public Sales initiateSale(String startTime) {
        if (currentSale != null) {
            return currentSale;
        }
        currentSale = saleDao.initiateSale(startTime);
        return currentSale;
    }

    /**
     * Add Product to Sale.
     * @param product product to be added.
     * @param quantity quantity of product that added.
     * @return LineItem of Sale that just added.
     */
    public LineItem addItem(Item product, int quantity) {
        if (currentSale == null)
            initiateSale(DateTimeSettings.getCurrentTime());

        LineItem lineItem = currentSale.addLineItem(product, quantity);

        if (lineItem.getId() == LineItem.UNDEFINED) {
            int lineId = saleDao.addLineItem(currentSale.getId(), lineItem);
            lineItem.setId(lineId);
        } else {
            saleDao.updateLineItem(currentSale.getId(), lineItem);
        }

        return lineItem;
    }

    /**
     * Returns total price of Sale.
     * @return total price of Sale.
     */
    public double getTotal() {
        if (currentSale == null) return 0;
        return currentSale.getTotal();
    }

    /**
     * End the Sale.
     * @param endTime time that Sale ended.
     */
    public void endSale(String endTime) {
        if (currentSale != null) {
            saleDao.endSale(currentSale, endTime);
            for(LineItem line : currentSale.getAllLineItem()){
                stock.updateStockSum(line.getItem().getId(), line.getQuantity());
            }
            currentSale = null;
        }
    }

    /**
     * Returns the current Sale of this Register.
     * @return the current Sale of this Register.
     */
    public Sales getCurrentSale() {
        if (currentSale == null)
            initiateSale(DateTimeSettings.getCurrentTime());
        return currentSale;
    }

    /**
     * Sets the current Sale of this Register.
     * @param id of Sale to retrieve.
     * @return true if success to load Sale from ID; otherwise false.
     */
    public boolean setCurrentSale(int id) {
        currentSale = saleDao.getSaleById(id);
        return false;
    }

    /**
     * Determines that if there is a Sale handling or not.
     * @return true if there is a current Sale; otherwise false.
     */
    public boolean hasSale(){
        if(currentSale == null)return false;
        return true;
    }

    /**
     * Cancels the current Sale.
     */
    public void cancleSale() {
        if (currentSale != null){
            saleDao.cancelSale(currentSale,DateTimeSettings.getCurrentTime());
            currentSale = null;
        }
    }

    /**
     * Edit the specific LineItem
     * @param saleId ID of LineItem to be edited.
     * @param lineItem LineItem to be edited.
     * @param quantity a new quantity to set.
     * @param priceAtSale a new priceAtSale to set.
     */
    public void updateItem(int saleId, LineItem lineItem, int quantity, double priceAtSale) {
        lineItem.setUnitPriceAtSale(priceAtSale);
        lineItem.setQuantity(quantity);
        saleDao.updateLineItem(saleId, lineItem);
    }

    /**
     * Removes LineItem from the current Sale.
     * @param lineItem lineItem to be removed.
     */
    public void removeItem(LineItem lineItem) {
        saleDao.removeLineItem(lineItem.getId());
        currentSale.removeItem(lineItem);
        if (currentSale.getAllLineItem().isEmpty()) {
            cancleSale();
        }
    }

}
