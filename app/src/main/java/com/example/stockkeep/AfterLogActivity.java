package com.example.stockkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AfterLogActivity extends AppCompatActivity {

    Button manage,view,logout;
    TextView out_name;

    // User data -> File Save
    private static final String file_name="userdata.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_log);

        manage=findViewById(R.id.btn_manage);
        view=findViewById(R.id.btn_view);
        logout=findViewById(R.id.btn_logout);

        out_name=findViewById(R.id.out_name);
        load();//View user Name in interface

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save("nan");
                startActivity(new Intent(AfterLogActivity.this, MainActivity.class));
            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AfterLogActivity.this, ManageActivity.class));
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AfterLogActivity.this, ItemList.class));
            }
        });

    }
    //------------------File Handler---------------
    //save
    public void save(String email){
        FileOutputStream fos=null;
        try {
            fos=openFileOutput(file_name,MODE_PRIVATE);
            fos.write(email.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(fos !=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    //load file name -> userdata.txt
    public void load(){
        FileInputStream fis=null;
        try {
            fis=openFileInput(file_name);
            InputStreamReader isr =new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String txt;
            while ((txt=br.readLine())!=null){
                sb.append(txt).append("\n");
            }
            String email=sb.toString();
//            Toast.makeText(AfterLogActivity.this, "Your email Address Is "+email, Toast.LENGTH_SHORT).show();
            out_name.setText(email);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(fis !=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    //logout
    public void logout(){
        save("nan");
    }
}