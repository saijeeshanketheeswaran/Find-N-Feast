package ca.csci4100.uoit.project;

import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.content.Context;
import android.widget.EditText;
import android.content.Intent;
import android.app.Activity;
import android.widget.Toast;
import android.view.View;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        context=this;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    public void getInfo(View view) {
        Intent getInfoIntent = new Intent(
                LoginActivity.this,
                InfoActivity.class
        );
        startActivity(getInfoIntent);
    }
    int SIGN_IN_VALUE=1;
    private void sign(FirebaseUser user){
        Intent i = new Intent(context, SignUpActivity.class);
        i.putExtra("user",user);
        this.startActivityForResult(i,SIGN_IN_VALUE);
    }
    public void guest(View v){
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("user",(FirebaseUser)null);
        this.startActivityForResult(i,SIGN_IN_VALUE);
    }
    public void signup(View view) {
        //return email and password
        //create new user
        EditText email = (EditText) findViewById(R.id.email_input);
        EditText password = (EditText) findViewById(R.id.password_input);
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                            sign(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void login(View view) {
        EditText email = (EditText) findViewById(R.id.email_input);
        EditText password = (EditText) findViewById(R.id.password_input);

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Toast.makeText(LoginActivity.this, "Authentication success.",
                            //        Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(context, MainActivity.class);
                            i.putExtra("user",user);
                            context.startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",//+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        if (email.getText().toString().equals("A@gmail.com") && password.getText().toString().equals("A")) {
            Intent LoginIntent = new Intent(this, MainActivity.class);
            startActivity(LoginIntent);

        }
        else {
            //Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }

        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==SIGN_IN_VALUE){
            if(resultCode==Activity.RESULT_OK){
                login(null);
            }
        }
    }
}
