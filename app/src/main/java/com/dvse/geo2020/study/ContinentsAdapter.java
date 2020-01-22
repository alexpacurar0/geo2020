package com.dvse.geo2020.study;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.dvse.geo2020.R;

import java.util.List;

public class ContinentsAdapter extends RecyclerView.Adapter<ContinentsAdapter.VH> {

    List<String> continents;

    public ContinentsAdapter(List<String> continents) {
        this.continents = continents;
    }

    private Consumer<String> onContinentClickListener;

    public void setOnContinentClickListener(Consumer<String> onContinentClickListener) {
        this.onContinentClickListener = onContinentClickListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View res = LayoutInflater.from(parent.getContext()).inflate(R.layout.continent_item, parent, false);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onContinentClickListener != null) {
                    onContinentClickListener.accept((String) v.getTag(R.id.continent));
                }
            }
        });
        return new VH(res);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String continent = continents.get(position);
        holder.textView.setText(continent);
        holder.itemView.setTag(R.id.continent, continent);
    }

    @Override
    public int getItemCount() {
        return continents.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }

        TextView textView;
    }
}