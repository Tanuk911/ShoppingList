package com.example.activityone;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListsActivity extends AppCompatActivity implements ListAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    Button btnBack;
    List<Item> items = new ArrayList<>();
    ArrayList<String> dates;
    ArrayList<String> locations;
    Map<Integer, ArrayList<String>> itemCollections;

    Dialog dialog_viewList;
    TextView txtDate, txtLocation, txtList, txtAmount;
    EditText edtxtNewList, edtxtAmount, edtxtRemoveItemNo;
    Spinner spinnerUnits;
    Button btnClose, btnPlus, btnMinus, btnEdit, btnConfirm;

    int currentItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lists);

        MyApplication app = (MyApplication) getApplicationContext();
        dates = app.dates;
        locations = app.locations;
        itemCollections = app.itemCollections;

        if (dates != null && locations != null) {
            for (int i = 0; i < dates.size(); i++) {
                items.add(new Item(dates.get(i), locations.get(i)));
            }
        }

        recyclerView = findViewById(R.id.lists_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListAdapter(getApplicationContext(), items, dates, locations, itemCollections, this));

        btnBack = findViewById(R.id.lists_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        //Dialog Box Related
        dialog_viewList = new Dialog(ListsActivity.this);
        dialog_viewList.setContentView(R.layout.view_list_dialog);
        dialog_viewList.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_viewList.setCancelable(true);

        txtDate = dialog_viewList.findViewById(R.id.view_list_txtDate);
        txtLocation = dialog_viewList.findViewById(R.id.view_list_txtLocation);
        btnClose = dialog_viewList.findViewById(R.id.view_list_close);
        spinnerUnits = dialog_viewList.findViewById(R.id.view_list_spinnerUnits);
        txtList = dialog_viewList.findViewById(R.id.view_list_txtListItem);
        txtAmount = dialog_viewList.findViewById(R.id.view_list_amount);
        edtxtNewList = dialog_viewList.findViewById(R.id.view_list_edtxtNewList);
        edtxtAmount = dialog_viewList.findViewById(R.id.view_list_edtxtAmount);
        edtxtRemoveItemNo = dialog_viewList.findViewById(R.id.view_list_edtxtRemoveItemNo);
        edtxtRemoveItemNo.setVisibility(View.GONE);
        btnPlus = dialog_viewList.findViewById(R.id.view_list_btnAdd);
        btnMinus = dialog_viewList.findViewById(R.id.view_list_btnMinus);
        btnMinus.setVisibility(View.GONE);
        btnEdit = dialog_viewList.findViewById(R.id.view_list_btnEdit);
        btnConfirm = dialog_viewList.findViewById(R.id.view_list_btnConfrim);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        ArrayList<String> unitsList = new ArrayList<>();
        unitsList.add("Kg");
        unitsList.add("L");
        unitsList.add("Packets");
        unitsList.add("No");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitsList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerUnits.setAdapter(adapter);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemtoList();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemFromList();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEdit();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmEdit();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void backToMain() {
        finish();
    }

    @Override
    public void onViewClick(int position) {
        currentItemPosition = position;
        // Get the data for the selected item
        String date = dates.get(position);
        String location = locations.get(position);

        txtDate.setText(date);
        txtLocation.setText(location);

        ArrayList<String> currentList = itemCollections.get(position);
        displayItemList(currentList);

        // Show the dialog
        dialog_viewList.show();
    }

    public void closeDialog() {
        dialog_viewList.dismiss();
    }

    private void displayItemList(ArrayList<String> itemList) {
        if (itemList == null || itemList.isEmpty()) {
            txtList.setText("No items added.");
        } else {
            StringBuilder builder = new StringBuilder();
            for (String item : itemList) {
                builder.append("#").append(itemList.indexOf(item)+1).append(" ").append(item).append("\n");
            }
            txtList.setText(builder.toString().trim());
        }
    }

    private void enableEdit() {
        btnPlus.setVisibility(View.GONE);
        btnMinus.setVisibility(View.VISIBLE);
        edtxtNewList.setVisibility(View.GONE);
        edtxtAmount.setVisibility(View.GONE);
        spinnerUnits.setVisibility(View.GONE);
        edtxtRemoveItemNo.setVisibility(View.VISIBLE);
    }

    private void confirmEdit() {
        btnMinus.setVisibility(View.GONE);
        btnPlus.setVisibility(View.VISIBLE);
        edtxtRemoveItemNo.setVisibility(View.GONE);
        edtxtNewList.setVisibility(View.VISIBLE);
        edtxtAmount.setVisibility(View.VISIBLE);
        spinnerUnits.setVisibility(View.VISIBLE);
    }

    public void addItemtoList() {
        if (currentItemPosition == -1) return; // No item selected

        String itemName = edtxtNewList.getText().toString().trim();
        String amount = edtxtAmount.getText().toString().trim();
        String unit = spinnerUnits.getSelectedItem().toString();

        if (itemName.isEmpty() || amount.isEmpty()) {
            edtxtNewList.setError("Enter item name");
            edtxtAmount.setError("Enter amount");
            return;
        }

        String newItem = itemName + " " + amount + " " + unit;

        // Add the item to the corresponding list
        ArrayList<String> currentList = itemCollections.get(currentItemPosition);
        if (currentList == null) {
            currentList = new ArrayList<>();
            itemCollections.put(currentItemPosition, currentList); // ******** Update global itemCollections ********
        }
        currentList.add(newItem);

        // Update the display in the dialog
        displayItemList(currentList);

        edtxtNewList.setText("");
        edtxtAmount.setText("");
    }

    private void removeItemFromList() {
        String removeItemNoString = edtxtRemoveItemNo.getText().toString().trim();
        if (removeItemNoString.isEmpty()) {
            edtxtRemoveItemNo.setError("Field is Empty");
            return;
        }
        int removeItemNo = Integer.parseInt(removeItemNoString);

        ArrayList<String> currentList = itemCollections.get(currentItemPosition);
        if (currentList == null || currentList.isEmpty()) {
            Toast.makeText(this, "No items to remove", Toast.LENGTH_SHORT).show();
            return;
        }

        if (removeItemNo < 1 || removeItemNo > currentList.size()) {
            edtxtRemoveItemNo.setError("Item not within Range");
            return;
        }

        currentList.remove(removeItemNo - 1);

        // Update the global itemCollections map
        itemCollections.put(currentItemPosition, currentList);

        displayItemList(currentList);
        edtxtRemoveItemNo.setText("");
    }
}