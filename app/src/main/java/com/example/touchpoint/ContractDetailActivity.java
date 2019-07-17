package com.example.touchpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.touchpoint.api.ApiClient;
import com.example.touchpoint.models.Contract;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractDetailActivity extends AppCompatActivity {

    Contract contract;
    int contractId;
    private TextInputEditText ed_commercial_name, ed_corporate_name, ed_name_and_position_ccr,
            ed_type_and_number_of_id, ed_address, ed_business_name, ed_pin_number, ed_reg_no_of_business,
            ed_phone, ed_email, ed_name;


    TextView dobTextView, dateTextView;
    ImageView image_signature;

    ProgressDialog progressDialog;

    Button downloadBtn;

    private long downloadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_detail);

        setTitle("Contract Details");

        if (getIntent() != null) {
            contractId = getIntent().getIntExtra("contractId", 0);
        }
        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        downloadBtn = findViewById(R.id.download_btn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://touchpoint-123.herokuapp.com/contract/download/"+ contractId;
                startDownload(url, "contract_document");
            }
        });

        image_signature = findViewById(R.id.image_signature);

        ed_commercial_name = findViewById(R.id.ed_commercial_name);

        ed_corporate_name = findViewById(R.id.ed_corporate_name);
        ed_name_and_position_ccr = findViewById(R.id.ed_name_and_position_ccr);
        ed_type_and_number_of_id = findViewById(R.id.ed_type_and_number_of_id);
        ed_address = findViewById(R.id.ed_address);
        ed_business_name = findViewById(R.id.ed_business_name);
        ed_pin_number = findViewById(R.id.ed_pin_number);
        ed_reg_no_of_business = findViewById(R.id.ed_reg_no_of_business);
        ed_phone = findViewById(R.id.ed_phone);
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);

        dobTextView = findViewById(R.id.dob);

        dateTextView = findViewById(R.id.date);


        fetchData();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    private void fetchData() {
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String authorization = "Bearer " + sharedPreferences.getString("access_token", null);
        ApiClient.getClient()
                .getContract(authorization, contractId)
                .enqueue(new Callback<Contract>() {
                    @Override
                    public void onResponse(Call<Contract> call, Response<Contract> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.code() == 200) {
                            contract = response.body();
                            setData();
                        } else {
                            Toast.makeText(ContractDetailActivity.this, "Fetching of data failed", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Contract> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ContractDetailActivity.this, "Fetching of data failed", Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }


    private void setData() {
        ed_commercial_name.setText(contract.getCommercial_name());
        ed_corporate_name.setText(contract.getCorporate_name());
        ed_name_and_position_ccr.setText(contract.getName_and_position_ccr());
        ed_type_and_number_of_id.setText(contract.getType_and_number_of_id());
        ed_address.setText(contract.getAddress());
        ed_business_name.setText(contract.getBusiness_name());
        ed_pin_number.setText(contract.getPin_number());
        ed_reg_no_of_business.setText(contract.getReg_no_of_business());
        ed_phone.setText(contract.getPhone());
        ed_name.setText(contract.getName());
        ed_email.setText(contract.getEmail());

        dobTextView.setText(contract.getDob());
        dateTextView.setText(contract.getDate());

        if (contract.getSignature_url() != null) {
            Glide.with(this)
                    .load(ApiClient.BASE_URL+ "/images/" + contract.getSignature_url())
                    .into(image_signature);

        } else {
            image_signature.setVisibility(View.GONE);
        }
    }


    private void startDownload(String url, String referralNote) {


        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri download_Uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading Touch contract document");
        request.setDescription("Download of " + referralNote + " in progress...");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/touch_downloads/" + referralNote + ".pdf");

        downloadID = downloadManager.enqueue(request);


    }


    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(ContractDetailActivity.this, "Download Completed", Toast.LENGTH_LONG).show();
            }
        }
    };
}
