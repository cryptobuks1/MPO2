package com.example.mpo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainMenu extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private TabItem inventoryfragment, salesfragment, invoicingfragment;
    private Resources res;
    public PagerAdapter pagerAdapter;


    private ItemCatalog productCatalog;
    private String productId;
    private Item product;
    private static boolean SDK_SUPPORTED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        tablayout = (TabLayout) findViewById(R.id.tablayout);
        inventoryfragment = (TabItem) findViewById(R.id.inventory);
        salesfragment = (TabItem) findViewById(R.id.sales);
        invoicingfragment = (TabItem) findViewById(R.id.invoicing);
        viewPager = findViewById(R.id.viewpager);

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                }  else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                }  else if (tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            public void onTabUnselected(TabLayout.Tab tab){

            }

            public void onTabReselected(TabLayout.Tab tab){

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

    }

    /**
     * Get view-pager
     * @return
     */
    public ViewPager getViewPager() {
        return viewPager;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            openQuitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    /**
     * Open quit dialog.
     */
    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainMenu.this);
        quitDialog.setTitle(res.getString(R.string.dialog_quit));
        quitDialog.setPositiveButton(res.getString(R.string.quit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        quitDialog.show();
    }


    /**
     * Option on-click handler.
     * @param view
     */
    public void optionOnClickHandler(View view) {
        viewPager.setCurrentItem(0);
        String id = view.getTag().toString();
        productId = id;
        try {
            productCatalog = Inventory.getInstance().getProductCatalog();
        } catch (DaoNoSetException e) {
            e.printStackTrace();
        }
        product = productCatalog.getProductById(Integer.parseInt(productId));
        openDetailDialog();

    }





    /**
     * Open detail dialog.
     */
    private void openDetailDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(MainMenu.this);
        //quitDialog.setTitle(Item.getName()); NIKNIK
        quitDialog.setPositiveButton(res.getString(R.string.remove), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                openRemoveDialog();
            }
        });

        quitDialog.setNegativeButton(res.getString(R.string.product_detail), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent newActivity = new Intent(MainMenu.this,
                        ItemDetailActivity.class);
                newActivity.putExtra("id", productId);
                startActivity(newActivity);
            }
        });

        quitDialog.show();
    }












    /**
     * Open remove dialog.
     */
    private void openRemoveDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainMenu.this);
        quitDialog.setTitle(res.getString(R.string.dialog_remove_product));
        quitDialog.setPositiveButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.setNegativeButton(res.getString(R.string.remove), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ItemCatalog.suspendproduct() NIKNIK
                //pagerAdapter.update(0);
            }
        });

        quitDialog.show();
    }

}
