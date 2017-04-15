package com.example.sashok.clientapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import rx.Observer;

/**
 * Created by sashok on 7.4.17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyHolder> {
    public List<Customer> customers;
    private MainActivity activity;
    Observer<Customer> mySubscriber;

    public CustomerAdapter(MainActivity activity, final List<Customer> customers) {
        this.activity = activity;
        this.customers = customers;
        mySubscriber = new Observer<Customer>() {
            @Override
            public void onNext(Customer s) {
                int pos = findById(s);
                if (pos != -1) {
                    customers.set(pos, s);
                    notifyItemChanged(pos);
                }

            }
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                Log.i("TAG","error ");
            }
        };
    }

    private int findById(Customer s) {
        for (Customer customer : customers
                ) {
            if (customer.getId() == s.getId()) {
                return customers.indexOf(customer);
            }

        }
        return -1;
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
                CustomerEditDialog dialog = new CustomerEditDialog(activity, mySubscriber, customer);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public interface EditCostumer {

        public void onCostumeredit(Customer costumer);
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