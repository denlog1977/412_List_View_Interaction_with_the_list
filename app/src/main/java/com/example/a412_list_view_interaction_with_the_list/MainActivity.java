package com.example.a412_list_view_interaction_with_the_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    private SharedPreferences mySharedPref;
    private static String LARGE_TEXT = "";
    private static String dateFromSharedPref = "";
    private List<Map<String, String>> content;
    private SimpleAdapter listContentAdapter;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        getDateFromSharedPref();

        if (dateFromSharedPref == "") {
            SharedPreferences.Editor myEditor = mySharedPref.edit();
            myEditor.putString(LARGE_TEXT, getString(R.string.large_text));
            myEditor.apply();
            Toast.makeText(this, "string.large_text сохранен в LARGE_TEXT SharedPreferences", Toast.LENGTH_SHORT).show();
        }




        ListView listView = findViewById(R.id.listView);

        content = prepareContent();

        listContentAdapter = createAdapter(content);

        listView.setAdapter(listContentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

//                // параметр view - строка, на которую кликнул пользователь, можно получить текст из нее
//                String textListItem = ((TextView) view.findViewById(R.id.textView)).getText().toString();
//                String length=((TextView) view.findViewById(R.id.textView2)).getText().toString();
//                Toast.makeText(getApplicationContext(), textListItem + "\n[длина текста = " + length + "]", Toast.LENGTH_SHORT).show();

                // position - номер строки, можно получить данные по этому номеру и взять текст из них

//                String textListItem = ((Map) listContentAdapter.getItem(position)).get("text").toString();
//                String length = ((Map) listContentAdapter.getItem(position)).get("length").toString();
//                Toast.makeText(getApplicationContext(), textListItem + "\n[длина текста = " + length + "]", Toast.LENGTH_SHORT).show();

                content.remove(position);
                listContentAdapter.notifyDataSetChanged();

//                Toast.makeText(getApplicationContext(), " --- contentSize --- " + Integer.toString(content.size()), Toast.LENGTH_SHORT).show();

            }
        });



        swipeLayout = findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            // Будет вызван, когда пользователь потянет список вниз
            @Override public void onRefresh()
            {
//                updateList();
                getDateFromSharedPref();
                content = prepareContent();
                Toast.makeText(getApplicationContext(), " --- contentSize --- " + Integer.toString(content.size()), Toast.LENGTH_SHORT).show();

                listContentAdapter.notifyDataSetChanged();


                swipeLayout.setRefreshing(false);


            }

        });

    }



    @NonNull
    private SimpleAdapter createAdapter(List<Map<String, String>> content) {
        return new SimpleAdapter(this, content, R.layout.listlayout, new String[]{"text","length"}, new int[]{R.id.textView, R.id.textView2});
    }

    @NonNull
    private List<Map<String, String>> prepareContent() {

        List<Map<String, String>> contentList = new ArrayList<>();
        String[] arrayContent = dateFromSharedPref.split("\n\n");
        Map<String, String> mapForList;
        for (int i = 0 ; i < arrayContent.length ; i++){
            mapForList = new HashMap<>();
            mapForList.put("text",arrayContent[i]);
            mapForList.put("length",Integer.toString(arrayContent[i].length()));
            contentList.add(mapForList);
        };
        return contentList;
    }

    private void getDateFromSharedPref(){
        dateFromSharedPref = mySharedPref.getString(LARGE_TEXT, "");
    }

    private void updateList(){
        getDateFromSharedPref();
        content = prepareContent();
        Toast.makeText(getApplicationContext(), " --- contentSize --- " + Integer.toString(content.size()), Toast.LENGTH_SHORT).show();

        listContentAdapter.notifyDataSetChanged();
    }

}
