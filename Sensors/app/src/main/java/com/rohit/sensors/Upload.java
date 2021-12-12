package com.rohit.sensors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Upload extends AppCompatActivity {

    private static final int SELECT = 100;
    ImageView uploadImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        uploadImg = findViewById(R.id.imgCam);
    }

    public void imageUpload(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri selectImage = data.getData();
                InputStream inputStream = null;
                try {
                    assert selectImage != null;
                    inputStream = getContentResolver().openInputStream(selectImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                BitmapFactory.decodeStream(inputStream);
                uploadImg.setImageURI(selectImage);
            }
        }
    }


}