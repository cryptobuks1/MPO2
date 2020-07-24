package com.example.mpo2;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UI for showing the detail of Sale in the record.
 * @author Refresh Team
 *
 */
public class SalesDetailActivity extends Activity {

    private TextView totalBox;
    private TextView dateBox;
    private ListView lineitemListView;
    private List<Map<String, String>> lineitemList;
    private Sales sales;
    private int saleId;
    private SalesLedger salesLedger;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        try {
            salesLedger = SalesLedger.getInstance();
        } catch (DaoNoSetException e) {
            e.printStackTrace();
        }

        saleId = Integer.parseInt(getIntent().getStringExtra("id"));
        sales = salesLedger.getSaleById(saleId);

        initUI(savedInstanceState);
    }


    /**
     * Initiate actionbar.
     */
    @SuppressLint("NewApi")
    private void initiateActionBar() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.sales));
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));
        }
    }


    /**
     * Initiate this UI.
     * @param savedInstanceState
     */
    private void initUI(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_salesdetails);

        initiateActionBar();

        totalBox = (TextView) findViewById(R.id.totalBox);
        dateBox = (TextView) findViewById(R.id.dateBox);
        lineitemListView = (ListView) findViewById(R.id.lineitemList);
    }

    /**
     * Show list.
     * @param list
     */
    private void showList(List<LineItem> list) {
        lineitemList = new ArrayList<Map<String, String>>();
        for(LineItem line : list) {
            lineitemList.add(line.toMap());
        }

        SimpleAdapter sAdap = new SimpleAdapter(SalesDetailActivity.this, lineitemList,
                R.layout.listview_lineproduct, new String[]{"name","quantity","price"}, new int[] {R.id.name,R.id.quantity,R.id.price});
        lineitemListView.setAdapter(sAdap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Update UI.
     */
    public void update() {
        totalBox.setText(sales.getTotal() + "");
        dateBox.setText(sales.getEndTime() + "");
        showList(sales.getAllLineItem());
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
}
