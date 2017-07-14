package com.santam.liilabproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SecondActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_second);

        setTitle("LiiLab: 2nd");

        mListView = (ListView) findViewById(R.id.ListView2);

        TitleMsgData = new ArrayList<HashMap<String, String>>();

        parseXML();

    }

    //Parse XML to ListView
    public void parseXML() {
        try {
            InputStream inputStream = getAssets().open("sample.xml");

            DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
            Document document = docBuilder.parse(inputStream);

            Element firstElement = document.getDocumentElement();
            firstElement.normalize();

            HashMap<String, String> data = new HashMap<String, String>();

            NodeList firstTitle = document.getElementsByTagName(TITLE).item(0).getChildNodes();
            Node nodeFirst = firstTitle.item(0);
            NodeList scndTitle = document.getElementsByTagName(MESSAGE).item(0).getChildNodes();
            Node nodeScnd = scndTitle.item(0);

            data.put(TITLE, nodeFirst.getNodeValue());
            data.put(MESSAGE, nodeScnd.getNodeValue());

            TitleMsgData.add(data);


            NodeList nodeList = document.getElementsByTagName(GLOOSDIV);

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    HashMap<String, String> data1 = new HashMap<String, String>();

                    Element element2 = (Element) node;
                    NodeList TitleNodeList = element2.getElementsByTagName(TITLE).item(0).getChildNodes();
                    Node TitleNode = TitleNodeList.item(0);
                    NodeList MessageNodeList = element2.getElementsByTagName(MESSAGE).item(0).getChildNodes();
                    Node MessageNode = MessageNodeList.item(0);

                    data1.put(TITLE, TitleNode.getNodeValue());
                    data1.put(MESSAGE, MessageNode.getNodeValue());

                    TitleMsgData.add(data1);

                }
            }
            Log.d("XMLFetch: ", TitleMsgData.toString());

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


        } catch (Exception e) {
            e.printStackTrace();
        }
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
            startActivity(new Intent(SecondActivity.this, FirstActivity.class));

        else if (menuId == R.id.scrn2)
            startActivity(new Intent(SecondActivity.this, SecondActivity.class));

        else if (menuId == R.id.scrn3)
            startActivity(new Intent(SecondActivity.this, ThirdActivity.class));

        else if (menuId == R.id.scrn4)
            startActivity(new Intent(SecondActivity.this, FourthActivity.class));

        else
            startActivity(new Intent(SecondActivity.this, FirstActivity.class));

        // Return Statement
        return super.onOptionsItemSelected(item);
    }
}
