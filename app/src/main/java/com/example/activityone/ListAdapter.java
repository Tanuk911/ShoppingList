package com.example.activityone;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.activityone.ListViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    Context context;
    List<Item> items;

    ArrayList<String> dates;
    ArrayList<String> locations;
    Map<Integer, ArrayList<String>> itemCollections;

    public interface OnItemClickListener {
        void onViewClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public ListAdapter(Context context, List<Item> items, ArrayList<String> dates, ArrayList<String> locations, Map<Integer, ArrayList<String>> itemCollections, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.items = items;
        this.dates = dates;
        this.locations = locations;
        this.itemCollections = itemCollections;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.txtDate.setText(items.get(position).getDate());
        holder.txtLocation.setText(items.get(position).getLocation());


        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onViewClick(holder.getAdapterPosition());
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the item from the list
                items.remove(holder.getAdapterPosition());
                dates.remove(holder.getAdapterPosition());
                locations.remove(holder.getAdapterPosition());
                itemCollections.remove(holder.getAdapterPosition());

                // Adjust the map keys for subsequent items (if needed)
                Map<Integer, ArrayList<String>> updatedMap = new HashMap<>();
                int index = 0;
                for (Map.Entry<Integer, ArrayList<String>> entry : itemCollections.entrySet()) {
                    updatedMap.put(index, entry.getValue());
                    index++;
                }
                itemCollections.clear();
                itemCollections.putAll(updatedMap);

                // Notify adapter about item removal
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), items.size()); // Update positions of subsequent items
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
