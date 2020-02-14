package com.dvse.geo2020;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public BaseRecyclerViewAdapter(List<T> items) {
        this.items = items;
    }

    List<T> items;

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private Consumer<T> onItemClickListener;

    public void setOnItemClickListener(Consumer<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getView(parent, viewType);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.accept((T) v.getTag(R.id.item));
                }
            }
        });
        return onCreateViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        T item = getItem(position);
        setupView(holder, position, item);
        holder.itemView.setTag(R.id.item, item);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    protected T getItem(int position) {
        if (items != null && position >= 0 && position < items.size()) {
            return items.get(position);
        }
        return null;
    }

    protected abstract VH onCreateViewHolder(View view);

    protected abstract View getView(ViewGroup parent, int viewType);

    protected abstract void setupView(VH holder, int position, T item);

}