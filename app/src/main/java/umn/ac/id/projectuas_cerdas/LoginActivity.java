package umn.ac.id.projectuas_cerdas;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button regisBtn, loginBtn;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    EditText emailText, passText;
    TextView loginInfo;
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regisBtn = findViewById(R.id.regisBtn);
        loginBtn = findViewById(R.id.loginBtn);
        loginInfo = findViewById(R.id.login_info);
        progressBar = findViewById(R.id.login_progressBar);

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    signIn();
                }
            }
        });

//        Firebase cek sudah login atau sudah
        mFirebaseAuth = FirebaseAuth.getInstance();

//        mFirebaseAuth.signOut();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {//sudah login, redirect ke MainActivity
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public boolean checkInput(){
        emailText = findViewById(R.id.userid);
        passText = findViewById(R.id.userpass);

        if(emailText.getText().toString().isEmpty()){
            emailText.setError("Email is required");
            emailText.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()){
            emailText.setError("Please input a valid email");
            emailText.requestFocus();
            return false;
        }
        if(passText.getText().toString().isEmpty()){
            passText.setError("Password is required");
            passText.requestFocus();
            return false;
        }

        return true;
    }

    public void signIn(){
        progressBar.setVisibility(View.VISIBLE);
        loginInfo.setVisibility(View.VISIBLE);
        loginInfo.setTextColor(Color.BLACK);
        loginInfo.setText("Logging In");

        String email = emailText.getText().toString();
        String password = passText.getText().toString();
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("STRING", "Login Berhasil");
                    progressBar.setVisibility(View.GONE);
                    loginInfo.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //makeToast("Log in success");
                    startActivity(intent);
                }
                else{
                    //makeToast("Invalid Credentials");
                    progressBar.setVisibility(View.GONE);
                    loginInfo.setText("Invalid Credentials");
                    loginInfo.setTextColor(Color.RED);
                    Log.d("STRING", "Login Gagal");
                }
            }
        });
    }

    public void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
