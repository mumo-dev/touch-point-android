package com.example.touchpoint;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.touchpoint.api.ApiClient;
import com.example.touchpoint.models.ApiResponse;
import com.example.touchpoint.models.Contract;
import com.example.touchpoint.models.Identification;
import com.google.android.material.textfield.TextInputEditText;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdentificationActivity extends AppCompatActivity {

    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_TAKE_PHOTO2 = 89;
    public String currentPhotoPath;
    public String currentPhotoPath2;

    private int identId;
    public List<String> servicesList;


    private TextInputEditText ed_salesagent_name, ed_salesagent_zone, ed_salesagent_phone, ed_pos_name,
            ed_pos_address, ed_owner_name, ed_owner_phone, ed_businesspermit_number, ed_kra_pin, ed_supervisor_name,
            ed_supervisor_phone, ed_number_cni_supervisor, ed_cashier1_name, ed_cashier1_phone, ed_cni_cashier1,
            ed_cashier2_name, ed_cashier2_phone, ed_cni_cashier2, ed_cashier3_name, ed_device_imei, ed_device_serial_no,
            ed_surface_room, ed_products_type, ed_core_business, ed_secondary_activity, ed_employees_no,
            ed_payment_phone_number, ed_payment_amount, ed_payment_goods_refno;

    private Button submitBtn;

    RadioGroup rg_topology_of_point, rg_computer_available, rg_waiting_room;

    ImageView imageView, imageView2;
    Button camera1, camera2;

    //services_to_market
    CheckBox cb_bill_payment, cb_mobile_money, cb_airtime, cb_money_transfer, cb_insurance;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        setTitle("Touch Point Identification");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("submitting ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);


        imageView = findViewById(R.id.image_view);
        imageView2 = findViewById(R.id.image_view2);

        camera1 = findViewById(R.id.open_camera);
        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto(true);
            }
        });
        camera2 = findViewById(R.id.open_camera2);
        camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto(false);
            }
        });


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

        submitBtn = findViewById(R.id.btn_submit);


        rg_topology_of_point = findViewById(R.id.rg_topology_of_point);
        rg_computer_available = findViewById(R.id.rg_computer_available);
        rg_waiting_room = findViewById(R.id.rg_waiting_room);

        cb_bill_payment = findViewById(R.id.cb_bill_payment);
        cb_bill_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {

                    servicesList.add("Payment of bills");
                } else {
                    servicesList.remove("Payment of bills");
                }
            }
        });

        cb_mobile_money = findViewById(R.id.cb_mobile_money);
        cb_mobile_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {

                    servicesList.add("Mobile Money");
                } else {
                    servicesList.remove("Mobile Money");
                }
            }
        });
        cb_airtime = findViewById(R.id.cb_airtime);
        cb_airtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {

                    servicesList.add("Airtime");
                } else {
                    servicesList.remove("Airtime");
                }
            }
        });
        cb_money_transfer = findViewById(R.id.cb_money_transfer);
        cb_money_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    servicesList.add("Money transfer");
                } else {
                    servicesList.remove("Money transfer");
                }
            }
        });
        cb_insurance = findViewById(R.id.cb_insurance);
        cb_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    servicesList.add("Insurance");
                } else {
                    servicesList.remove("Insurance");
                }
            }
        });

        servicesList = new ArrayList<>();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String cashier1_phone = ed_cashier1_phone.getText().toString();
//                String supervisor_phone = ed_supervisor_phone.getText().toString();
//                String payment_phone_number = ed_payment_phone_number.getText().toString();
//                if (!isValidPhoneNumber(supervisor_phone)
//                        || !isValidPhoneNumber(cashier1_phone) || !isValidPhoneNumber(payment_phone_number)) {
//                    Toast.makeText(IdentificationActivity.this, "Enter a valid Phone Number", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                String supervisor_name = ed_supervisor_name.getText().toString();
                String supervisor_phone = ed_supervisor_phone.getText().toString();
                String number_cni_supervisor = ed_number_cni_supervisor.getText().toString();

                String cashier1_name = ed_cashier1_name.getText().toString();
                String cashier1_phone = ed_cashier1_phone.getText().toString();
                String cni_cashier1 = ed_cni_cashier1.getText().toString();


                String payment_phone_number = ed_payment_phone_number.getText().toString();
                String payment_amount = ed_payment_amount.getText().toString();


                if (TextUtils.isEmpty(supervisor_name)
                        || TextUtils.isEmpty(supervisor_phone)
                        || TextUtils.isEmpty(number_cni_supervisor)
                ) {
                    Toast.makeText(IdentificationActivity.this, "Please   fill all the Supervisor details", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(cashier1_name)
                        || TextUtils.isEmpty(cashier1_phone)
                        || TextUtils.isEmpty(cni_cashier1)
                ) {
                    Toast.makeText(IdentificationActivity.this, "Please fill all the cashier 1 details", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(payment_phone_number)
                        || TextUtils.isEmpty(payment_amount)
                ) {
                    Toast.makeText(IdentificationActivity.this, "Please fill all the payment details", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                Identification identification = getData();
                uploadData(identification);
            }
        });
    }


    private Identification getData() {
        String salesagent_name = ed_salesagent_name.getText().toString();
        String salesagent_zone = ed_salesagent_zone.getText().toString();
        String salesagent_phone = ed_salesagent_phone.getText().toString();
        String pos_name = ed_pos_name.getText().toString();
        String pos_address = ed_pos_address.getText().toString();
        String owner_name = ed_owner_name.getText().toString();
        String owner_phone = ed_owner_phone.getText().toString();
        String businesspermit_number = ed_businesspermit_number.getText().toString();
        String kra_pin = ed_kra_pin.getText().toString();
        String supervisor_name = ed_supervisor_name.getText().toString();
        String supervisor_phone = ed_supervisor_phone.getText().toString();
        String number_cni_supervisor = ed_number_cni_supervisor.getText().toString();

        String cashier1_name = ed_cashier1_name.getText().toString();
        String cashier1_phone = ed_cashier1_phone.getText().toString();
        String cni_cashier1 = ed_cni_cashier1.getText().toString();

        String cashier2_name = ed_cashier2_name.getText().toString();
        String cashier2_phone = ed_cashier2_phone.getText().toString();
        String cni_cashier2 = ed_cni_cashier2.getText().toString();
        String cashier3_name = ed_cashier3_name.getText().toString();
        String device_imei = ed_device_imei.getText().toString();
        String device_serial_no = ed_device_serial_no.getText().toString();
        String surface_room = ed_surface_room.getText().toString();
        String products_type = ed_products_type.getText().toString();
        String core_business = ed_core_business.getText().toString();
        String secondary_activity = ed_secondary_activity.getText().toString();
        String employees_no = ed_employees_no.getText().toString();
        String payment_phone_number = ed_payment_phone_number.getText().toString();
        String payment_amount = ed_payment_amount.getText().toString();
        String payment_goods_refno = ed_payment_goods_refno.getText().toString();

        // get selected radio button from radioGroup
        int selectedId = rg_topology_of_point.getCheckedRadioButtonId();


        // find the radiobutton by returned id
        RadioButton topology_rb = findViewById(selectedId);

        String topology_of_point = "";
        if (topology_rb != null) {
            topology_of_point = topology_rb.getText().toString();
        }

        selectedId = rg_computer_available.getCheckedRadioButtonId();
        RadioButton computer_rb = findViewById(selectedId);
        String computeravailability = "";
        if (computer_rb != null) {
            computeravailability = computer_rb.getText().toString();
        }


        boolean computer_equipment_available = false;
        if (computeravailability.equals("YES")) {
            computer_equipment_available = true;
        }


        selectedId = rg_waiting_room.getCheckedRadioButtonId();
        RadioButton waiting_rb = findViewById(selectedId);
        String waiting_room = "";
        if (waiting_rb != null) {
            waiting_room = waiting_rb.getText().toString();
        }


        boolean waiting_room_available = false;
        if (waiting_room.equals("YES")) {
            waiting_room_available = true;
        }

        StringBuilder servicesToMarket = new StringBuilder();

        for (String val : servicesList) {
            servicesToMarket.append(val).append(", ");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);

        if(TextUtils.isEmpty(employees_no)){
            employees_no = "0";
        }

        Identification identification = new Identification(salesagent_name, salesagent_zone, salesagent_phone,
                pos_name, pos_address, owner_name, owner_phone, businesspermit_number, kra_pin, supervisor_name,
                supervisor_phone, number_cni_supervisor, cashier1_name, cashier1_phone, cni_cashier1, cashier2_name,
                cashier2_phone, cni_cashier2, cashier3_name, device_imei, device_serial_no, surface_room, products_type, core_business,
                secondary_activity, Integer.valueOf(employees_no), waiting_room_available, computer_equipment_available,
                servicesToMarket.toString(), payment_phone_number, payment_amount, payment_goods_refno, topology_of_point);

        identification.setUser_id(id);

        return identification;

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            setPic(true);
        }
        if (requestCode == REQUEST_TAKE_PHOTO2 && resultCode == RESULT_OK) {

            setPic(false);

        }
    }

    public void uploadPhoto(final boolean isFirst) {

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String rationale = "Please provide storage write permission so that you can upload your photo";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                dispatchTakePictureIntent(isFirst);
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                Toast.makeText(IdentificationActivity.this, "Please accept this permission to proceed", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void dispatchTakePictureIntent(boolean isFirst) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(isFirst);
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again", Toast.LENGTH_SHORT).show();
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.touchpoint.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (isFirst)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                else
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO2);
            }
        }
    }

    private File createImageFile(boolean isFirst) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if (isFirst)
            currentPhotoPath = image.getAbsolutePath();
        else
            currentPhotoPath2 = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic(boolean isFirst) {
        // Get the dimensions of the View
        int targetW;
        int targetH;
        if (isFirst) {
            targetW = imageView.getWidth();
            targetH = imageView.getHeight();
        } else {
            targetW = imageView2.getWidth();
            targetH = imageView2.getHeight();
        }

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        if (isFirst)
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        else
            BitmapFactory.decodeFile(currentPhotoPath2, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap;
        if (isFirst)
            bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        else
            bitmap = BitmapFactory.decodeFile(currentPhotoPath2, bmOptions);

        if (isFirst)
            imageView.setImageBitmap(bitmap);
        else
            imageView2.setImageBitmap(bitmap);
    }


    private void uploadImage(String filePath1, String filePath2, final int identId) {

        //  Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();
        progressDialog.show();
        //Create a file object using file path

        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        File compressedImageFile1;
        File compressedImageFile2;
        try {
            compressedImageFile1 = new Compressor(this).compressToFile(file1);
            compressedImageFile2 = new Compressor(this).compressToFile(file2);

            // Create a request body with file and image media type
            RequestBody fileReqBody1 = RequestBody.create(MediaType.parse("image/*"), compressedImageFile1);
            RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), compressedImageFile2);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part1 = MultipartBody.Part.createFormData("front_image", file1.getName(), fileReqBody1);
            MultipartBody.Part part2 = MultipartBody.Part.createFormData("back_image", file2.getName(), fileReqBody2);
            //Create request body with text description and text media type
            RequestBody id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(identId));
            //
            SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
            String authorization = "Bearer " + sharedPreferences.getString("access_token", null);
            ApiClient.getClient()
                    .uploadIdentificationImage(authorization, part1, part2, id)
                    .enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(IdentificationActivity.this, "Data uploaded sucessfully", Toast.LENGTH_LONG)
                                        .show();

                                Intent i = new Intent(IdentificationActivity.this, IdentificationDetailActivity.class);
                                i.putExtra("identId", identId);
                                startActivity(i);
                            }
                            Log.d("Upload", "success");
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {

                            progressDialog.dismiss();
                            Log.e("Upload", "failed", t);
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void uploadData(Identification identification) {
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String authorization = "Bearer " + sharedPreferences.getString("access_token", null);
        ApiClient.getClient()
                .uploadIdentification(authorization, identification)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        progressDialog.dismiss();
                        ApiResponse apiResponse = response.body();
                        identId = apiResponse.getId();

                        if (currentPhotoPath != null && currentPhotoPath2 != null) {
                            uploadImage(currentPhotoPath, currentPhotoPath2, apiResponse.getId());
                        } else {
                            if (response.isSuccessful()) {
                                Toast.makeText(IdentificationActivity.this, "Data uploaded sucessfully", Toast.LENGTH_LONG)
                                        .show();

                                Intent i = new Intent(IdentificationActivity.this, IdentificationDetailActivity.class);
                                i.putExtra("identId", identId);
                                startActivity(i);
                            }
                        }
                        Log.d("Upload", "data success: " + response.code());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("Upload", "data upload failed", t);

                        Toast.makeText(IdentificationActivity.this, "Data upload failed\n" + t.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                });

    }
}
