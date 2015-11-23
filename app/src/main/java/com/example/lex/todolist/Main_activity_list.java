package com.example.lex.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main_activity_list extends Activity {

    private ListView list;

    // data overview
    public static final String PREFS_NAME = "MyPreferenceData";

    // list of string items
    ArrayList<String> listItems = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_list);

        readItems();

        list = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

        getDetailsItem();
        removeListViewListener();
    }

    // reads items from saved text file
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            listItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            listItems = new ArrayList<String>();
        }
    }

    // writes items to text file
    private void writeItem() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, listItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void mouseClick(View view) {
//        Intent i = new Intent(Main_activity_list.this, side_activity.class);
//        startActivity(i);
//    }


    public void onAddItem(View v) {
        EditText inputAdd = (EditText) findViewById(R.id.inputAdd);
        String itemText = inputAdd.getText().toString();
        writeItem();
        adapter.add(itemText);
        inputAdd.setText("");
    }

    public void getDetailsItem() {
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter1, View item, int pos, long id) {
                        Intent i = new Intent(Main_activity_list.this, side_activity.class);
                        startActivity(i);
                        }
                });
    }



    public void removeListViewListener() {
        list.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter1, View item, int pos, long id) {

                        listItems.remove(pos);
                        adapter.notifyDataSetChanged();
                        writeItem();
                        return true;
                    }
                });
    }
}

