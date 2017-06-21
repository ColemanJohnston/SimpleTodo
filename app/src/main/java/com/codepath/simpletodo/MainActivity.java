package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    // declaring stateful objects here; these will be null before onCreate is called
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        //        items.add("finish project");
        //        items.add("eat awesome food");
        //        items.add("sleep");
        setupListViewListener();    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                items.remove(position);                //notify adapter that underlying the dataset changed
                itemsAdapter.notifyDataSetChanged();
                Log.i("MainActivity","Removed item " + position);
                writeItems();
                return true;
            }
        });
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        Toast.makeText(getApplicationContext(),"Item added to list", Toast.LENGTH_SHORT).show();
        writeItems();
    }    //returns the file in which the data is stored


    private File getDataFile(){
        return new File(getFilesDir(), "todo.txt");
    }

    private void readItems() {
        try {
            // create the array using the content in the file
            items = new ArrayList<String>(FileUtils.readLines(getDataFile()));
        }
        catch(IOException e){
            e.printStackTrace();
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }}
