package com.srikanth.covid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewwHolder> {
    private Context mContext;
    private ArrayList<Item> mitemList;
    public Adapter(Context context, ArrayList<Item> itemList)
    {
        mContext=context;
        mitemList=itemList;

    }

    @NonNull
    @Override
    public ViewwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View V= LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
       return new ViewwHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewwHolder holder, int position) {
        Item currentItem=mitemList.get(position);


        String country=currentItem.getCountry();
        String conformed=currentItem.getConfirmed();
        String recovered=currentItem.getRecovered();
        String deaths=currentItem.getDeaths();
        String provience=currentItem.getProvince();


        holder.mTextviewCountry.setText(country);
        holder.mTextviewConformed.setText("Confirmed:"+conformed);
        holder.mTextviewRecovered.setText("Recovered:"+recovered);
        holder.mTextviewDeaths.setText("Deaths:"+deaths);
        holder.mTextviewProvience.setText(provience);


    }

    @Override
    public int getItemCount() {
        return mitemList.size();
    }
    public void filterList(ArrayList<Item> filteredList)
    {
        mitemList=filteredList;
        notifyDataSetChanged();
    }

    public class ViewwHolder extends RecyclerView.ViewHolder{

        public TextView mTextviewCountry;
        public TextView mTextviewConformed;
        public TextView mTextviewRecovered;
        public TextView mTextviewDeaths;
        public TextView mTextviewProvience;


        public ViewwHolder(@NonNull View itemView) {
            super(itemView);
            mTextviewCountry=itemView.findViewById(R.id.country);
            mTextviewConformed=itemView.findViewById(R.id.conformed);
            mTextviewRecovered=itemView.findViewById(R.id.recovered);
            mTextviewDeaths=itemView.findViewById(R.id.deaths);
            mTextviewProvience=itemView.findViewById(R.id.provience);

        }
    }

}
