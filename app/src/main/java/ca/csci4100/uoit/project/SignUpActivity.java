package ca.csci4100.uoit.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void getNext(View view) {
        Intent getNextIntent = new Intent(
                SignUpActivity.this,
                UserQuesActivity.class
        );
        startActivityForResult(getNextIntent,1);

        /*
        EditText first_name = (EditText) findViewById(R.id.first_name_input);
        EditText last_name = (EditText) findViewById(R.id.last_name_input);
        EditText email = (EditText) findViewById(R.id.email_input);
        EditText password = (EditText) findViewById(R.id.password_input);
        EditText password_confirm = (EditText) findViewById(R.id.password_confirm_input);

        String First_Name = first_name.getText().toString();
        String Last_Name = last_name.getText().toString();
        String Email = email.getText().toString();
        String Password1 = password.getText().toString();
        String Password2 = password_confirm.getText().toString();

        /*
        //Email not in database
        if (Password1.equals(Password2)) {
            sqliteHelper = new SqliteHelper (this);

            sqliteHelper.addUser(new User(First_Name, Last_Name, Email, Password1));

            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        }
        else{
            Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }


        if (!sqliteHelper.CheckIfEmailExists(Email)) {

        }

        else{
            //Email already in database
            Toast.makeText(SignUpActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
        }
        */

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==1){
            if(resultCode==Activity.RESULT_OK){
                System.out.println("user info returned");
                Bundle b =data.getExtras();
                String fMeal=b.getString("favouriteMeal");
                double pMin=b.getDouble("minPrice"), pMax=b.getDouble("maxPrice");
                ArrayList<String> foodList=new ArrayList<>(b.getStringArrayList("foodList"));
                System.out.println(fMeal);
                System.out.println(pMin+" - "+pMax);
                for(int x=0;x<foodList.size();x++){
                    System.out.println(foodList.get(x));
                }


                //save name and preferences locally as text will change later TODO
                String fileName = "data";
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(fileName , Context.MODE_PRIVATE);

                    outputStream.write(((EditText)findViewById(R.id.first_name_input)).getText().toString().getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.write(((EditText)findViewById(R.id.last_name_input)).getText().toString().getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.write(fMeal.getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.write((pMin+"").getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.write((pMax+"").getBytes());
                    outputStream.write("\n".getBytes());

                    outputStream.write((foodList.size()+"").getBytes());
                    outputStream.write("\n".getBytes());

                    for(int x=0;x<foodList.size();x++){
                        outputStream.write(foodList.get(x).getBytes());
                        outputStream.write("\n".getBytes());
                    }

                    outputStream.close();
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }


                finish();
            }
        }



    }


}
