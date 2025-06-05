package com.example.rewindpassion;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HostActivity extends AppCompatActivity {

    Spinner playerDropdown;
    Button allowButton, denyButton;
    RecyclerView playerView;
    ArrayList<String> allPlayers;
    ArrayList<String> acceptedPlayers;
    PlayerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_host);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hostLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playerDropdown = findViewById(R.id.playerDropdown);
        allowButton = findViewById(R.id.allowButton);
        denyButton = findViewById(R.id.denyButton);
        playerView = findViewById(R.id.playerView);

        // Dummy players (to be removed later)
        allPlayers = new ArrayList<>();
        allPlayers.add("Player A");
        allPlayers.add("Player B");
        allPlayers.add("Player C");

        // Set up of the dropdown
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allPlayers);
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerDropdown.setAdapter(dropdownAdapter);

        // Adding dummy players to dropdown
        acceptedPlayers = new ArrayList<>();
        adapter = new PlayerListAdapter(acceptedPlayers);
        playerView.setLayoutManager(new LinearLayoutManager(this));
        playerView.setAdapter(adapter);

        // Logic for 'Allow' button
        allowButton.setOnClickListener(v -> {
            String selected = (String) playerDropdown.getSelectedItem();
            if (selected != null && !acceptedPlayers.contains(selected)) {
                acceptedPlayers.add(selected);
                adapter.notifyDataSetChanged();
                allPlayers.remove(selected);
                dropdownAdapter.notifyDataSetChanged();
            }
        });

        // Logic for 'Deny' button
        denyButton.setOnClickListener(v -> {
            String selected = (String) playerDropdown.getSelectedItem();
            if (selected != null) {
                allPlayers.remove(selected);
                dropdownAdapter.notifyDataSetChanged();
            }
        });
    }
}