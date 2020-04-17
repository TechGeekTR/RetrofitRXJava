package com.ahmetgaffaroglu.retrofitrxjava.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmetgaffaroglu.retrofitrxjava.Model.CryptoModel;
import com.ahmetgaffaroglu.retrofitrxjava.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<CryptoModel> cryptoList;

    private String[] colors = {"#3b4054","#141728","#33363f","#193945","#4F1818","#1D5138","#1D5150","#424D4D"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList) { //Bu constuctor ı kurma amacımız, her RecyclerAdapter class ından instance alındığında bir adet liste istesin diye.
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());    //LayoutInflater farklı XML leri sınıflara bağlamamızda yardımcı oluyor.
        View view = layoutInflater.inflate(R.layout.raw_layout,parent,false); //raw_layout ı bakladık.
        return new RowHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {

        holder.bind(cryptoList.get(position),colors,position);

    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(CryptoModel cryptoModel, String[] colors, Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
            // raw_layout ta bulunan değişkenlerle burada bulunan değişkenleri bağlıyoruz.
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textName.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price);

        }
    }
}
