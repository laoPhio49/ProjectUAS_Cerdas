package umn.ac.id.projectuas_cerdas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    Button regisBtn;
    EditText edtUsername, edtEmail, edtPass, edtConfPass, edtOccupation, edtPhone;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findView();

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValue()){
                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT)
                            .show();

                    //Register();

                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Form not completed", Toast.LENGTH_SHORT)
                            .show();
                }

                //Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findView(){
        //Register Button
        regisBtn = findViewById(R.id.regisBtn);

        //EditText Value
        edtUsername = findViewById(R.id.useridreg);
        edtEmail = findViewById(R.id.useremailreg);
        edtPass = findViewById(R.id.userpassreg);
        edtConfPass = findViewById(R.id.userconfirmpassreg);
        edtOccupation = findViewById(R.id.useroccupation);
        edtPhone = findViewById(R.id.userphonenumber);

        username = edtUsername.getText().toString();
        password = edtPass.getText().toString();
    }

    public boolean checkValue(){
        if(edtUsername.getText().toString().isEmpty()){
            edtUsername.setError("Username is required");
            edtUsername.requestFocus();
            return false;
        }
        if(edtEmail.getText().toString().isEmpty()){
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return false;
        }
        if(edtPass.getText().toString().isEmpty()){
            edtPass.setError("Password is required");
            edtPass.requestFocus();
            return false;
        }
        if(edtConfPass.getText().toString().isEmpty()){
            edtConfPass.setError("Password confirmation is required");
            edtConfPass.requestFocus();
            return false;
        }
        if(!edtConfPass.getText().toString().equals(edtPass.getText().toString())){
            edtConfPass.setError("Password is not match");
            edtConfPass.requestFocus();
            return false;
        }
        if(edtOccupation.getText().toString().isEmpty()){
            edtOccupation.setError("Username is required");
            edtOccupation.requestFocus();
            return false;
        }
        if(edtPhone.getText().toString().isEmpty()){
            edtPhone.setError("Username is required");
            edtPhone.requestFocus();
            return false;
        }

        return true;
    }

}
