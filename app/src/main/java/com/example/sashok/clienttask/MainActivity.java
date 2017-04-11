package com.example.sashok.clienttask;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    Toolbar toolbar;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    CustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layout_manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout_manager);


    }

    public void wordReceived(List<Customer> customers) {
        EditCostumer listener = new EditCostumer() {
            @Override
            public void onCostumeredit(Customer costumer) {
                Log.i("TAG", costumer.toString());
            }
        };
        adapter = new CustomerAdapter(MainActivity.this, customers, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.update) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            storeCustomers();
            return true;
        }
        return false;
    }

    public void storeCustomers() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        String url = "https://fsfss.retailcrm.ru/api/v4/customers?apiKey=X8PBNzZE7h7f6BtDuiJn0bLbtYjFpI7r";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray customers_list = response.getJSONArray("customers");
                            JSONObject customer_object;
                            JSONArray phones;
                            List<Customer> customers = new ArrayList<>();
                            Customer customer;
                            for (int i = 0; i < customers_list.length(); i++) {
                                try {
                                    customer = new Customer();
                                    customer_object = customers_list.getJSONObject(i);
                                    customer.setCustomerId(customer_object.has("id") ? customer_object.getInt("id") : 0);
                                    customer.setManagerId(customer_object.has("managerId") ? customer_object.getInt("managerId") : 0);

                                    customer.setFirstName(customer_object.has("firstName") ? customer_object.getString("firstName") : "");
                                    customer.setLastName(customer_object.has("lastName") ? customer_object.getString("lastName") : "");
                                    customer.setEmail(customer_object.has("email") ? customer_object.getString("email") : "");
                                    if (customer_object.has("phones")) {
                                        phones = customer_object.getJSONArray("phones");
                                        customer.initPhones();
                                        for (int j = 0; j < phones.length(); j++) {
                                            customer.getPhones().add(phones.getJSONObject(j).getString("number"));
                                        }
                                    }
                                    customer.setVip(customer_object.has("vip") ? customer_object.getBoolean("vip") : null);
                                    customer.setBad(customer_object.has("bad") ? customer_object.getBoolean("bad") : null);
                                    customer.setSite(customer_object.has("site") ? customer_object.getString("site") : "");
                                    if (customer_object.has("createdAt")) {
                                        String date = customer_object.getString("createdAt");
                                        java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        try {
                                            customer.setCreateDate(format.parse(date));
                                        } catch (Exception e) {

                                        }
                                    }
                                    customers.add(customer);
                                } catch (Exception e) {
                                    Log.i("TAG", "error in " + i);
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

    public interface EditCostumer {

        public void onCostumeredit(Customer costumer);
    }
}

