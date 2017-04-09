package com.example.sashok.clienttask;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by sashok on 23.3.17.
 */

public class CustomerInfoDialog extends AlertDialog  {
    LinearLayout root_layout;
    Context ctx;
    Customer customer;
    TextView example_textView;
    protected CustomerInfoDialog(final Context context, final Customer customer)
    {
        super(context);
        ctx=context;
        this.customer=customer;
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.customer_info, null);
        setView(view);
        example_textView=(TextView)view.findViewById(R.id.textview);
        example_textView.setText("id: "+ customer.getCustomerId());
        String name="";
        if (customer.getFirstName() !=null) name= customer.getFirstName();
        if (customer.getLastName() !=null) name+=" "+ customer.getLastName();
        setTitle("Информация о "+name);
        setValues(view);
    }


    public void setValues(final View view) {
        if (customer.getSite() != null) addToView(view, "site" + customer.getSite());
        if (customer.getVip() != null) addToView(view, "vip: " + customer.getVip());
        if (customer.getBad() != null) addToView(view, "bad: " + customer.getBad());
        if (customer.getEmail() != null) addToView(view, "email: " + customer.getEmail());
        if (customer.getCreateDate() != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportDate = df.format(customer.getCreateDate());
            addToView(view, "date: " + reportDate);
        }
        if (customer.getManagerId() != 0) addToView(view, "managerId: " + customer.getManagerId());
        if (customer.getPhones().size() != 0) {
            for (String phone : customer.getPhones()
                    ) {
                addToView(view, "phone: " + phone);
            }
        }
    }

    public void addToView(View main_view,String text){
        root_layout=(LinearLayout)main_view.findViewById(R.id.root_layout);

        TextView textView=new TextView(ctx);
        textView.setText(text);
        textView.setTextSize(25);
        //
        textView.setTextColor(example_textView.getCurrentTextColor());
        ViewGroup.LayoutParams lp = example_textView.getLayoutParams();
        root_layout.addView(textView,lp);
    }


}
