package umn.ac.id.projectuas_cerdas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class KosAdapter extends RecyclerView.Adapter  {

    private ArrayList<Kos> kosArrayList;

    public KosAdapter(ArrayList<Kos> kosArrayList) {
        this.kosArrayList = kosArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_view, parent, false);

        return new KosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((KosViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return kosArrayList.size();
    }

    private class KosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView namaKos, hargaKos, jenisKos;
        private ImageView gambarKos;

        public KosViewHolder(View itemView){
            super(itemView);

            namaKos = itemView.findViewById(R.id.home_card_nama);
            hargaKos = itemView.findViewById(R.id.home_card_harga);
            jenisKos = itemView.findViewById(R.id.home_card_jenis);

            //gambarKos = itemView.findViewById(R.id.home_card_gambar_kos);

            itemView.setOnClickListener(this);
        }

        public void bindView(int pos){
            namaKos.setText(kosArrayList.get(pos).getName());
            jenisKos.setText(kosArrayList.get(pos).getJenis());
            hargaKos.setText(kosArrayList.get(pos).getHargaString());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
