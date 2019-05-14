package umn.ac.id.projectuas_cerdas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Kos> kosArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.home_recycler_view);

        addData();

        KosAdapter kosAdapter = new KosAdapter(kosArrayList);

        recyclerView.setAdapter(kosAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void addData(){
        kosArrayList = new ArrayList<>();
        kosArrayList.add(new Kos("Kos Aldric", "Pascal Timur 16", "", "Kos inet kampang", "Campur", 2000000, 10, 100));
        kosArrayList.add(new Kos("Kos Fudan", "Apartemen Scientia", "", "Ga jadi pindah", "Campur", 5000000, 2, 3));
        kosArrayList.add(new Kos("Kos Mbak Dewi", "Kelapa Dia", "", "Gatau deh ini mah", "Wanita", 1500000, 5, 10));
    }
}
