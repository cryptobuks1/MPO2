package com.example.mpo2;

import java.util.Calendar;
import java.util.List;

/**
 * Book that keeps sale record.
 *
 * @author Refresh Team
 *
 */
public class SalesLedger {

    private static SalesLedger instance = null;
    private static SalesDao saleDao = null;

    private SalesLedger() throws DaoNoSetException {
        if (!isDaoSet()) {
            throw new DaoNoSetException();
        }
    }

    /**
     * Determines whether the DAO already set or not.
     * @return true if the DAO already set; otherwise false.
     */
    public static boolean isDaoSet() {
        return saleDao != null;
    }

    public static SalesLedger getInstance() throws DaoNoSetException {
        if (instance == null) instance = new SalesLedger();
        return instance;
    }

    /**
     * Sets the database connector.
     * @param dao Data Access Object of Sale.
     */
    public static void setSalesDao(SalesDao dao) {
        saleDao = dao;
    }

    /**
     * Returns all sale in the records.
     * @return all sale in the records.
     */
    public List<Sales> getAllSale() {
        return saleDao.getAllSale();
    }

    /**
     * Returns the Sale with specific ID.
     * @param id ID of specific Sale.
     * @return the Sale with specific ID.
     */
    public Sales getSaleById(int id) {
        return saleDao.getSaleById(id);
    }

    /**
     * Clear all records in SaleLedger.
     */
    public void clearSaleLedger() {
        saleDao.clearSaleLedger();
    }

    /**
     * Returns list of Sale with scope of time.
     * @param start start bound of scope.
     * @param end end bound of scope.
     * @return list of Sale with scope of time.
     */
    public List<Sales> getAllSaleDuring(Calendar start, Calendar end) {
        return saleDao.getAllSaleDuring(start, end);
    }
}
