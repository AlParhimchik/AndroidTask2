package com.example.sashok.clienttask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sashok on 7.4.17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyHolder> {
    public List<Customer> customers;
    private MainActivity activity;
    private MainActivity.EditCostumer listener;

    public CustomerAdapter(MainActivity activity, List<Customer> customers, MainActivity.EditCostumer listener) {
        this.activity = activity;
        this.customers = customers;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customers_list, parent, false);
        MyHolder pvh = new MyHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final Customer customer = customers.get(position);
        String name;
        name = (customer.getFirstName() != null ? customer.getFirstName() : "");
        name += " " + (customer.getLastName() != null ? customer.getLastName() : "");
        holder.customer_name.setText(name);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerInfoDialog dialog = new CustomerInfoDialog(activity, customer);
                dialog.show();
            }
        });
        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerEditDialog dialog = new CustomerEditDialog(activity, listener, customer);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView customer_name;
        RelativeLayout layout;
        ImageView edit_btn;

        public MyHolder(View itemView) {
            super(itemView);
            customer_name = (TextView) itemView.findViewById(R.id.customer_name);
            layout = (RelativeLayout) itemView.findViewById(R.id.search_layout);
            edit_btn = (ImageView) itemView.findViewById(R.id.edit_btn);
        }
    }


}