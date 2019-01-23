package com.example.tanveerhoque.qfinder;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class BioActivity extends AppCompatActivity {
    JsonObject jsonObject;
    String jsonString;
    ImageView qrImage;
    TextView name;
    TextView organization;
    TextView phone_view;
    TextView email;
    TextView address;
    //    static TextView facebook;
    LinearLayout phone, messenger, location, organization_layout, phone_layout, email_layout, address_layout;
    //            , facebook_layout;
    Button retry, exit;

    public BioActivity() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        initGui();

        JsonManupulation();
    }

    public void JsonManupulation(){
        jsonString = getIntent().getStringExtra("JSON_DATA");
        Log.d("JSON Data: ", jsonString);
//        Toast.makeText(this, jsonString, Toast.LENGTH_LONG).show();
        try{
            jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
            Log.d("Object: ", String.valueOf(jsonObject));
        }catch (Exception e){
            Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
//        serialize();
        deserialize();
    }

    private void deserialize() {
//        jsonString = "{'name': 'Tanveer', 'organization': 'org', 'phone': '0100', 'email': 'thaque20@gmail.com', 'address': '2302', 'facebook': 'thaque20'}";


//        String img = jsonObject.get("image").getAsString();
//        Bitmap bitmap;
//        try{
//            byte [] encodeByte=Base64.decode(img,Base64.DEFAULT);
//            bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            qrImage.setImageBitmap(bitmap);
//
//        }catch(Exception e){
//            e.getMessage();
//        }
//        Toast.makeText(this, img, Toast.LENGTH_SHORT).show();
//        byte[] decodedString = Base64.decode(img, Base64.URL_SAFE );
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        name.setText(jsonObject.get("name").getAsString());
        organization.setText(jsonObject.get("organization").getAsString());
        phone_view.setText(jsonObject.get("phone").getAsString());
        email.setText(jsonObject.get("email").getAsString());
        address.setText(jsonObject.get("address").getAsString());

//        System.out.print("Hello: ");

//        Gson gson = new Gson();
//
//        String userJson = "{'name': 'Tanveer', 'organization': 'org', 'phone': '0100', 'email': 'thaque20@gmail.com', 'address': '2302', 'facebook': 'thaque20'}";
//        User user = gson.fromJson(userJson, User.class);

//        System.out.print(user);
    }

//    private void serialize() {
//        User user = new User(
//                "Name",
//                "Organizaiton",
//                "Phone",
//                "Email",
//                "Addres",
//                "Facebook"
//        );
//
//        Gson gson = new Gson();
//        String json = gson.toJson(user);
//    }

    public void initGui() {
        qrImage = (ImageView) findViewById(R.id.qrImage);
        name = (TextView) findViewById(R.id.name);

        phone = (LinearLayout) findViewById(R.id.phone);
        messenger = (LinearLayout) findViewById(R.id.messenger);
        location = (LinearLayout) findViewById(R.id.location);

        organization_layout = (LinearLayout) findViewById(R.id.organization_layout);
        phone_layout = (LinearLayout) findViewById(R.id.phone_layout);
        email_layout = (LinearLayout) findViewById(R.id.email_layout);
        address_layout = (LinearLayout) findViewById(R.id.address_layout);
//        facebook_layout = (LinearLayout) findViewById(R.id.facebook_layout);


        organization = (TextView) findViewById(R.id.organization);
        phone_view = (TextView) findViewById(R.id.phone_view);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.address);
//        facebook = (TextView) findViewById(R.id.facebook);


        retry = (Button) findViewById(R.id.retry);
        exit = (Button) findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BioActivity.this, MainActivity.class));
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
        phone_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://m.me/"+jsonObject.get("messenger").getAsString());

                Intent toMessenger= new Intent(Intent.ACTION_VIEW, uri);
                startActivity(toMessenger);
                try {
                    startActivity(toMessenger);
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(BioActivity.this, "Please Install Facebook Messenger",    Toast.LENGTH_LONG).show();
                }
            }

        });

//        facebook_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                getOpenFacebookIntent(BioActivity.this);
//
//            }
//        });

        email_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                emailIntent.setData(Uri.parse("mailto:"+email.getText().toString()));
                startActivity(emailIntent);
            }
        });

        organization_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+organization.getText().toString()));
                startActivity(browserIntent);
            }
        });
    }

//    public static Intent getOpenFacebookIntent(Context context) {
//        try {
//            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/thaque20"));
//        } catch (Exception e) {
//            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/thaque20"));
//        }
//    }

    public void call(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone_view.getText().toString()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
