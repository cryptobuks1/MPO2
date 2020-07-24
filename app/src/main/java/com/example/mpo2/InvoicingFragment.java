package com.example.mpo2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * UI for showing sale's record.
 * @author Refresh Team
 *
 */
public class InvoicingFragment extends UpdateFragment {

    private SalesLedger saleLedger;
    List<Map<String, String>> saleList;
    private ListView saleLedgerListView;
    private TextView totalBox;
    private Spinner spinner;
    private Button previousButton;
    private Button nextButton;
    private TextView currentBox;
    private Calendar currentTime;
    private DatePickerDialog datePicker;

    public static final int DAILY = 0;
    public static final int WEEKLY = 1;
    public static final int MONTHLY = 2;
    public static final int YEARLY = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            saleLedger = SalesLedger.getInstance();
        } catch (DaoNoSetException e) {
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.layout_invoicing, container, false);

        previousButton = (Button) view.findViewById(R.id.previousButton);
        nextButton = (Button) view.findViewById(R.id.nextButton);
        currentBox = (TextView) view.findViewById(R.id.currentBox);
        saleLedgerListView = (ListView) view.findViewById(R.id.saleListView);
        totalBox = (TextView) view.findViewById(R.id.totalBox);
        spinner = (Spinner) view.findViewById(R.id.spinner1);

        initUI();
        return view;
    }

    /**
     * Initiate this UI.
     */
    private void initUI() {
        currentTime = Calendar.getInstance();
        datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                currentTime.set(Calendar.YEAR, y);
                currentTime.set(Calendar.MONTH, m);
                currentTime.set(Calendar.DAY_OF_MONTH, d);
                update();
            }
        }, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(Calendar.DAY_OF_MONTH));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.period, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

        currentBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });



        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate(-1);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate(1);
            }
        });

        saleLedgerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
                String id = saleList.get(position).get("id").toString();
                Intent newActivity = new Intent(getActivity().getBaseContext(), SalesDetailActivity.class);
                newActivity.putExtra("id", id);
                startActivity(newActivity);
            }
        });

    }

    /**
     * Show list.
     * @param list
     */
    private void showList(List<Sales> list) {

        saleList = new ArrayList<Map<String, String>>();
        for (Sales sale : list) {
            saleList.add(sale.toMap());
        }

        SimpleAdapter sAdap = new SimpleAdapter(getActivity().getBaseContext() , saleList,
                R.layout.listview_invoicing, new String[] { "id", "startTime", "total"},
                new int[] { R.id.sid, R.id.startTime , R.id.total});
        saleLedgerListView.setAdapter(sAdap);
    }

    @Override
    public void update() {
        int period = spinner.getSelectedItemPosition();
        List<Sales> list = null;
        Calendar cTime = (Calendar) currentTime.clone();
        Calendar eTime = (Calendar) currentTime.clone();

        /**if(period == DAILY){ //NIKNIK
            currentBox.setText(" [" + DateTimeSettings.getSQLDateFormat(currentTime) +  "] "); NIKNIK
            currentBox.setTextSize(16);
        } else if (period == WEEKLY){
            while(cTime.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                cTime.add(Calendar.DATE, -1);
            }

            String toShow = " [" + DateTimeSettings.getSQLDateFormat(cTime) +  "] ~ [";
            eTime = (Calendar) cTime.clone();
            eTime.add(Calendar.DATE, 7);
            toShow += DateTimeSettings.getSQLDateFormat(eTime) +  "] ";
            currentBox.setTextSize(16);
            currentBox.setText(toShow);
        } else if (period == MONTHLY){
            cTime.set(Calendar.DATE, 1);
            eTime = (Calendar) cTime.clone();
            eTime.add(Calendar.MONTH, 1);
            eTime.add(Calendar.DATE, -1);
            currentBox.setTextSize(18);
            currentBox.setText(" [" + currentTime.get(Calendar.YEAR) + "-" + (currentTime.get(Calendar.MONTH)+1) + "] ");
        } else if (period == YEARLY){
            cTime.set(Calendar.DATE, 1);
            cTime.set(Calendar.MONTH, 0);
            eTime = (Calendar) cTime.clone();
            eTime.add(Calendar.YEAR, 1);
            eTime.add(Calendar.DATE, -1);
            currentBox.setTextSize(20);
            currentBox.setText(" [" + currentTime.get(Calendar.YEAR) +  "] ");
        }
        currentTime = cTime;
        list = saleLedger.getAllSaleDuring(cTime, eTime);
        double total = 0;
        for (Sales sale : list)
            total += sale.getTotal();

        totalBox.setText(total + "");
        showList(list);**/
    }

    @Override
    public void onResume() {
        super.onResume();
        // update();
        // it shouldn't call update() anymore. Because super.onResume()
        // already fired the action of spinner.onItemSelected()
    }

    /**
     * Add date.
     * @param increment
     */
    private void addDate(int increment) {
        int period = spinner.getSelectedItemPosition();
        if (period == DAILY){
            currentTime.add(Calendar.DATE, 1 * increment);
        } else if (period == WEEKLY){
            currentTime.add(Calendar.DATE, 7 * increment);
        } else if (period == MONTHLY){
            currentTime.add(Calendar.MONTH, 1 * increment);
        } else if (period == YEARLY){
            currentTime.add(Calendar.YEAR, 1 * increment);
        }
        update();
    }








    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Invoicing.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoicingFragment newInstance(String param1, String param2) {
        InvoicingFragment fragment = new InvoicingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InvoicingFragment() {
        // Required empty public constructor
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