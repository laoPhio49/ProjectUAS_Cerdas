package umn.ac.id.projectuas_cerdas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Kos> kosArrayList;
    private FirebaseUser mFirebaseUser;
    String emailUser;

    TextView homeInfo;
    ProgressBar progressBar;

    private User user;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeInfo = findViewById(R.id.home_info);
        progressBar = findViewById(R.id.home_progressBar);
        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        emailUser = mFirebaseUser.getEmail();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = firebaseDatabase.getReference("user");

        Log.d("USER", mFirebaseUser.getEmail());
        Log.d("USER", dbRef.toString());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("USER", dataSnapshot.toString());
                int i=1;
                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){

                    String email = (String) messageSnapshot.child("email").getValue();

                    Log.d("USER", "INI "+email);

                    if(email.equals(emailUser)){
                        String name = (String) messageSnapshot.child("username").getValue();
                        String phone = String.valueOf(messageSnapshot.child("noHP").getValue());
                        //String occupation = changeOccupation((String) messageSnapshot.child("pekerjaanId").getValue());
                        String occupation = (String) messageSnapshot.child("pekerjaanId").getValue();
                        occupation = changeOccupation(occupation);
                        Log.d("USER", "After changing occupation to " + occupation);
                        String jenisId = (String) messageSnapshot.child("jenisId").getValue();

                        Log.d("USER", "snapshot fav -> " + messageSnapshot.child("favourite").getValue());
                        //String fav = (String) messageSnapshot.child("favourite").getValue();

                        Log.d("USER", "name: " + name);
                        Log.d("USER", "phone: " + phone);
                        Log.d("USER", "occupation: " + occupation);
                        Log.d("USER", "jenisId: " + jenisId);
                        Log.d("USER", "fav: " + "");

                        User temp = new User(name, email, phone, occupation, "", jenisId);
                        user = temp;
                        user.setIdx(Integer.toString(i));
                        Log.d("USER", "user.getNama() -> " + user.getNama());
                    }
                    i++;
                    //kosArrayList.add(new Kos(name,address,"",details,type,owner,price,avrooms,rooms));
                }
            }

            public String changeOccupation(String id){
                Log.d("USER", "pekerjaanId: " + id);
                if(id.equals("1")) return "Admin";
                if(id.equals("2")) return "Mahasiswa";
                if(id.equals("3")) return "Pegawai Negri";
                if(id.equals("4")) return "Pegawai Swasta";
                if(id.equals("5")) return "Wirausaha";
                if(id.equals("6")) return "Pekerja Lepas";
                if(id.equals("7")) return "Dosen";
                else return "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.home_menu:
                progressBar.setVisibility(View.VISIBLE);
                homeInfo.setVisibility(View.VISIBLE);
                homeInfo.setText("Loading account information\nand Kos info");

                fragment = new HomeFragment();

                progressBar.setVisibility(View.GONE);
                homeInfo.setVisibility(View.GONE);

                break;
            case R.id.search_menu:
                Intent myIntent = new Intent(getBaseContext(), SearchFragment.class);
                startActivity(myIntent);
                break;
            case R.id.favorite_menu:
                fragment = new FavouriteFragment();

                Bundle dataFav = new Bundle();
                dataFav.putString("email", emailUser);
                fragment.setArguments(dataFav);
                Log.d("User Bool", "email"+emailUser);

                break;
            case R.id.account_menu:
                if(user != null){
                    fragment = new AccountFragment();
                    Bundle data = new Bundle();

                    Log.d("USER", "name: " + user.getNama());
                    Log.d("USER", "phone: " + user.getPhone());
                    Log.d("USER", "occupation: " + user.getOccupation());
                    Log.d("USER", "jenisId: " + user.getType());
                    Log.d("USER", "email: " + emailUser);

                    data.putString("email", emailUser);
                    data.putString("username", user.getNama());
                    data.putString("phone", user.getPhone());
                    data.putString("occupation", user.getOccupation());
                    data.putString("jenis", user.getType());
                    data.putString("index", user.getIdx());

                    fragment.setArguments(data);

                }
                else{
                    makeToast("Account not fully loaded");
                }
                break;

        }

        return loadFragment(fragment);
    }

    public void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //firebaseAuth.getInstance();
        firebaseAuth.signOut();
        Log.d("SIGN OUT", "User has been signed out");
    }
}
