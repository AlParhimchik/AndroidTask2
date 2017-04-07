package com.example.sashok.clienttask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sashok on 7.4.17.
 */

public class customerAdapter extends RecyclerView.Adapter<customerAdapter.MyHolder> {
    private MainActivity activity;
    public List<Customer> customers;
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customers_list, parent, false);
        MyHolder pvh = new MyHolder(v);
        return pvh;
    }


    public  customerAdapter(MainActivity activity, List<Customer> customers){
        this.activity = activity;
        this.customers=customers;
    }

    public class MyHolder  extends RecyclerView.ViewHolder{
        TextView customer_name;
        RelativeLayout layout;
        public MyHolder(View itemView) {
            super(itemView);
            customer_name = (TextView) itemView.findViewById(R.id.customer_name);
            layout=(RelativeLayout) itemView.findViewById(R.id.search_layout);
        }
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final Customer customer  = customers.get(position);
        String name="";
        if (customer.firstName!=null) name=customer.firstName;
        if (customer.lastName!=null) name+=" "+customer.lastName;
         holder.customer_name.setText(name);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerInfoDialog dialog=new CustomerInfoDialog(activity,customer);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }


}