package com.example.mpo2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

/**
 * A dialog shows the total change and confirmation for Sale.
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class EndingPaymentsFragmentDialog extends DialogFragment {

    private Button doneButton;
    private TextView chg;
    private Registers regis;
    private UpdateFragment saleFragment;
    private UpdateFragment reportFragment;

    /**
     * End this UI.
     * @param saleFragment
     * @param reportFragment
     */
    public EndingPaymentsFragmentDialog(UpdateFragment saleFragment, UpdateFragment reportFragment) {
        super();
        this.saleFragment = saleFragment;
        this.reportFragment = reportFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            regis = Registers.getInstance();
        } catch (DaoNoSetException e) {
            e.printStackTrace();
        }

        View v = inflater.inflate(R.layout.dialog_paymentfollowing, container,false);
        String strtext=getArguments().getString("edttext");
        chg = (TextView) v.findViewById(R.id.changeTxt);
        chg.setText(strtext);
        doneButton = (Button) v.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                end();
            }
        });

        return v;
    }

    /**
     * End
     */
    private void end(){
        regis.endSale(DateTimeSettings.getCurrentTime());
        saleFragment.update();
        reportFragment.update();
        this.dismiss();
    }

}
