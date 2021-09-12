package com.emiliaengberg.cafestudent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerAdapterMackor extends RecyclerView.Adapter<RecyclerAdapterMackor.ViewHolder> {
    ArrayList<Macka> mMackaLista;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mackaNamn;
        TextView mackaBeskrivning;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Set up the views for the rows in the RecyclerView
            mackaNamn = itemView.findViewById(R.id.macka_namn);
            mackaBeskrivning = itemView.findViewById(R.id.macka_beskrivning);
        }
    }

    //Constructor that accepts an array as in parameter
    public RecyclerAdapterMackor(ArrayList<Macka> mackaLista){
        mMackaLista = mackaLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create inflater and viewholder that inflates the view in the viewholder
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_row_macka, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //Binds the data to the viewholder for each row
        final Macka macka = mMackaLista.get(position);
        holder.mackaNamn.setText(macka.getName());
        holder.mackaBeskrivning.setText(macka.getDescription());
    }

    @Override
    public int getItemCount() {
        //Total number of rows
        return mMackaLista.size();
    }
}
