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
import com.example.touchpoint.api.ApiClient;
import com.example.touchpoint.models.Contract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractListActivity extends AppCompatActivity {


    private ContractAdapter adapter;
    private List<Contract> contractList;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("List of submitted contracts");

        setContentView(R.layout.activity_contract_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contractList = new ArrayList<>();
        adapter = new ContractAdapter(contractList, this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);


//        getData();

    }

    /*
    private void getData() {

        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String authorization = "Bearer " + sharedPreferences.getString("access_token", null);
        int userId = sharedPreferences.getInt("id", 0);
        ApiClient.getClient("http://touchpoint-123.herokuapp.com")
                .getContracts(authorization, userId)
                .enqueue(new Callback<List<Contract>>() {
                    @Override
                    public void onResponse(Call<List<Contract>> call, Response<List<Contract>> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            contractList = response.body();
                            adapter.setContractList(contractList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Contract>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }

    */
}
