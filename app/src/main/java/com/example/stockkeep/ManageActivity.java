package com.example.stockkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ManageActivity extends AppCompatActivity {
    // User data -> File Save
    private static final String file_name="userdata.txt";

    public String file_email;

    Button search,update,delete,save,clear,date;
    EditText itmName,itmQt,itmDate;

    DBhelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        //database
        database=new DBhelper(this);

        //custom code start
        search=findViewById(R.id.btn_srch);
        update=findViewById(R.id.btn_update);
        delete=findViewById(R.id.btn_delete);
        save=findViewById(R.id.btn_save);
        clear=findViewById(R.id.btn_clear);
        date=findViewById(R.id.btn_date);


        itmName=findViewById(R.id.input_item_name);
        itmQt=findViewById(R.id.input_item_qt);
        itmDate=findViewById(R.id.input_item_date);

        //Current Date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        itmDate.setText(formattedDate);//set Date

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    String itm,dt,qt,email;
                    itm=itmName.getText().toString();
                    qt=itmQt.getText().toString();
                    dt=itmDate.getText().toString();
                    email=load();

                    Cursor c = database.ViewItemData(itm,email);
                    if(c.getCount()>0){
                        c.moveToFirst();
                        qt=c.getString(1);
                        Toast.makeText(ManageActivity.this, qt+"- Allready in your Database List", Toast.LENGTH_SHORT).show();
                    }else {
                        if (database.InsertItemData(itm,qt,dt,email)){
                            Toast.makeText(ManageActivity.this,itm+" Create Successfull",Toast.LENGTH_SHORT).show();
                            clear();
                        }else {
                            Toast.makeText(ManageActivity.this,"Operation Un-successfull",Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    Toast.makeText(ManageActivity.this,"Fill the all Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!itmName.getText().toString().isEmpty()){
                    String name,qt,dt,email;
                    name=itmName.getText().toString();
                    email=load();

                    Cursor c = database.ViewItemData(name,email);
                    if(c.getCount()>0){
                        c.moveToFirst();
                        qt=c.getString(2);
                        dt=c.getString(3);
                        itmQt.setText(qt);
                        itmDate.setText(dt);
                    }else {
                        Toast.makeText(ManageActivity.this, "No Item found in Database", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ManageActivity.this, "Please Enter Search Field", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itm,dt,qt;
                itm=itmName.getText().toString();
                dt=itmDate.getText().toString();
                qt=itmQt.getText().toString();
                if (validation()){
                    Cursor a = database.ViewItemData(itm,load());
                    if(a.getCount()>0){
                        if(database.updateItem(itm,dt,qt)){
                            Toast.makeText(ManageActivity.this, itm+" Update Succesfull", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ManageActivity.this, "Update Un-succesfull", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ManageActivity.this, "No item in database", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itm;
                itm=itmName.getText().toString();
                if (!itm.isEmpty()){
                    Cursor c = database.ViewItemData(itm,load());
                    if(c.getCount()>0){
                        if(database.DeleteItem(itm)){
                            Toast.makeText(ManageActivity.this, itm+" Delete Successfully", Toast.LENGTH_SHORT).show();
                            clear();
                        }else {
                            Toast.makeText(ManageActivity.this, "Data Delete Un-successfully", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ManageActivity.this, "Please Enter Valide Data", Toast.LENGTH_SHORT).show();
                        clear();
                    }
                }else {
                    Toast.makeText(ManageActivity.this, "Please Enter Item Name Field", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Current Date
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                itmDate.setText(formattedDate);//set Date
            }
        });

    }

    //clear
    private void clear() {
        itmName.setText("");
        itmQt.setText("");
        itmDate.setText("");
    }

    //Validation
    private boolean validation() {
        if (itmName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Item Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (itmDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Stock Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (itmQt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Stock Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //------------------File Handler---------------
    //save
//        public void save(String email){
//            FileOutputStream fos=null;
//            try {
//                fos=openFileOutput(file_name,MODE_PRIVATE);
//                fos.write(email.getBytes());
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }finally {
//                if(fos !=null){
//                    try {
//                        fos.close();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }
    //load file name -> userdata.txt
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