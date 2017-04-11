package com.example.sashok.clienttask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sashok on 23.3.17.
 */

public class CustomerEditDialog extends AlertDialog implements View.OnFocusChangeListener {
    EditText edit_field;
    TextView edit_name;
    LinearLayout root_layout;
    Context ctx;
    Customer customer;
    MainActivity.EditCostumer listener;
    ProgressDialog pd;

    protected CustomerEditDialog(final Context context, MainActivity.EditCostumer listener, final Customer customer) {
        super(context);
        ctx = context;
        this.customer = customer;
        this.listener = listener;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.edit_customer, null);
        setView(view);
        fillFields(view);
        TextView cancel_btn = (TextView) view.findViewById(R.id.cancel_button);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView sumbit_btn = (TextView) view.findViewById(R.id.sumbit_btn);
        sumbit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < root_layout.getChildCount(); i++) {
                    LinearLayout layout = (LinearLayout) root_layout.getChildAt(i);
                    View text = layout.getChildAt(1);
                    if (text instanceof EditText) {
                        EditText t = (EditText) text;
                        switch (i) {
                            case 0:
                                customer.setFirstName(t.getText().toString());
                                break;
                            case 1:
                                customer.setLastName(t.getText().toString());
                                break;
                            case 2:
                                customer.setEmail(t.getText().toString());
                                break;
                            case 3:
                                customer.setSite(t.getText().toString());
                                break;
                            case 4: {
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                try {
                                    customer.setCreateDate(format.parse(t.getText().toString()));
                                } catch (ParseException e) {
                                    e.getMessage();
                                }
                                break;
                            }
                            case 5:
                                customer.setManagerId((Integer.parseInt(t.getText().toString())));
                                break;
                        }

                    }
                }
                pd = new ProgressDialog(ctx);
                pd.setMessage("Оправляем изменения на сервер");
                pd.setCancelable(false);
                pd.show();

                SendChanges op = new SendChanges();
                op.execute(customer);

            }
        });
        setTitle(R.string.change_title);
    }

    public void addNewEditText(View view, String text, String fields_name) {
        LinearLayout new_layout = new LinearLayout(ctx);
        new_layout.setLayoutParams(view.findViewById(R.id.edit_layout).getLayoutParams());

        TextView textView = new TextView(ctx);
        textView.setText(fields_name);
        textView.setTextSize(20);
        ViewGroup.LayoutParams lp_tv = edit_name.getLayoutParams();
        textView.setTextColor(ContextCompat.getColor(ctx, R.color.primary_text));
        new_layout.addView(textView, lp_tv);

        EditText editText = new EditText(ctx);
        if (text != null) editText.setText(text);
        ViewGroup.LayoutParams lp = edit_field.getLayoutParams();
        editText.setHintTextColor(edit_field.getCurrentHintTextColor());
        editText.setBackgroundResource(R.drawable.edit_text_style);
        editText.setHint(text);
        editText.setOnFocusChangeListener(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        new_layout.addView(editText, lp);
        root_layout.addView(new_layout);
    }

    private void fillFields(final View root) {
        root_layout = (LinearLayout) root.findViewById(R.id.edit_field_layout);
        root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_name.requestFocus();
            }
        });
        edit_field = (EditText) root.findViewById(R.id.edit_field);
        edit_field.setOnFocusChangeListener(this);
        edit_name = (TextView) root.findViewById(R.id.field_name);
        edit_name.setText("firstName");
        if (customer.getFirstName() != null) {
            edit_field.setText(customer.getFirstName());
        }
        addNewEditText(root, customer.getLastName(), "lastName");
        addNewEditText(root, customer.getEmail(), "e-mail");
        addNewEditText(root, customer.getSite(), "site");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reportDate = df.format(customer.getCreateDate());
        addNewEditText(root, reportDate + "", "createDate");
        addNewEditText(root, customer.getManagerId() + "", "managerId");

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            hideKeyboard(view);
            edit_name.requestFocus();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onAnswerReceived(String response) {
        if (response == "no site") Toast.makeText(ctx, "have not site", Toast.LENGTH_LONG).show();
        else {
            JSONObject jObject = null; // json
            try {
                jObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "ServerError", Toast.LENGTH_SHORT).show();
            }
            boolean resp;
            try {
                resp = jObject.getBoolean("success");
                Toast.makeText(ctx, resp == true ? "200. OK" : "something vrong", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pd.cancel();
        dismiss();
        listener.onCostumeredit(customer);

    }

    public class SendChanges extends AsyncTask<Customer, Void, Void> {

        @Override
        protected Void doInBackground(Customer... params) {
            final Customer customer = params[0];
            if (customer.getSite() == "") {
                onAnswerReceived("no_site");
                return null;
            }
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                String URL = "https://fsfss.retailcrm.ru/api/v4/customers/" + customer.getCustomerId() + "/edit";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onAnswerReceived(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onAnswerReceived(error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("apiKey", "X8PBNzZE7h7f6BtDuiJn0bLbtYjFpI7r");
                        params.put("by", "id");
                        params.put("site", customer.getSite());
                        JSONObject jsonBody = new JSONObject();

                        try {
                            jsonBody.put("managerId", String.valueOf(customer.getManagerId()));
                            jsonBody.put("firstName", customer.getFirstName());
                            jsonBody.put("lastName", customer.getLastName());
                            jsonBody.put("email", customer.getEmail());
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String reportDate = df.format(customer.getCreateDate());
                            jsonBody.put("createdAt", reportDate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params.put("customer", jsonBody.toString());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
    }

}

