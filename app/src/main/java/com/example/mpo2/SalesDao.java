package com.example.mpo2;

import java.util.Calendar;
import java.util.List;

/**
 * DAO for Sale process.
 *
 * @author Refresh Team
 *
 */
public interface SalesDao {

    /**
     * Initiates a new Sale.
     * @param startTime time that Sale initiated.
     * @return Sale that initiated
     */
    Sales initiateSale(String startTime);

    /**
     * End Sale
     * @param sale Sale to be ended.
     * @param endTime time that Sale ended.
     */
    void endSale(Sales sale, String endTime);

    /**
     * Add LineItem to Sale.
     * @param saleId ID of the Sale to add LineItem.
     * @param lineItem LineItem to be added.
     * @return ID of LineItem that just added.
     */
    int addLineItem(int saleId, LineItem lineItem);

    /**
     * Returns all sale in the records.
     * @return all sale in the records.
     */
    List<Sales> getAllSale();

    /**
     * Returns the Sale with specific ID.
     * @param id ID of specific Sale.
     * @return the Sale with specific ID.
     */
    Sales getSaleById(int id);

    /**
     * Clear all records in SaleLedger.
     */
    void clearSaleLedger();

    /**
     * Returns list of LineItem that belong to Sale with specific Sale ID.
     * @param saleId ID of sale.
     * @return list of LineItem that belong to Sale with specific Sale ID.
     */
    List<LineItem> getLineItem(int saleId);

    /**
     * Updates the data of specific LineItem.
     * @param saleId ID of Sale that this LineItem belong to.
     * @param lineItem to be updated.
     */
    void updateLineItem(int saleId, LineItem lineItem);

    /**
     * Returns list of Sale with scope of time.
     * @param start start bound of scope.
     * @param end end bound of scope.
     * @return list of Sale with scope of time.
     */
    List<Sales> getAllSaleDuring(Calendar start, Calendar end);

    /**
     * Cancel the Sale.
     * @param sale Sale to be cancel.
     * @param endTime time that cancelled.
     */
    void cancelSale(Sales sale,String endTime);

    /**
     * Removes LineItem.
     * @param id of LineItem to be removed.
     */
    void removeLineItem(int id);


}
