package com.example.stockkeep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ItemList extends AppCompatActivity {
    private static final String file_name="userdata.txt";

    public String file_email;

    RecyclerView recyclerView;
    ArrayList<String> itm,dt,qt;

    DBhelper database;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        database=new DBhelper(this);
        itm=new ArrayList<>();
        dt=new ArrayList<>();
        qt=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        adapter=new Adapter(this,itm,dt,qt);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    private void displayData() {
        String email=load();
        Cursor c=database.ViewItem(email);
        if (c.getCount()==0){
            Toast.makeText(ItemList.this, "No Item In Database", Toast.LENGTH_SHORT).show();
        }else {
            while (c.moveToNext()){
                itm.add(c.getString(1));
                dt.add(c.getString(2));
                qt.add(c.getString(3));
            }
        }
    }
    String load() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(file_name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String txt;
            while ((txt = br.readLine()) != null) {
                sb.append(txt).append("\n");
            }
            file_email = sb.toString();
            return file_email;
            //            Toast.makeText(LoginActivity.this, "Your email Address Is "+email, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}