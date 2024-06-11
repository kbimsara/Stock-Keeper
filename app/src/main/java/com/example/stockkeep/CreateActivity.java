package com.example.stockkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {

    Button create;
    EditText name,email,pw;
    DBhelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //custom code start
        create=findViewById(R.id.btn_create2);//btn

        //text input
        name=findViewById(R.id.input_name);
        email=findViewById(R.id.input_item_name);
        pw=findViewById(R.id.input_pw);

        //database
        database=new DBhelper(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    String n,e,p;
                    n=name.getText().toString();
                    e=email.getText().toString();
                    p=pw.getText().toString();
                    if (database.InsertUserData(n,e,p)){
                        Toast.makeText(CreateActivity.this,n+" Registration Successfull",Toast.LENGTH_SHORT).show();
                        clear();
                    }else {
                        Toast.makeText(CreateActivity.this,"User Registration Un-successfull",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CreateActivity.this,"Fill the all Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //clear
    private void clear() {
        name.setText("");
        email.setText("");
        pw.setText("");
    }

    //Validation
    private boolean validation() {
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Fuel Type", Toast.LENGTH_SHORT).show();
            return false;
        }
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
}