package com.dvse.geo2020.study;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dvse.geo2020.BaseRecyclerViewAdapter;
import com.dvse.geo2020.R;

import java.util.List;

public class ContinentsAdapter extends BaseRecyclerViewAdapter<String, ContinentsAdapter.VH> {

    public ContinentsAdapter(List<String> continents) {
        super(continents);
    }

    @Override
    protected VH onCreateViewHolder(View view) {
        return new VH(view);
    }

    @Override
    protected View getView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.continent_item, parent, false);
    }

    @Override
    protected void setupView(VH holder, int position, String item) {
        holder.textView.setText(item);
    }

    static class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }

        TextView textView;
    }
}