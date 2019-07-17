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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.touchpoint.api.ApiClient;
import com.example.touchpoint.models.Identification;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdentificationDetailActivity extends AppCompatActivity {


    private TextInputEditText ed_salesagent_name, ed_salesagent_zone, ed_salesagent_phone, ed_pos_name,
            ed_pos_address, ed_owner_name, ed_owner_phone, ed_businesspermit_number, ed_kra_pin, ed_supervisor_name,
            ed_supervisor_phone, ed_number_cni_supervisor, ed_cashier1_name, ed_cashier1_phone, ed_cni_cashier1,
            ed_cashier2_name, ed_cashier2_phone, ed_cni_cashier2, ed_cashier3_name, ed_device_imei, ed_device_serial_no,
            ed_surface_room, ed_products_type, ed_core_business, ed_secondary_activity, ed_employees_no,
            ed_payment_phone_number, ed_payment_amount, ed_payment_goods_refno;

    TextView tv_topology_of_point, tv_computer_available, tv_waiting_room, products_services;

    ImageView imageView1, imageView2;

    Identification identification;
    int identId;
    ProgressDialog progressDialog;

    Button downloadBtn;

    private long downloadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification_detail);
        setTitle("Identifications Details");

        if (getIntent() != null) {
            identId = getIntent().getIntExtra("identId", 0);
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
                String url = "http://touchpoint-123.herokuapp.com/identification/download/"+ identId;
                startDownload(url, "identification_document");
            }
        });

        imageView1 = findViewById(R.id.image_view);
        imageView2 = findViewById(R.id.image_view2);


        ed_salesagent_name = findViewById(R.id.ed_salesagent_name);
        ed_salesagent_zone = findViewById(R.id.ed_salesagent_zone);
        ed_salesagent_phone = findViewById(R.id.ed_salesagent_phone);
        ed_pos_name = findViewById(R.id.ed_pos_name);
        ed_pos_address = findViewById(R.id.ed_pos_address);

        ed_owner_name = findViewById(R.id.ed_owner_name);
        ed_owner_phone = findViewById(R.id.ed_owner_phone);
        ed_businesspermit_number = findViewById(R.id.ed_businesspermit_number);
        ed_kra_pin = findViewById(R.id.ed_kra_pin);

        ed_supervisor_name = findViewById(R.id.ed_supervisor_name);
        ed_supervisor_phone = findViewById(R.id.ed_supervisor_phone);
        ed_number_cni_supervisor = findViewById(R.id.ed_number_cni_supervisor);

        ed_cashier1_name = findViewById(R.id.ed_cashier1_name);
        ed_cashier1_phone = findViewById(R.id.ed_cashier1_phone);
        ed_cni_cashier1 = findViewById(R.id.ed_cni_cashier1);
        ed_cashier2_name = findViewById(R.id.ed_cashier2_name);
        ed_cashier2_phone = findViewById(R.id.ed_cashier2_phone);
        ed_cni_cashier2 = findViewById(R.id.ed_cni_cashier2);
        ed_cashier3_name = findViewById(R.id.ed_cashier3_name);

        ed_device_imei = findViewById(R.id.ed_device_imei);
        ed_device_serial_no = findViewById(R.id.ed_device_serial_no);
        ed_surface_room = findViewById(R.id.ed_surface_room);
        ed_products_type = findViewById(R.id.ed_products_type);
        ed_core_business = findViewById(R.id.ed_core_business);

        ed_secondary_activity = findViewById(R.id.ed_secondary_activity);
        ed_employees_no = findViewById(R.id.ed_employees_no);
        ed_payment_phone_number = findViewById(R.id.ed_payment_phone_number);
        ed_payment_amount = findViewById(R.id.ed_payment_amount);
        ed_payment_goods_refno = findViewById(R.id.ed_payment_goods_refno);

        tv_topology_of_point = findViewById(R.id.tv_topology_of_point);
        tv_computer_available = findViewById(R.id.tv_computer_available);
        tv_waiting_room = findViewById(R.id.tv_waiting_room);
        products_services = findViewById(R.id.products_services);

//        setData();

        if (identId != 0) {
            fetchData();
        }

    }

    private void fetchData() {
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String authorization = "Bearer " + sharedPreferences.getString("access_token", null);
        ApiClient.getClient()
                .getIdentification(authorization, identId)
                .enqueue(new Callback<Identification>() {
                    @Override
                    public void onResponse(Call<Identification> call, Response<Identification> response) {
                        progressDialog.dismiss();

                        if (response.isSuccessful() && response.code() == 200) {
                            identification = response.body();
                            setData();
                        } else {
                            Toast.makeText(IdentificationDetailActivity.this, "Fetching of data failed", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Identification> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(IdentificationDetailActivity.this, "Fetching of data failed", Toast.LENGTH_LONG)
                                .show();
                    }
                });

    }


    private void setData() {
        ed_salesagent_name.setText(identification.getSalesagent_name());
        ed_salesagent_zone.setText(identification.getSalesagent_zone());
        ed_salesagent_phone.setText(identification.getSalesagent_phone());
        ed_pos_name.setText(identification.getPos_name());
        ed_pos_address.setText(identification.getPos_address());

        ed_owner_name.setText(identification.getOwner_name());
        ed_owner_phone.setText(identification.getOwner_phone());
        ed_businesspermit_number.setText(identification.getBusinesspermit_number());
        ed_kra_pin.setText(identification.getKra_pin());

        ed_supervisor_name.setText(identification.getSupervisor_name());
        ed_supervisor_phone.setText(identification.getSupervisor_name());
        ed_number_cni_supervisor.setText(identification.getNumber_cni_supervisor());

        ed_cashier1_name.setText(identification.getCashier1_name());
        ed_cashier1_phone.setText(identification.getCashier1_phone());
        ed_cni_cashier1.setText(identification.getCni_cashier1());
        ed_cashier2_name.setText(identification.getCashier2_name());
        ed_cashier2_phone.setText(identification.getCashier2_phone());
        ed_cni_cashier2.setText(identification.getCni_cashier2());
        ed_cashier3_name.setText(identification.getCashier3_name());

        ed_device_imei.setText(identification.getDevice_imei());
        ed_device_serial_no.setText(identification.getDevice_serial_no());
        ed_surface_room.setText(identification.getSurface_room());
        ed_products_type.setText(identification.getProducts_type());
        ed_core_business.setText(identification.getCore_business());


        ed_secondary_activity.setText(identification.getSecondary_activity());
        ed_employees_no.setText(String.valueOf(identification.getEmployees_no()));
        ed_payment_phone_number.setText(identification.getPayment_phone_number());
        ed_payment_amount.setText(String.valueOf(identification.getPayment_amount()));
        ed_payment_goods_refno.setText(identification.getPayment_goods_refno());

        tv_topology_of_point.setText(identification.getTopology_of_point());
        products_services.setText(identification.getServices_to_market());

        tv_computer_available.append(identification.isComputer_available() ? "  YES" : "  NO");
        tv_waiting_room.append(identification.isWaiting_room() ? "  YES" : "  NO");


        if (identification.getFront_image_url() != null) {
            Glide.with(this)
                    .load(ApiClient.BASE_URL+ "/images/" + identification.getFront_image_url())
                    .into(imageView1);

        } else {
            imageView1.setVisibility(View.GONE);
        }

        if (identification.getBack_image_url() != null) {
            Glide.with(this)
                    .load(ApiClient.BASE_URL+"/images/" + identification.getBack_image_url())
                    .into(imageView2);

        } else {
            imageView2.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(onDownloadComplete);
    }

    private void startDownload(String url, String referralNote) {

        //request permissions;;


        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri download_Uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading Touch Identification Document");
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
                Toast.makeText(IdentificationDetailActivity.this, "Download Completed", Toast.LENGTH_LONG).show();
            }
        }
    };


}
