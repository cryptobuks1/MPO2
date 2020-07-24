package com.example.mpo2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentIntegratorSupportV4;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UI for Inventory, shows list of Product in the ProductCatalog.
 * Also use for a sale process of adding Product into sale.
 *
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class InventoryFragment extends UpdateFragment {

    protected static final int SEARCH_LIMIT = 0;
    private ListView inventoryListView;
    private ItemCatalog productCatalog;
    private List<Map<String, String>> inventoryList;
    private Button addProductButton;
    private EditText searchBox;
    private Button scanButton;

    private ViewPager viewPager;
    private Registers register;
    private MainActivity main;

    private UpdateFragment saleFragment;
    private Resources res;

    /**
     * Construct a new InventoryFragment.
     * @param saleFragment
     */
    public InventoryFragment(UpdateFragment saleFragment) {
        super();
        this.saleFragment = saleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            productCatalog = Inventory.getInstance().getProductCatalog();
            register = Registers.getInstance();
        } catch (DaoNoSetException e) {
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.layout_stock, container, false);

        res = getResources();
        inventoryListView = (ListView) view.findViewById(R.id.productListView);
        addProductButton = (Button) view.findViewById(R.id.addProductButton);
        scanButton = (Button) view.findViewById(R.id.scanButton);
        searchBox = (EditText) view.findViewById(R.id.searchBox);

        main = (MainActivity) getActivity();
        viewPager = main.getViewPager();

        initUI();
        return view;
    }

    /**
     * Initiate this UI.
     */
    private void initUI() {

        addProductButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showPopup(v);
            }
        });

        searchBox.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if (s.length() >= SEARCH_LIMIT) {
                    search();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
                int id = Integer.parseInt(inventoryList.get(position).get("id").toString());

                register.addItem(productCatalog.getProductById(id), 1);
                saleFragment.update();
                viewPager.setCurrentItem(1);
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(InventoryFragment.this);
                scanIntegrator.initiateScan();
            }
        });

    }

    /**
     * Show list.
     * @param list
     */
    private void showList(List<Item> list) {

        inventoryList = new ArrayList<Map<String, String>>();
        for(Item product : list) {
            inventoryList.add(product.toMap());
        }

        AdaptButtons sAdap = new AdaptButtons(getActivity().getBaseContext(), inventoryList,
                R.layout.listview_inventory, new String[]{"name"}, new int[] {R.id.name}, R.id.optionView, "id");
        inventoryListView.setAdapter(sAdap);
    }

    /**
     * Search.
     */
    private void search() {
        String search = searchBox.getText().toString();

        /*if (search.equals("/demo")) {
            testAddProduct();
            searchBox.setText("");
        } else NIKNIK*/

            if (search.equals("/clear")) {
            DbExecutor.getInstance().dropAllData();
            searchBox.setText("");
        }
        /*else if (search.equals("")) {
            showList(productCatalog.getAllProduct());
        } NIKNIK else  {
            List<Item> result = productCatalog.searchProduct(search);
            showList(result);
            if (result.isEmpty()) {

            }
        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, intent);

        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            searchBox.setText(scanContent);
        } else {
            Toast.makeText(getActivity().getBaseContext(), res.getString(R.string.fail),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /** //NIKNIK
     * Test adding product

    protected void testAddProduct() {
        Demo.testProduct(getActivity());
        Toast.makeText(getActivity().getBaseContext(), res.getString(R.string.success),
                Toast.LENGTH_SHORT).show();
    }*/

    /**
     * Show popup.
     * @param anchorView
     */
    public void showPopup(View anchorView) {
        SupportItemDialogFragment newFragment = new SupportItemDialogFragment(InventoryFragment.this);
        newFragment.show(getFragmentManager(), "");
    }

    @Override
    public void update() {
        search();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }





















    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InventoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inventory.
     */
    // TODO: Rename and change types and number of parameters
    public static InventoryFragment newInstance(String param1, String param2) {
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

}
