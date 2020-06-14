package com.example.photolate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class SecondActivity extends AppCompatActivity {

    TextView mResult;
    ImageView mPreviewIv;
    Uri image_uri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mResult = findViewById(R.id.result);
        mPreviewIv = findViewById(R.id.imageIv);
        Intent intent = getIntent();
        image_uri = intent.getParcelableExtra("ImageUri");

        mPreviewIv.setImageURI(image_uri);

        //get drawable bitmap for text recognition
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!recognizer.isOperational()) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = recognizer.detect(frame);
            StringBuilder sb = new StringBuilder();
            //get text from sb until there is no text
            for (int i = 0; i < items.size(); i++) {
                TextBlock myItem = items.valueAt(i);
                sb.append(myItem.getValue());
                sb.append("\n");
            }
            //set text to edit text
            mResult.setText(sb.toString());
        }
    }
}