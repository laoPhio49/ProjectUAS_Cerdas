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
                    Register(email, password);
                    addToDB();
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
                if(task.isSuccessful()){
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

    Long finalCount;

    public void addToDB() {
        Log.d("ADDTODB", "In Function addToDB");
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = db.getReference("user");

        //Long finalCount;

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ADDTODB", "In Function onDataChange");
                Log.d("ADDTODB", "dataSnapshot.getChildrenCount() -> " + dataSnapshot.getChildrenCount());

                Long count = dataSnapshot.getChildrenCount();

                finalCount = count+1;

                Log.d("ADDTODB", "finalCount in onDataChange: " + finalCount);

                writeToDB("email", String.valueOf(finalCount), email, db);

                writeToDB("favourite/0", String.valueOf(finalCount), "0", db);

                writeToDB("jenisId", String.valueOf(finalCount), "3", db);
                writeToDB("noHP", String.valueOf(finalCount), phone, db);
                writeToDB("password", String.valueOf(finalCount), password, db);
                writeToDB("pekerjaanId", String.valueOf(finalCount), occupation, db);
                writeToDB("username", String.valueOf(finalCount), username, db);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ADDTODB", "In Function onCancelled");
            }
        };

        dbRef.addListenerForSingleValueEvent(valueEventListener);

        Log.d("ADDTODB", "finalCount: " + finalCount);

        dbRef.removeEventListener(valueEventListener);
    }

    public void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void writeToDB(String type, String count, String value, FirebaseDatabase database){
        DatabaseReference databaseReference = database.getReference("user/" + count + "/" + type);
        databaseReference.setValue(value);

    }
}


//    public void AddToDB(){
//        Log.d("ADD TO DB", "In Function AddToDB()");
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference userCount = database.getReference("user/userCount");
//
//        final long[] idx = new long[1];
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try{
//                    String childrenCount = dataSnapshot.getValue(String.class);
//
//                    Log.d("ADD TO DB", "count inside func: " + childrenCount);
//
//                    final String count = String.valueOf(idx[0]);
//
//                    writeToDB("email", count, email, database);
//                    writeToDB("jenisId", count, "3", database);
//                    writeToDB("noHP", count, phone, database);
//                    writeToDB("password", count, password, database);
//                    writeToDB("pekerjaanId", count, occupation, database);
//                    writeToDB("username", count, username, database);
//
//                    finalize();
//                }
//                catch (Throwable throwable){
//                    Log.e("ADD TO DB", throwable.getMessage());
//                }
//
//            }
//
//            @Override
//            protected void finalize() throws Throwable {
//                super.finalize();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("ADD TO DB", "Failed to read value. ", databaseError.toException());
//            }
//        };
//
//        userCount.addValueEventListener(valueEventListener);
//
//        Log.d("ADD TO DB", "count outside func: " + idx[0]);
//
//
//        userCount.removeEventListener(valueEventListener);
//    }
//
//    public void AddUserCount(){
//        Log.d("ADD TO DB", "finalCount: " + finalCount);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userCount = database.getReference("user/userCount");
//        userCount.setValue(finalCount);
//    }
//
//    public void writeToDB(String type, String count, String value, FirebaseDatabase database){
//        if(count.equals("countUser")){
//            DatabaseReference databaseReference = database.getReference("user/userCount");
//            databaseReference.setValue(value);
//        }
//        else{
//            DatabaseReference databaseReference = database.getReference("user/" + count + "/" + type);
//            databaseReference.setValue(value);
//        }
//
//    }