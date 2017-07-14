package com.santam.liilabproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import com.santam.liilabproject.R;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    ListView mListView;

    static ArrayList<HashMap<String, String>> TitleMsgData;

    ListAdapter adapter;

    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String GLOSSARY = "glossary";
    public static final String GLOOSDIV = "GlossDiv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.liilab);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setContentView(R.layout.activity_first);



        setTitle("LiiLab: 1st");

        mListView = (ListView) findViewById(R.id.ListView1);

        TitleMsgData = new ArrayList<HashMap<String, String>>();

        parseJson();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int menuId = item.getItemId();

        if (menuId == R.id.scrn1)
            startActivity(new Intent(FirstActivity.this, FirstActivity.class));

        else if (menuId == R.id.scrn2)
            startActivity(new Intent(FirstActivity.this, SecondActivity.class));

        else if (menuId == R.id.scrn3)
            startActivity(new Intent(FirstActivity.this, ThirdActivity.class));

        else if (menuId == R.id.scrn4)
            startActivity(new Intent(FirstActivity.this, FourthActivity.class));

        else
            startActivity(new Intent(FirstActivity.this, FirstActivity.class));

        // Return Statement
        return super.onOptionsItemSelected(item);
    }

    // Load sample.json
    public String loadJSONData() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open("sample.json");
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferReader.close();
            Log.d("Log E-", "Response Ready " + stringBuilder.toString());
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    // Parse JSON to ListView

    public void parseJson() {
        String title;
        String msg;
        JSONArray secondArray;
        try {

            JSONObject jsonObj = new JSONObject(loadJSONData());
            HashMap<String, String> data = new HashMap<String, String>();

            JSONObject glossary = jsonObj.getJSONObject(GLOSSARY);
            data.put(TITLE, glossary.getString(TITLE).toString());
            data.put(MESSAGE, glossary.getString(MESSAGE).toString());
            Log.d("DataCheck: ", data.toString());
            TitleMsgData.add(data);

            secondArray = glossary.getJSONArray(GLOOSDIV);
            for (int j = 0; j < secondArray.length(); j++) {
                JSONObject getScndJson = secondArray.getJSONObject(j);
                HashMap<String, String> data1 = new HashMap<String, String>();
                data1.put(TITLE, getScndJson.getString(TITLE).toString());
                data1.put(MESSAGE, getScndJson.getString(MESSAGE).toString());
                //                    title = getScndJson.getString("title").toString();
                //                    msg = getScndJson.getString("message").toString();
                //                    titleData.add(title);
                //                    msgData.add(msg);
                TitleMsgData.add(data1);
            }

            Log.d("DataCheck: ", TitleMsgData.toString());

            String[] from = {TITLE, MESSAGE};
            int[] to = {android.R.id.text1, android.R.id.text2};

            adapter = new SimpleAdapter(
                    getApplicationContext(), TitleMsgData,
                    android.R.layout.simple_list_item_2, from, to){

                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    text1.setTextColor(Color.BLACK);
                    text1.setTextSize(19);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                    text2.setTextColor(Color.BLACK);
                    text2.setTextSize(16);
                    return view;
                };
            };
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    TextView mTitle = (TextView) view.findViewById(android.R.id.text1);
                    String titleText = mTitle.getText().toString().trim();
                    Toast.makeText(getApplicationContext(), "Title is: '" + titleText + "'", Toast.LENGTH_SHORT).show();

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
