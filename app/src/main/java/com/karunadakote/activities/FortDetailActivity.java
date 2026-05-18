package com.karunadakote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.karunadakote.R;
import com.karunadakote.models.Fort;
import com.karunadakote.utils.FortDataRepository;
import com.karunadakote.utils.PrefsManager;

import java.util.List;

public class FortDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fort_detail);

        String fortId = getIntent().getStringExtra(MainActivity.EXTRA_FORT_ID);
        List<Fort> forts = FortDataRepository.getAllForts();
        Fort fort = null;
        for (Fort f : forts) {
            if (f.getId().equals(fortId)) { fort = f; break; }
        }
        if (fort == null) { finish(); return; }

        // Count visited points
        PrefsManager prefs = new PrefsManager(this);
        int visited = 0;
        for (com.karunadakote.models.FortPoint p : fort.getPoints()) {
            if (prefs.isPointVisited(p.getId())) visited++;
        }
        int total = fort.getPoints().size();

        ((TextView) findViewById(R.id.tv_fort_name)).setText(fort.getName());
        ((TextView) findViewById(R.id.tv_fort_name_kn)).setText(fort.getNameKannada());
        ((TextView) findViewById(R.id.tv_dynasty)).setText("Dynasty: " + fort.getDynasty());
        ((TextView) findViewById(R.id.tv_year)).setText("Built: " + fort.getBuiltYear());
        ((TextView) findViewById(R.id.tv_description)).setText(fort.getDescription());
        ((TextView) findViewById(R.id.tv_progress)).setText(
            "Explored: " + visited + " / " + total + " landmarks"
        );

        // Back arrow
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // Begin Tour
        Fort finalFort = fort;
        ((Button) findViewById(R.id.btn_begin_tour)).setOnClickListener(v -> {
            Intent intent = new Intent(this, MapGuideActivity.class);
            intent.putExtra(MainActivity.EXTRA_FORT_ID, finalFort.getId());
            startActivity(intent);
        });

        // Photo Challenges button
        ((Button) findViewById(R.id.btn_challenges)).setOnClickListener(v -> {
            Intent intent = new Intent(this, PhotoChallengeActivity.class);
            intent.putExtra(MainActivity.EXTRA_FORT_ID, finalFort.getId());
            startActivity(intent);
        });
    }
}
