package com.example.tanveerhoque.qfinder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import lib.folderpicker.FolderPicker;

import static com.google.gson.internal.$Gson$Types.arrayOf;

public class qrGenerateActivity extends AppCompatActivity {
    String TAG = "GenerateQRCode";
    EditText edtValue;
    ImageView qrImage;
    Button exit, save;
    String inputValue;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    private static final int SDCARD_PERMISSION = 1,
            FOLDER_PICKER_CODE = 2,
            FILE_PICKER_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generate);
        inputValue = getIntent().getStringExtra("JSON_DATA");
        qrImage = (ImageView) findViewById(R.id.QR_Image);
        exit = (Button) findViewById(R.id.exit);
        save = (Button) findViewById(R.id.save);

        if (inputValue.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    inputValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        } else {
            edtValue.setError("Required");
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrGenerateActivity.this.finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermission();
                try{
                    qrImage.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable) qrImage.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

//                    String folderLocation = intent.getExtras().getString("data");

//                    File file = new File(savePath, "qrcode")
                    DateFormat df = new SimpleDateFormat("-yyyy-MM-dd-HH-mm-ss-SSS");
                    String extens = df.format(Calendar.getInstance().getTime());

                    boolean save = QRGSaver.save(savePath, "qrcode"+extens, bitmap, QRGContents.ImageType.IMAGE_PNG);
//                    String result = save ? "Image Saved" : "Image Not Saved";
                    if(save){
                        Toast.makeText(qrGenerateActivity.this, "Image saved to QRCode Folder", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(qrGenerateActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//                    Toast.makeText(this, savePath, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    void checkStoragePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //Write permission is required so that folder picker can create new folder.
            //If you just want to pick files, Read permission is enough.

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        SDCARD_PERMISSION);
            }
        }

    }

    void pickFolder() {
        Intent intent = new Intent(this, FolderPicker.class);
        startActivityForResult(intent, FOLDER_PICKER_CODE);
    }

    void pickFile() {
        Intent intent = new Intent(this, FolderPicker.class);

        //Optional
        intent.putExtra("title", "Select file to upload");
        intent.putExtra("location", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        intent.putExtra("pickFiles", true);
        //Optional

        startActivityForResult(intent, FILE_PICKER_CODE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == FOLDER_PICKER_CODE) {

            if (resultCode == Activity.RESULT_OK && intent.hasExtra("data")) {


            } else if (resultCode == Activity.RESULT_CANCELED) {
//                tvFolder.setText(R.string.folder_pick_cancelled);
            }

        } else if (requestCode == FILE_PICKER_CODE) {

            if (resultCode == Activity.RESULT_OK && intent.hasExtra("data")) {
                String fileLocation = "<b>Selected File: </b>"+ intent.getExtras().getString("data");
//                tvFile.setText( Html.fromHtml(fileLocation) );
                Toast.makeText(this, fileLocation, Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
//                tvFile.setText(R.string.file_pick_cancelled);
            }

        }

    }

}