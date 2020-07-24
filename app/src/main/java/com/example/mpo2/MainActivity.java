package com.example.mpo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private TabItem inventoryfragment, salesfragment, invoicingfragment;
    public PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


}

