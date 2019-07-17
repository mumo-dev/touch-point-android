package com.example.touchpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.touchpoint.adapter.ContractAdapter;
import com.example.touchpoint.adapter.IdentificationAdapter;
import com.example.touchpoint.api.ApiClient;
import com.example.touchpoint.models.Contract;
import com.example.touchpoint.models.Identification;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdentificationListActivity extends AppCompatActivity {

    private IdentificationAdapter adapter;
    private List<Identification> identificationList;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification_list);

        setTitle("List of submitted identification forms");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        identificationList = new ArrayList<>();
        adapter = new IdentificationAdapter(identificationList, this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);


    }

/*
    private void getData() {

        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String authorization = "Bearer " + sharedPreferences.getString("access_token", null);
        int userId = sharedPreferences.getInt("id", 0);
        ApiClient.getClient("http://touchpoint-123.herokuapp.com")
                .getIdentifications(authorization, userId)
                .enqueue(new Callback<List<Identification>>() {
                    @Override
                    public void onResponse(Call<List<Identification>> call, Response<List<Identification>> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            identificationList = response.body();
                            adapter.setContractList(identificationList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Identification>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }

    */
}
