package com.example.signupaactivity;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<Item> items;
    ItemViewHolder holder;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        this.holder = holder;
        holder.itemNameTextView.setText(item.getName());
        holder.itemDescriptionTextView.setText(item.getDescription());
        holder.itemPriceTextView.setText("$"+item.getPrice());
        Glide.with(holder.itemView.getContext())
                .load("http:/"+HTTP.host+item.getImage())
                .into(holder.itemImageView);
    }

    @Override
    public int getItemCount() {
        if(items != null)
            return items.size();
        return 0;
    }

    public void setBitmap(Bitmap bitmap){
        holder.itemImageView.setImageBitmap(bitmap);
    }
}
