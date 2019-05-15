package umn.ac.id.projectuas_cerdas;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Kos> kosArrayList;
    private FirebaseUser mFirebaseUser;
    String email;

    @Override
    protected void onStart() {
        super.onStart();
        //mFirebaseUser.get
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = mFirebaseUser.getEmail();
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
                fragment = new HomeFragment();
                break;
            case R.id.search_menu:
                fragment = new SearchFragment();
                break;
            case R.id.favorite_menu:
                fragment = new FavouriteFragment();
                break;
            case R.id.account_menu:
                fragment = new AccountFragment();
                Bundle data = new Bundle();
                data.putString("email", email);
                fragment.setArguments(data);
//                ((TextView) fragment.getView().findViewById(R.id.tv_username)).setText("Someone else");
                break;
        }

        return loadFragment(fragment);
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
