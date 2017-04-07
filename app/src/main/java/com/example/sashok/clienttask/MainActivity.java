package com.example.sashok.clienttask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    customerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button store_customers=(Button)findViewById(R.id.btn_store);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layout_manager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout_manager);
        store_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                String url="https://fsfss.retailcrm.ru/api/v4/customers?apiKey=X8PBNzZE7h7f6BtDuiJn0bLbtYjFpI7r";
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray customers_list=response.getJSONArray("customers");
                                    JSONObject customer_object;
                                    JSONArray phones;
                                    List<Customer> customers=new ArrayList<>();
                                    Customer customer;
                                    for (int i=0;i<customers_list.length();i++){
                                        try {
                                            customer=new Customer();
                                            customer_object = customers_list.getJSONObject(i);
                                            if (customer_object.has("id")) customer.customer_id = customer_object.getInt("id");
                                            if (customer_object.has("firstName")) customer.firstName = customer_object.getString("firstName");
                                            if (customer_object.has("lastName")) customer.lastName = customer_object.getString("lastName");
                                            if (customer_object.has("email")) customer.email=customer_object.getString("email");
                                            if (customer_object.has("phones")) {
                                                phones = customer_object.getJSONArray("phones");
                                                customer.phones = new ArrayList<>();
                                                for (int j = 0; j < phones.length(); j++) {
                                                    customer.phones.add(phones.getJSONObject(j).getString("number"));
                                                }
                                            }
                                            if (customer_object.has("vip")) customer.vip = customer_object.getBoolean("vip");
                                            if (customer_object.has("bad")) customer.bad = customer_object.getBoolean("bad");
                                            if (customer_object.has("site")) customer.site = customer_object.getString("site");
                                            if (customer_object.has("createdAt")) {
                                                String date = customer_object.getString("createdAt");
                                                java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                try {
                                                    customer.createDate = format.parse(date);
                                                } catch (Exception e) {

                                                }
                                            }
                                            customers.add(customer);
                                        }
                                        catch (Exception e){
                                            Log.i("TAG","error in "+i);
                                        }


                                    }
                                    wordReceived(customers);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub

                            }
                        });
                queue.add(jsObjRequest);
            }

        });

    }
    public void wordReceived(List<Customer> customers){
        adapter = new customerAdapter(MainActivity.this, customers);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }
}
