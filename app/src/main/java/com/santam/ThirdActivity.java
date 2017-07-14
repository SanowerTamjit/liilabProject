package com.santam.liilabproject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ThirdActivity extends AppCompatActivity {

    TextView mShowTd;
    private Document htmlDocument;
    private String htmlContentInStringFormat;
    String tdElement = "";

//    public  static String[] suffix = new String[]{"th","st","nd","rd","th","th","th","th","th","th"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle("LiiLab: 3rd");
        mShowTd = (TextView) findViewById(R.id.ShowTD);
        String htmlFilename = "sample.html";

        parseHTML(htmlFilename);
    }

    public void parseHTML(String htmlFile) {
        AssetManager mgr = getBaseContext().getAssets();
        try {
            InputStream in = mgr.open(htmlFile, AssetManager.ACCESS_BUFFER);
            htmlContentInStringFormat = ConvertToString(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (htmlContentInStringFormat.equals("")) {
            Toast.makeText(ThirdActivity.this, "There is no HTML file to parse", Toast.LENGTH_LONG).show();
        } else {

            htmlDocument = Jsoup.parse(htmlContentInStringFormat);
            Elements articleElements = htmlDocument.select("td");

//            int count = 1;

            for (Element element : articleElements) {
//                if (((count % 100) >= 11) && ((count%100) <= 13))
//                    tdElement = tdElement + count + "th TD: "+ element.text() + "\n\n";
//                else
//                    tdElement = tdElement + count + "" + suffix[count%10] +"TD: "+ element.text() + "\n\n";
                tdElement = tdElement + element.text() + "\n\n";

//                count ++;
            }
            mShowTd.setText(tdElement);

        }
    }

    //Conver InputStream to String
    private String ConvertToString(InputStream in) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferReader.close();
            Log.d("Log E-", "Parse Ready " + stringBuilder.toString());
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
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
            startActivity(new Intent(ThirdActivity.this, FirstActivity.class));

        else if (menuId == R.id.scrn2)
            startActivity(new Intent(ThirdActivity.this, SecondActivity.class));

        else if (menuId == R.id.scrn3)
            startActivity(new Intent(ThirdActivity.this, ThirdActivity.class));

        else if (menuId == R.id.scrn4)
            startActivity(new Intent(ThirdActivity.this, FourthActivity.class));

        else
            startActivity(new Intent(ThirdActivity.this, FirstActivity.class));

        // Return Statement
        return super.onOptionsItemSelected(item);
    }
}
