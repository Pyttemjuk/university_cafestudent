package com.emiliaengberg.cafestudent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterBestallning extends RecyclerView.Adapter<RecyclerAdapterBestallning.ViewHolder> {

    ArrayList<Bestallning> mBestallningLista;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bestallningTidpunkt;
        TextView bestallningMackor;

        public ViewHolder(View itemView) {
            super(itemView);

            //Set up the views for the rows in the RecyclerView
            bestallningTidpunkt = itemView.findViewById(R.id.bestallning_tidpunkt);
            bestallningMackor = itemView.findViewById(R.id.bestallning_mackor);
        }
    }

    //Constructor that accepts an array as in parameter
    public RecyclerAdapterBestallning(ArrayList<Bestallning> bestallningLista) {
        mBestallningLista = bestallningLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create inflater and viewholder that inflates the view in the viewholder
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_row_bestallning, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Binds the data to the viewholder for each row
        final Bestallning bestallning = mBestallningLista.get(position);
        holder.bestallningTidpunkt.setText(bestallning.getTidpunkt());
        holder.bestallningMackor.setText(bestallning.getMackor());
    }

    @Override
    public int getItemCount() {
        //Total number of rows
        return mBestallningLista.size();
    }
}
