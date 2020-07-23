package com.example.mpo2;

/**
 * Uses to directly access to database.
 *
 * @author Refresh Team
 *
 */
public class DbExecutor {

    private static Database database;
    private static DbExecutor instance;

    private DbExecutor() {

    }

    /**
     * Sets database for use in DatabaseExecutor.
     * @param db database.
     */
    public static void setDatabase(Database db) {
        database = db;
    }

    public static DbExecutor getInstance() {
        if (instance == null)
            instance = new DbExecutor();
        return instance;
    }

    /**
     * Drops all data in database.
     */
    public void dropAllData() {
        execute("DELETE FROM " + DbContent.TABLE_PRODUCT_CATALOG + ";");
        execute("DELETE FROM " + DbContent.TABLE_SALE + ";");
        execute("DELETE FROM " + DbContent.TABLE_SALE_LINEITEM + ";");
        execute("DELETE FROM " + DbContent.TABLE_STOCK + ";");
        execute("DELETE FROM " + DbContent.TABLE_STOCK_SUM + ";");
        execute("VACUUM;");
    }

    /**
     * Directly execute to database.
     * @param queryString query string for execute.
     */
    private void execute(String queryString) {
        database.execute(queryString);
    }


}
