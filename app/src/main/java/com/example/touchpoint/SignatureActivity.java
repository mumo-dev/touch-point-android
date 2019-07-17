package com.example.touchpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class SignatureActivity extends AppCompatActivity {

    Bitmap bitmap;

    String path;

    private static final String IMAGE_DIRECTORY = "/signdemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        setTitle("Contract Agreement ");
        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/contract.htm");
    }

    public void makeSignature(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        Button clear, save;
        final SignatureView signatureView;
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view1 = inflater.inflate(R.layout.dialog_signature, null);
//        TextView textView = view1.findViewById(R.id.ed_name);
        signatureView = view1.findViewById(R.id.signature_view);
        clear = view1.findViewById(R.id.clear);
        save = view1.findViewById(R.id.save);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();

                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                String rationale = "Please provide storage write permission so that you can upload your photo";
                Permissions.Options options = new Permissions.Options()
                        .setRationaleDialogTitle("Info")
                        .setSettingsDialogTitle("Warning");

                Permissions.check(SignatureActivity.this/*context*/, permissions, rationale, options, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        // do your task.
                        path = saveImage(bitmap);

                        Toast.makeText(SignatureActivity.this, "Signature saved", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        // permission denied, block the feature.
                        Toast.makeText(SignatureActivity.this, "Please accept this permission to proceed", Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
        builder.setView(view1)
                // Add action buttons
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
//                        name = ed_name.getText().toString();
//
                        dialog.cancel();


                    }
                });

        builder.create();

        builder.show();
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("TAG", wallpaperDirectory.toString());
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(SignatureActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }

    public void backToContract(View view) {

        if (path == null) {
            Toast.makeText(this, "Please sign before leaving", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent();
            i.putExtra("signature_path", path);
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
