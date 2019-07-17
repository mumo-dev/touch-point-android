package com.example.touchpoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.touchpoint.api.ApiClient;
import com.example.touchpoint.models.ApiResponse;
import com.example.touchpoint.models.Contract;
import com.example.touchpoint.models.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.kyanogen.signatureview.SignatureView;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractActivity extends AppCompatActivity {

    public static final String TAG = "ContractActivity";
    public static final int SIGNATURE_REQUEST = 45 ;

    private TextInputEditText ed_commercial_name, ed_corporate_name, ed_name_and_position_ccr,
            ed_type_and_number_of_id, ed_address, ed_business_name, ed_pin_number, ed_reg_no_of_business,
            ed_phone, ed_email, ed_name;

    String dateOfBirth;
    String date;
    int contractId;


    Button submit;

    String path;
    String name;

    TextView signatureStatsuTv;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        setTitle("Contract Form");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("submitting ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

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

        signatureStatsuTv = findViewById(R.id.signature_status);



        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = ed_phone.getText().toString();
                if (!isValidPhoneNumber(phone)){
                    Toast.makeText(ContractActivity.this, "Enter a valid phone number", Toast.LENGTH_LONG).show();
                    return;
                }
                Contract contract = getData();
                if (contract == null) {
                    Toast.makeText(ContractActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    return;
                }

                if (path == null) {
                    Toast.makeText(ContractActivity.this, "Please sign first", Toast.LENGTH_LONG).show();
                    return;
                }

                uploadData(contract);
            }
        });

        final TextView dobTextView = findViewById(R.id.dob);
        dobTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(ContractActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateOfBirth = year + "-" + format(monthOfYear + 1) + "-" + format(dayOfMonth);
                                dobTextView.setText(dateOfBirth);
                            }
                        }, year, month, day);


                picker.show();
            }
        });

        final TextView dateTextView = findViewById(R.id.date);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(ContractActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date = year + "-" + format(monthOfYear + 1) + "-" + format(dayOfMonth);
                                dateTextView.setText(date);
                            }
                        }, year, month, day);


                picker.show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGNATURE_REQUEST && resultCode == RESULT_OK){
            if(data != null){
                path = data.getStringExtra("signature_path");
                signatureStatsuTv.setText("Signed");
            }
        }
    }

    static boolean isValidPhoneNumber(String phoneNo) {
        // First validate that the phone number is not null and has a length of 10
        if (TextUtils.isEmpty(phoneNo) || phoneNo.length() != 10) {
            return false;
        }
        // Next check the first two characters of the string to make sure it's 07
        if (!phoneNo.startsWith("07")) {
            return false;
        }
        // Now verify that each character of the string is a digit
        for (char c : phoneNo.toCharArray()) {
            if (!Character.isDigit(c)) {
                // One of the characters is not a digit (e.g. 0-9)
                return false;
            }
        }
        // At this point you know it is valid
        return true;
    }

    public void makeSignature(View view) {

       Intent i = new Intent(this, SignatureActivity.class);
       startActivityForResult(i, SIGNATURE_REQUEST);
    }


    private void uploadImage(String filePath, final int contractId) {

       // Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();
        progressDialog.show();
        //Create a file object using file path

        File file = new File(filePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(contractId));
        //
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String authorization = "Bearer " + sharedPreferences.getString("access_token", null);
        ApiClient.getClient()
                .uploadContractImage(authorization, part, id)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        progressDialog.dismiss();
                        Toast.makeText(ContractActivity.this, "Data uploaded sucessfully", Toast.LENGTH_LONG)
                                .show();

                        Intent i = new Intent(ContractActivity.this, ContractDetailActivity.class);
                        i.putExtra("contractId", contractId);
                        startActivity(i);

                        Log.d("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        Log.e("Upload", "failed", t);
                    }
                });

    }


    private void uploadData(Contract contract) {
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String authorization = "Bearer " + sharedPreferences.getString("access_token", null);
        ApiClient.getClient()
                .uploadContract(authorization, contract)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        progressDialog.dismiss();
                        ApiResponse apiResponse = response.body();
                        contractId =  apiResponse.getId();

                        uploadImage(path, apiResponse.getId());
                        Log.d("Upload", "data success: " + response.code());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        progressDialog.dismiss();

                        Log.e("Upload", "data upload failed", t);
                    }
                });

    }

    private String format(int number) {
        if (number < 10) {
            return "0" + number;
        }

        return String.valueOf(number);
    }


    private Contract getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String commercialName = ed_commercial_name.getText().toString();
        String corporateName = ed_corporate_name.getText().toString();
        String nameAndPositionCCR = ed_name_and_position_ccr.getText().toString();
        String typeNumberID = ed_type_and_number_of_id.getText().toString();
        String address = ed_address.getText().toString();
        String businessName = ed_business_name.getText().toString();
        String pinNumber = ed_pin_number.getText().toString();
//        String regNo = ed_pin_number.getText().toString();
        String phone = ed_phone.getText().toString();
        String name = ed_name.getText().toString();
        String email = ed_email.getText().toString();
        String regnoOfBusiness = ed_reg_no_of_business.getText().toString();
        int id = sharedPreferences.getInt("id", 0);


        if (TextUtils.isEmpty(commercialName) || TextUtils.isEmpty(corporateName)
                || TextUtils.isEmpty(nameAndPositionCCR) || TextUtils.isEmpty(typeNumberID) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(businessName) || TextUtils.isEmpty(pinNumber) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(regnoOfBusiness) || id == 0
                || TextUtils.isEmpty(dateOfBirth) || TextUtils.isEmpty(date)) {
            return null;
        }

        Contract contract = new Contract(commercialName, corporateName, nameAndPositionCCR,
                typeNumberID, address, businessName, pinNumber, regnoOfBusiness, phone, email, name, id, dateOfBirth, date);

        return contract;
    }


}
