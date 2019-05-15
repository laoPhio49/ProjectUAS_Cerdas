package umn.ac.id.projectuas_cerdas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.zip.Inflater;

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

        public KosViewHolder(final View itemView){
            super(itemView);

            namaKos = itemView.findViewById(R.id.home_card_nama);
            hargaKos = itemView.findViewById(R.id.home_card_harga);
            jenisKos = itemView.findViewById(R.id.home_card_jenis);

            //gambarKos = itemView.findViewById(R.id.home_card_gambar_kos);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext(), DetailKosan.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nama",kosArrayList.get(pos).getName());
                    bundle.putString("alamat",kosArrayList.get(pos).getAlamat());
                    bundle.putString("avroom",kosArrayList.get(pos).getKamarTersedia());
                    bundle.putString("rooms",kosArrayList.get(pos).getJumlahKamar());
                    bundle.putString("tipe",kosArrayList.get(pos).getJenis());
                    bundle.putString("price",kosArrayList.get(pos).getHarga());
                    bundle.putString("detail",kosArrayList.get(pos).getDetail());
                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            });
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
