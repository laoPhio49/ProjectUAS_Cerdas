package umn.ac.id.projectuas_cerdas;

import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Kos> kosArrayList = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    TextView homeInfo;
    ProgressBar progressBar;

    DatabaseReference databaseReference = database.getReference("kos");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.home_recycler_view);

//        homeInfo = container.findViewById(R.id.home_info);
//        progressBar = container.findViewById(R.id.home_progressBar);

        //addData();
        if(kosArrayList.isEmpty()){
//            progressBar.setVisibility(View.VISIBLE);
//            homeInfo.setVisibility(View.VISIBLE);
//            homeInfo.setText("Loading account information\nand Kos info");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i=1;
                    for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                        String id = String.valueOf(i++);
                        String name = (String) messageSnapshot.child("nama").getValue();
                        String price = String.valueOf(messageSnapshot.child("harga").getValue());
                        String type = (String) messageSnapshot.child("jenis").getValue();
                        String address = (String) messageSnapshot.child("lokasi").getValue();
                        String details = (String) messageSnapshot.child("detail").getValue();
                        String avrooms = (String) messageSnapshot.child("kamarTersedia").getValue();
                        String rooms = (String) messageSnapshot.child("jumlahKamar").getValue();
                        String owner = (String) messageSnapshot.child("pemilikId").getValue();
                        kosArrayList.add(new Kos(id, name,address,"",details,type,owner,price,avrooms,rooms));
                    }

//                    progressBar.setVisibility(View.GONE);
//                    homeInfo.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        KosAdapter kosAdapter = new KosAdapter(kosArrayList);

        recyclerView.setAdapter(kosAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }


}
