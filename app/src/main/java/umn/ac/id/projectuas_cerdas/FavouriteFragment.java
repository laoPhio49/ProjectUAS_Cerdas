package umn.ac.id.projectuas_cerdas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    private ArrayList<Kos> kosArrayList;
    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference = database.getReference("kos");

    String emailUser;

    ArrayList<String> favourites = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        kosArrayList = new ArrayList<>();
        //View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        emailUser = getArguments().getString("email");

        Log.d("FAVFRAG", emailUser);

        recyclerView = view.findViewById(R.id.fav_recycler_view);

        DatabaseReference dbRef = database.getReference("user");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("USER", dataSnapshot.toString());
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    String email = (String) messageSnapshot.child("email").getValue();

                    Log.d("USER", email);

                    if (email.equals(emailUser)) {
                        // Cari di favourite
                        Log.d("FAVFRAG", messageSnapshot.child("favourite").getChildren().toString());
                        for(DataSnapshot dataFavourite : messageSnapshot.child("favourite").getChildren()){
                            if(dataFavourite.getValue() != "0"){
                                Log.d("FAVORITES",dataFavourite.getValue().toString());
                                favourites.add(dataFavourite.getValue().toString());
                            }
                        }
                    }
                    //kosArrayList.add(new Kos(name,address,"",details,type,owner,price,avrooms,rooms));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference dbRefKos = database.getReference("kos");
        dbRefKos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=1;
                Log.d("FAVORITES","satu: "+dataSnapshot.toString());

                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    Log.d("FAVORITES","Snapshot: "+messageSnapshot.toString());
                    String id = String.valueOf(i);
                    if(favourites.contains(id)){
                        String name = (String) messageSnapshot.child("nama").getValue();
                        String price = String.valueOf(messageSnapshot.child("harga").getValue());
                        String type = (String) messageSnapshot.child("jenis").getValue();
                        String address = (String) messageSnapshot.child("lokasi").getValue();
                        String details = (String) messageSnapshot.child("detail").getValue();
                        String avrooms = (String) messageSnapshot.child("kamarTersedia").getValue();
                        String rooms = (String) messageSnapshot.child("jumlahKamar").getValue();
                        String owner = (String) messageSnapshot.child("pemilikId").getValue();
                        Log.d("FAVORITES", name);
                        kosArrayList.add(new Kos(id, name,address,"",details,type,owner,price,avrooms,rooms));
                    }
                    i++;
                }
                KosAdapter kosAdapter = new KosAdapter(kosArrayList);

                recyclerView.setAdapter(kosAdapter);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("ARRAYLIST","NI KOS ANJENG : " + kosArrayList.size());




        return view;
    }
}
