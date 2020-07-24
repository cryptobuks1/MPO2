package com.example.mpo2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UI for Sale operation.
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class SalesFragment extends UpdateFragment {

    private Registers register;
    private ArrayList<Map<String, String>> saleList;
    private ListView saleListView;
    private Button clearButton;
    private TextView totalPrice;
    private Button endButton;
    private UpdateFragment reportFragment;
    private Resources res;

    /**
     * Construct a new SaleFragment.
     * @param
     */
    public SalesFragment(UpdateFragment reportFragment) {
        super();
        this.reportFragment = reportFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            register = Registers.getInstance();
        } catch (DaoNoSetException e) {
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.layout_sales, container, false);

        res = getResources();
        saleListView = (ListView) view.findViewById(R.id.sale_List);
        totalPrice = (TextView) view.findViewById(R.id.totalPrice);
        clearButton = (Button) view.findViewById(R.id.clearButton);
        endButton = (Button) view.findViewById(R.id.endButton);

        initUI();
        return view;
    }

    /**
     * Initiate this UI.
     */
    private void initUI() {

        saleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                showEditPopup(arg1,arg2);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = ((MainActivity) getActivity()).getViewPager();
                viewPager.setCurrentItem(1);
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(register.hasSale()){
                    showPopup(v);
                } else {
                    Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!register.hasSale() || register.getCurrentSale().getAllLineItem().isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
                } else {
                    showConfirmClearDialog();
                }
            }
        });
    }

    /**
            * Show list
	 * @param list
	 */
    private void showList(List<LineItem> list) {

        saleList = new ArrayList<Map<String, String>>();
        for(LineItem line : list) {
            saleList.add(line.toMap());
        }

        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(getActivity().getBaseContext(), saleList,
                R.layout.listview_lineproduct, new String[]{"name","quantity","price"}, new int[] {R.id.name,R.id.quantity,R.id.price});
        saleListView.setAdapter(sAdap);
    }

    /**
     * Try parsing String to double.
     * @param value
     * @return true if can parse to double.
     */
    public boolean tryParseDouble(String value)
    {
        try  {
            Double.parseDouble(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    /**
     * Show edit popup.
     * @param anchorView
     * @param position
     */
    public void showEditPopup(View anchorView,int position){
        Bundle bundle = new Bundle();
        bundle.putString("position",position+"");
        bundle.putString("sale_id",register.getCurrentSale().getId()+"");
        bundle.putString("product_id",register.getCurrentSale().getLineItemAt(position).getItem().getId()+"");

        EditingFragmentDialog newFragment = new EditingFragmentDialog(SalesFragment.this, reportFragment);
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "");

    }

    /**
     * Show popup
     * @param anchorView
     */
    public void showPopup(View anchorView) {
        Bundle bundle = new Bundle();
        bundle.putString("edttext", totalPrice.getText().toString());
        PaymentsFragmentDialog newFragment = new PaymentsFragmentDialog(SalesFragment.this, reportFragment);
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "");
    }

    @Override
    /*public void update() { //NIKNIK
        if(register.hasSale()){
            showList(register.getCurrentSale().getAllLineItem());
            totalPrice.setText(register.getTotal() + "");
        }
        else{
            showList(new ArrayList<LineItem>());
            totalPrice.setText("0.00");
        }
    }*/


    public void update() {

    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    /**
     * Show confirm or clear dialog.
     */
    private void showConfirmClearDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(res.getString(R.string.dialog_clear_sale));
        dialog.setPositiveButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.setNegativeButton(res.getString(R.string.clear), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                register.cancleSale();
                update();
            }
        });

        dialog.show();
    }





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SalesFragment() {
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
    public static SalesFragment newInstance(String param1, String param2) {
        SalesFragment fragment = new SalesFragment();
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
