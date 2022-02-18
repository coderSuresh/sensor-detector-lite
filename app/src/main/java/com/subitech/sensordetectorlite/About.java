package com.subitech.sensordetectorlite;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        button = findViewById(R.id.btn_send_mail);

        button.setOnClickListener(View -> sendMail());
    }

    public void sendMail() {
        try {
            String[] mail = {"sd807141@gmail.com"};
            String subject = "Sensor Detector Lite V 0.1";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_EMAIL, mail);
            intent.setData(Uri.parse("mailto:"));
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Email client not found", Toast.LENGTH_SHORT).show();
        }
    }
}
