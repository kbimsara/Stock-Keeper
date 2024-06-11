package com.example.stockkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    // User data -> File Save
    private static final String file_name="userdata.txt";

    Button login;
    EditText email,pw;
    DBhelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //custom code start
        login=findViewById(R.id.btn_login2);//btn

        //text input
        email=findViewById(R.id.input_item_name);
        pw=findViewById(R.id.input_pw);

        //database
        database=new DBhelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e,p,e2,p2;
                e=email.getText().toString();
                p=pw.getText().toString();
                if(validation()){
                    Cursor c = database.ViewUserData(e);
                    if(c.getCount()>0){
                        c.moveToFirst();
                        e2=c.getString(0);
                        p2=c.getString(2);
                        if (e.equals(e2) && p.equals(p2)){
//                            Toast.makeText(LoginActivity.this, e2+" Email & Password is "+p2, Toast.LENGTH_SHORT).show();
                            save(e2);
                            clear();
                            load();
                            startActivity(new Intent(LoginActivity.this, AfterLogActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Your email or password are incorrect", Toast.LENGTH_SHORT).show();
                            clear();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "No email found in Database", Toast.LENGTH_SHORT).show();
                        clear();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"Please fill the all Fields",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //clear
    private void clear() {
        email.setText("");
        pw.setText("");
    }

    //Validation
    private boolean validation() {
        if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Fuel Capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pw.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Fuel Capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
//            Toast.makeText(LoginActivity.this, "Your email Address Is "+email, Toast.LENGTH_SHORT).show();
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
}