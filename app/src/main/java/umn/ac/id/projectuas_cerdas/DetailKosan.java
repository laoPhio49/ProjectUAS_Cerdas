package umn.ac.id.projectuas_cerdas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailKosan extends AppCompatActivity {

    TextView name , address, avroom, rooms, type, price, detail;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();



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
        } else {
            name.setText("");
            address.setText("");
            avroom.setText("");
            rooms.setText("");
            type.setText("");
            price.setText("");
            detail.setText("");
        }





    }
}
