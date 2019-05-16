package umn.ac.id.projectuas_cerdas;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailKosan extends AppCompatActivity {

    TextView name , address, avroom, rooms, type, price, detail;

    int position;
    String str_pos;
    String kosId;
    FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kosan);
        Bundle bundle = getIntent().getExtras();

        name = findViewById(R.id.kostName);
        address = findViewById(R.id.kostAddress);
        avroom = findViewById(R.id.kostavRoom);
        rooms = findViewById(R.id.kostRooms);
        type = findViewById(R.id.kostType);
        price = findViewById(R.id.kostPrice);
        detail = findViewById(R.id.kostDetail);
        position = Integer.valueOf(bundle.getString("position"))+1;
        str_pos = String.valueOf(position);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        if(bundle!=null){
            name.setText(bundle.getString("nama"));
            String tmp_address = "Alamat :\n" .concat(bundle.getString("alamat"));
            address.setText(tmp_address);
            String tmp_avroom = "Kamar yang tersedia :\n" .concat(bundle.getString("avroom"));
            avroom.setText(tmp_avroom);
            String tmp_rooms = "Kamar Total :\n" .concat(bundle.getString("rooms"));
            rooms.setText(tmp_rooms);
            String tmp_tipe = "Tipe : " .concat(bundle.getString("tipe"));
            type.setText(tmp_tipe);
            String tmp_price = "Harga : " .concat(bundle.getString("price"));
            price.setText(tmp_price);
            String tmp_detail = "Detail :\n" .concat(bundle.getString("detail"));
            detail.setText(tmp_detail);
            kosId = bundle.getString("id");
        } else {
            name.setText("");
            address.setText("");
            avroom.setText("");
            rooms.setText("");
            type.setText("");
            price.setText("");
            detail.setText("");
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference databaseReference = database.getReference("user");

                final String useremail = user.getEmail();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                            //Log.d("USER", dataSnapshot.toString());
//                            Log.d("USER",dataSnapshot.child("email").getValue().toString());
                            String myEmail = (String) snapshot.child("email").getValue();
                            boolean isfavourite = false;
                            if(myEmail.equals(useremail)) {
                                Log.d("USER BOOL","email="+myEmail);
                                for (DataSnapshot dataFavourite : snapshot.child("favourite").getChildren()) {
                                    Log.d("USER Bool", "kosID: " + kosId);
                                    Log.d("USER Bool", "data: " + dataFavourite.getValue().toString());
                                    if (dataFavourite.getValue().equals(kosId)) {
                                        isfavourite = true;
                                        break;
                                    }
                                }
                                if(!isfavourite){
                                    Toast.makeText(DetailKosan.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                                    long idx = snapshot.child("favourite").getChildrenCount();
                                    snapshot.getRef().child("favourite").child(String.valueOf(idx)).setValue(kosId);
                                }
                                break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });




    }

}
