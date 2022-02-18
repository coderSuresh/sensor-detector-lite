package com.subitech.sensordetectorlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    boolean lightIsOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);
        imageButton = findViewById(R.id.image_button);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(-1);

        String[] name = new String[sensorList.size()];
        String[] version = new String[sensorList.size()];
        String[] vendor = new String[sensorList.size()];
        for (int i = 0; i < sensorList.size(); i++) {
            name[i] = sensorList.get(i).getName();
            version[i] = String.valueOf(sensorList.get(i).getVersion());
            vendor[i] = sensorList.get(i).getVendor();
        }

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), name, version, vendor);
        listView.setAdapter(customAdapter);

        //image button click event handling
        imageButton.setOnClickListener(view -> handleTorch());
    }

    public void handleTorch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraId = null;
            try {
                if (camManager != null) {
                    cameraId = camManager.getCameraIdList()[0];
                }
                if (camManager != null) {
                    if (!lightIsOn) {
                        lightIsOn = true;
                        camManager.setTorchMode(cameraId, true);
                    } else {
                        lightIsOn = false;
                        camManager.setTorchMode(cameraId, false);
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mail:
                sendMail();
                break;

            case R.id.about:
                startActivity(new Intent(MainActivity.this, About.class));
                break;

            case R.id.apps:
                openMarket();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void sendMail() {
        try {
            String[] mail = {"sd807141@gmail.com"};
            String subject = "Sensor Detector Lite V 0.1";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_EMAIL, mail);
            intent.setData(Uri.parse("mailto:"));
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Email client not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void openMarket() {

        final String packageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (
                android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

}