package com.karunadakote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karunadakote.R;
import com.karunadakote.adapters.FortListAdapter;
import com.karunadakote.models.Fort;
import com.karunadakote.utils.FortDataRepository;
import com.karunadakote.utils.PrefsManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_FORT_ID = "fort_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvGreeting = findViewById(R.id.tv_greeting);
        RecyclerView rv = findViewById(R.id.rv_forts);

        // Set greeting based on time
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        if (hour < 12)      tvGreeting.setText("ಶುಭೋದಯ • Good Morning");
        else if (hour < 17) tvGreeting.setText("ಶುಭ ಮಧ್ಯಾಹ್ನ • Good Afternoon");
        else                tvGreeting.setText("ಶುಭ ಸಂಜೆ • Good Evening");

        List<Fort> forts = FortDataRepository.getAllForts();
        PrefsManager prefs = new PrefsManager(this);

        FortListAdapter adapter = new FortListAdapter(forts, prefs, fort -> {
            Intent intent = new Intent(this, FortDetailActivity.class);
            intent.putExtra(EXTRA_FORT_ID, fort.getId());
            startActivity(intent);
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
}
