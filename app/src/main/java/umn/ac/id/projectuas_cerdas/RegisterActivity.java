package umn.ac.id.projectuas_cerdas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button regisBtn;
    EditText edtUsername, edtEmail, edtPass, edtConfPass, edtOccupation, edtPhone;

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
    }

    public Boolean checkValue(){


        return true;
    }
}
