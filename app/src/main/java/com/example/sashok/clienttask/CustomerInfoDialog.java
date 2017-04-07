package com.example.sashok.clienttask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        example_textView.setText("id: "+customer.customer_id);
        String name="";
        if (customer.firstName!=null) name=customer.firstName;
        if (customer.lastName!=null) name+=" "+customer.lastName;
        setTitle("Информация о "+name);
        setValues(view);
    }


    public void setValues(final View view) {
        if (customer.site != null) addToView(view, "site" + customer.site);
        if (customer.vip != null) addToView(view, "vip: " + customer.vip);
        if (customer.bad != null) addToView(view, "bad: " + customer.bad);
        if (customer.email != null) addToView(view, "email: " + customer.email);
        if (customer.createDate != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportDate = df.format(customer.createDate);
            addToView(view, "date: " + reportDate);
        }
        if (customer.manager_id != 0) addToView(view, "managerId: " + customer.manager_id);
        if (customer.phones.size() != 0) {
            for (String phone : customer.phones
                    ) {
                addToView(view, "phone: " + phone);
            }
        }
    }
    public static float dipToPixels(Context context, float dipValue){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  dipValue, metrics);
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
