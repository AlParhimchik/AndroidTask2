package com.example.sashok.clientapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sashok on 23.3.17.
 */

public class CustomerInfoDialog extends AlertDialog {
    LinearLayout root_layout;
    Context ctx;
    Customer customer;
    TextView example_textView;

    protected CustomerInfoDialog(final Context context, final Customer customer) {
        super(context);
        ctx = context;
        this.customer = customer;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.customer_info, null);
        setView(view);
        example_textView = (TextView) view.findViewById(R.id.textview);
        example_textView.setText("id: " + customer.getId());
        String name = "";
        if (customer.getFirstName() != null) name = customer.getFirstName();
        if (customer.getLastName() != null) name += " " + customer.getLastName();
        setTitle("Информация о " + name);
        example_textView.setText(customer.showInformation());
    }

}
