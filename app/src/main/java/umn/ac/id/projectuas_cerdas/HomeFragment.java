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

import java.io.Serializable;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Kos> kosArrayList = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    RecyclerView recyclerView;
//    TextView homeInfo;
//    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        Log.d("HOMEFRAGMENT", "onStart");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("HOMEFRAGMENT", "onCreate");
    }

    DatabaseReference databaseReference = database.getReference("kos");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("HOMEFRAGMENT", "onCreateView");

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.home_recycler_view);

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

                    viewData();
//                    progressBar.setVisibility(View.GONE);
//                    homeInfo.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



        return view;
    }

    public void viewData(){

        KosAdapter kosAdapter = new KosAdapter(kosArrayList);
        recyclerView.setAdapter(kosAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("HOMEFRAGMENT", "onSaveInstanceState");

        outState.putSerializable("list", (Serializable) kosArrayList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("HOMEFRAGMENT", "onActivityCreated");

        if(savedInstanceState != null){
            kosArrayList = (ArrayList<Kos>) savedInstanceState.getSerializable("list");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("HOMEFRAGMENT", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("HOMEFRAGMENT", "onStop");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("HOMEFRAGMENT", "onResume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d("HOMEFRAGMENT", "onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d("HOMEFRAGMENT", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("HOMEFRAGMENT", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d("HOMEFRAGMENT", "onDetach");
    }
}
