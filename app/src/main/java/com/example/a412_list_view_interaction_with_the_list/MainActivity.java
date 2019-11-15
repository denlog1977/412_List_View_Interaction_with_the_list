package com.example.a412_list_view_interaction_with_the_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText mLargeText;
    private Button mBtnSaveText;

    private SharedPreferences mySharedPref;
    private static String LARGE_TEXT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDateFromSharedPref();

        if (LARGE_TEXT == "") {
            SharedPreferences.Editor myEditor = mySharedPref.edit();
            myEditor.putString(LARGE_TEXT, getString(R.string.large_text));
            myEditor.apply();
            Toast.makeText(this, "string.large_text сохранен в LARGE_TEXT SharedPreferences", Toast.LENGTH_SHORT).show();
        }




        ListView listView = findViewById(R.id.listView);

        List<Map<String, String>> content = prepareContent();

        SimpleAdapter listContentAdapter = createAdapter(content);

        listView.setAdapter(listContentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                // параметр view - строка, на которую кликнул пользователь, можно получить текст из нее
                String text = ((TextView) view.findViewById(R.id.textView)).getText().toString();
                String length=((TextView) view.findViewById(R.id.textView2)).getText().toString();
                // position - номер строки, можно получить данные по этому номеру и взять текст из них
                Toast.makeText(getApplicationContext(), "  textView.getText " + text + " " + length, Toast.LENGTH_SHORT).show();

//                text = ((Map) final listContentAdapter.getItem(position)).get("text").toString();
//                length = ((Map) final listContentAdapter.getItem(position)).get("length").toString();
//                Toast.makeText(getApplicationContext(), "listContentAdapter.getItem "  + text + " " + length, Toast.LENGTH_SHORT).show();
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
        String[] arrayContent = LARGE_TEXT.split("\n\n");
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
        mLargeText.setText(mySharedPref.getString(LARGE_TEXT, ""));
    }

}
