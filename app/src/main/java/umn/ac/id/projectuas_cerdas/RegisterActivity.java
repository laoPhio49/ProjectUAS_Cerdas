package umn.ac.id.projectuas_cerdas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    Button regisBtn;
    //EditText edtUsername, edtEmail, edtPass, edtConfPass, edtOccupation, edtPhone;
    EditText edtUsername, edtEmail, edtPass, edtConfPass, edtPhone;
    Spinner spnOccupation;
    String username, password, email, occupation, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findView();

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValue()){
//                    Register(email, password);
                    AddToDB();
                    makeToast("Registered");
                }
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
        //edtOccupation = findViewById(R.id.useroccupation);
        spnOccupation = findViewById(R.id.useroccupation);
        edtPhone = findViewById(R.id.userphonenumber);
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
        if(!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
            edtEmail.setError("Please input a valid email");
            edtEmail.requestFocus();
            return false;
        }
        if(edtPass.getText().toString().isEmpty()){
            edtPass.setError("Password is required");
            edtPass.requestFocus();
            return false;
        }
        if(edtPass.getText().toString().length() < 6){
            edtPass.setError("Password length must be at least 6 characters");
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
//        if(edtOccupation.getText().toString().isEmpty()){
//            edtOccupation.setError("Occupation is required");
//            edtOccupation.requestFocus();
//            return false;
//        }
        if(spnOccupation.getSelectedItem().toString().isEmpty()){
            spnOccupation.requestFocus();
        }
        if(edtPhone.getText().toString().isEmpty()){
            edtPhone.setError("Phone number is required");
            edtPhone.requestFocus();
            return false;
        }

        username = edtUsername.getText().toString();
        password = edtPass.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();

        occupation = spnOccupation.getSelectedItem().toString();
        if(occupation.equals("Admin")) occupation = "1";
        if(occupation.equals("Mahasiswa")) occupation = "2";
        if(occupation.equals("Pegawai Negeri")) occupation = "3";
        if(occupation.equals("Pegawai Swasta")) occupation = "4";
        if(occupation.equals("Wirausaha")) occupation = "5";
        if(occupation.equals("Pekerja Lepas")) occupation = "6";
        if(occupation.equals("Dosen")) occupation = "7";

        return true;
    }

    public void Register(final String email, final String pass){
        Log.d("SIGN UP", "In function Register()");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Log.d("SIGN UP", "email: " + email);
                //Log.d("SIGN UP", "password: " + pass);
                if(task.isSuccessful()){
//                    AddToDB();
                    makeToast("User successfully registered");
                    Log.d("SIGN UP", "User successfully registered");
                }
                else{
                    makeToast("Oops... Something awful happened");
                    Log.e("SIGN UP", "Oops... Something awful happened");
                }
            }
        });

        finish();
    }

    public void AddToDB(){
        Log.d("SIGN UP", "In Function AddToDB()");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userCount = database.getReference("user");

        final long[] idx = new long[1];

        userCount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long childrenCount = dataSnapshot.getChildrenCount();
                idx[0] = childrenCount;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ADD TO DB", "Failed to read value. ", databaseError.toException());
            }
        });

        String count = Long.toString(idx[0]);

        Log.d("ADD TO DB", "count: " + count);

//        writeToDB("email", count, email, database);
//        writeToDB("jenisId", count, "3", database);
//        writeToDB("noHP", count, phone, database);
//        writeToDB("password", count, password, database);
//        writeToDB("pekerjaanId", count, occupation, database);
//        writeToDB("username", count, username, database);

        //databaseReferenceEmail.setValue(email);
    }

    public void writeToDB(String type, String count, String value, FirebaseDatabase database){
        if(count.equals("countUser")){
            DatabaseReference databaseReference = database.getReference("user/userCount");
            databaseReference.setValue(value);
        }
        else{
            DatabaseReference databaseReference = database.getReference("user/" + count + "/" + type);
            databaseReference.setValue(value);
        }

    }

    public void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
