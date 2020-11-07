package com.darkheavenapps.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText;
    private Button saveButton, retrieveAll;
    private TextView retrieveTextView;
    private String clientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        saveButton = findViewById(R.id.saveButton);
        retrieveTextView = findViewById(R.id.retrieveTextView);
        retrieveAll = findViewById(R.id.retrieveAllButton);

        //uploading data to server

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a test parse object and sending data to parse server
                final ParseObject client = new ParseObject("Client");
                client.put("Name", nameEditText.getText().toString());
                client.put("Age", Integer.parseInt(ageEditText.getText().toString()));
                client.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(MainActivity.this, "Saved " + client.get("Name") + " data...", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });

            }
        });

        //retrieving data from server

        retrieveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Client");
                parseQuery.getInBackground("lzDN6gt39T", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            retrieveTextView.setText(object.get("Name").toString() + "-" + object.get("Age"));
                        } else {
                            FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        //retrieving all data from server together

        retrieveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientData = "";
                ParseQuery<ParseObject> getAllData = ParseQuery.getQuery("Client");
                getAllData.whereGreaterThan("Age", 10);                                             //gets clients with age of 10 and above
                getAllData.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (objects != null && e == null) {
                            for (ParseObject clients : objects) {
                                clientData = clientData + clients.get("Name") + "\n";
                                FancyToast.makeText(MainActivity.this, clientData, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            }
                        } else {
                            FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        });


    }
}