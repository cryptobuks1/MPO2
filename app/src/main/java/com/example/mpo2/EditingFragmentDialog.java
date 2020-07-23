package com.example.mpo2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

@SuppressLint("ValidFragment")
public class EditingFragmentDialog extends DialogFragment {
    private Registers registers;
    private UpdateFragment saleFragment;
    private UpdateFragment reportFragment;
    private EditText quantityBox;
    private EditText priceBox;
    private Button comfirmButton;
    private String saleId;
    private String position;
    private LineItem lineItem;
    private Button removeButton;

    /**
     * Construct a new  EditFragmentDialog.
     * @param saleFragment
     * @param reportFragment
     */
    public EditingFragmentDialog(UpdateFragment saleFragment, UpdateFragment reportFragment) {
        super();
        this.saleFragment = saleFragment;
        this.reportFragment = reportFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_saleediting, container, false);
        try {
            registers = Registers.getInstance();
        } catch (DaoNoSetException e) {
            e.printStackTrace();
        }

        quantityBox = (EditText) v.findViewById(R.id.quantityBox);
        priceBox = (EditText) v.findViewById(R.id.priceBox);
        comfirmButton = (Button) v.findViewById(R.id.confirmButton);
        removeButton = (Button) v.findViewById(R.id.removeButton);

        saleId = getArguments().getString("sale_id");
        position = getArguments().getString("position");

        lineItem = registers.getCurrentSale().getLineItemAt(Integer.parseInt(position));
        quantityBox.setText(lineItem.getQuantity()+"");
        priceBox.setText(lineItem.getItem().getUnitPrice()+"");
        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Log.d("remove", "id=" + lineItem.getId());
                registers.removeItem(lineItem);
                end();
            }
        });

        comfirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registers.updateItem(
                        Integer.parseInt(saleId),
                        lineItem,
                        Integer.parseInt(quantityBox.getText().toString()),
                        Double.parseDouble(priceBox.getText().toString())
                );

                end();
            }

        });
        return v;
    }

    /**
     * End.
     */
    private void end(){
        saleFragment.update();
        reportFragment.update();
        this.dismiss();
    }


}
