package umn.ac.id.projectuas_cerdas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    String nama, email, phone, occupation, jenis, index;
    EditText edtName, edtEmail, edtPhone;
    Spinner spnOccupation;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getBundleFromIntent();
        setCurrentInfo();
        buttonSaveOnClick();
    }

    public void buttonSaveOnClick(){
        btnSave = findViewById(R.id.edit_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EDITACCOUNT", "Save Button clicked");
                getValueFromEditText();
                addToDB();
            }
        });
    }

    public void getBundleFromIntent(){
        Bundle bundle = getIntent().getExtras();

        nama = bundle.getString("name");
        email = bundle.getString("email");
        email = email.replace("Email : ", "");
        phone = bundle.getString("phone");
        phone = phone.replace("Phone : ", "");
        occupation = bundle.getString("occupation");
        jenis = bundle.getString("jenis");
        index = bundle.getString("index");
    }

    public void setCurrentInfo(){
        edtName = findViewById(R.id.edit_name);
        edtEmail = findViewById(R.id.edit_email);
        edtPhone = findViewById(R.id.edit_phone);
        spnOccupation = findViewById(R.id.edit_occupation);

        int idOccupation = getIdOccupation(occupation);

        edtName.setText(nama);
        edtPhone.setText(phone);
        edtEmail.setText(email);
        spnOccupation.setSelection(idOccupation);
    }

    public int getIdOccupation(String occupation){
        if(occupation.equals("Admin")) return 1;
        if(occupation.equals("Mahasiswa")) return 2;
        if(occupation.equals("Pegawai Negri")) return 3;
        if(occupation.equals("Pegawai Swasta")) return 4;
        if(occupation.equals("Wirausaha")) return 5;
        if(occupation.equals("Pekerja Lepas")) return 6;
        if(occupation.equals("Dosen")) return 7;

        return 0;
    }

    public void getValueFromEditText(){
        nama = edtName.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();
        occupation = spnOccupation.getSelectedItem().toString();
        occupation = Integer.toString(getIdOccupation(occupation));
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

                //finalCount = count+1;

                Log.d("ADDTODB", "finalCount in onDataChange: " + finalCount);

                writeToDB("email", index, email, db);
//                writeToDB("jenisId", String.valueOf(finalCount), jenis, db);
                writeToDB("noHP", index, phone, db);
                writeToDB("pekerjaanId", index, occupation, db);
                writeToDB("username", index, nama, db);
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

    public void writeToDB(String type, String count, String value, FirebaseDatabase database){
        DatabaseReference databaseReference = database.getReference("user/" + count + "/" + type);
        databaseReference.setValue(value);
    }

}