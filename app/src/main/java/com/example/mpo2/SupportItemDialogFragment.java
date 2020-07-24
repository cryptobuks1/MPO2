package com.example.mpo2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.integration.android.IntentIntegratorSupportV4;

import androidx.fragment.app.DialogFragment;

/**
 * A dialog of adding a Product.
 *
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class SupportItemDialogFragment extends DialogFragment {

    private EditText barcodeBox;
    private ItemCatalog itemCatalog;
    private Button scanButton;
    private EditText priceBox;
    private EditText nameBox;
    private Button confirmButton;
    private Button clearButton;
    private UpdateFragment fragment;
    private Resources res;

    /**
     * Construct a new AddProductDialogFragment
     * @param fragment
     */
    public SupportItemDialogFragment(UpdateFragment fragment) {

        super();
        this.fragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            itemCatalog = Stock.getInstance().getProductCatalog();
        } catch (DaoNoSetException e) {
            e.printStackTrace();
        }

        View v = inflater.inflate(R.layout.layout_additem, container,
                false);

        res = getResources();

        barcodeBox = (EditText) v.findViewById(R.id.barcodeBox);
        scanButton = (Button) v.findViewById(R.id.scanButton);
        priceBox = (EditText) v.findViewById(R.id.priceBox);
        nameBox = (EditText) v.findViewById(R.id.nameBox);
        confirmButton = (Button) v.findViewById(R.id.confirmButton);
        clearButton = (Button) v.findViewById(R.id.clearButton);

        initUI();
        return v;
    }

    /**
     * Construct a new
     */
    private void initUI() {
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(SupportItemDialogFragment.this); //NIKNIK
                scanIntegrator.initiateScan();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nameBox.getText().toString().equals("")
                        || barcodeBox.getText().toString().equals("")
                        || priceBox.getText().toString().equals("")) {

                    Toast.makeText(getActivity().getBaseContext(),
                            res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
                            .show();

                } else {
                    boolean success = itemCatalog.addItem(nameBox
                            .getText().toString(), barcodeBox.getText()
                            .toString(), Double.parseDouble(priceBox.getText()
                            .toString()));

                    if (success) {
                        Toast.makeText(getActivity().getBaseContext(),
                                res.getString(R.string.success) + ", "
                                        + nameBox.getText().toString(),
                                Toast.LENGTH_SHORT).show();

                        fragment.update();
                        clearAllBox();
                        SupportItemDialogFragment.this.dismiss();

                    } else {
                        Toast.makeText(getActivity().getBaseContext(),
                                res.getString(R.string.fail),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(barcodeBox.getText().toString().equals("") && nameBox.getText().toString().equals("") && priceBox.getText().toString().equals("")){
                    SupportItemDialogFragment.this.dismiss();
                } else {
                    clearAllBox();
                }
            }
        });
    }

    /**
     * Clear all box
     */
    private void clearAllBox() {
        barcodeBox.setText("");
        nameBox.setText("");
        priceBox.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, intent);

        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            barcodeBox.setText(scanContent);
        } else {
            Toast.makeText(getActivity().getBaseContext(),
                    res.getString(R.string.fail),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
