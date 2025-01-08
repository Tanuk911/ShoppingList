package com.example.activityone;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewHolder extends RecyclerView.ViewHolder {

    TextView txtDate, txtLocation;
    Button btnView, btnDelete;
    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        txtDate = itemView.findViewById(R.id.item_view_txtDate);
        txtLocation = itemView.findViewById(R.id.item_view_txtLocation);

        btnView = itemView.findViewById(R.id.item_view_btnView);
        btnDelete = itemView.findViewById(R.id.item_view_btnDelete);

    }
}
