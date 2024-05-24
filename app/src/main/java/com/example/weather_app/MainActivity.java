package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    TextView textView2;
    ImageView imageView;

    //main func
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        editText = (EditText) findViewById(R.id.editTextText);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
    public void GetTempeture(View view)
    {
        ApiHandler apiHandler = new ApiHandler();
        JSONObject obj= null;
        try {
            if(editText.getText().toString().equals(""))
                obj = apiHandler.execute("İstanbul").get();
            else
                obj = apiHandler.execute(editText.getText().toString()).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONObject main = obj.getJSONObject("main");
            double Temp = main.getDouble("temp")-275.13;
            textView.setText("" +Temp+" C");//sicaklik

            JSONArray jsonArray = obj.getJSONArray("weather");
            JSONObject jsnObj = jsonArray.getJSONObject(0);

            String mWeather = jsnObj.getString("main");
            textView2.setText(mWeather);//genel hava durumu

            String iconS = jsnObj.getString("icon");
            String iconUrl = "http://openweathermap.org/img/wn/" + iconS + ".png";
            Picasso.get().load(iconUrl).into(imageView);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}