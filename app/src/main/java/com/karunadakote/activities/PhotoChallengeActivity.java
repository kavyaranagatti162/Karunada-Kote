package com.karunadakote.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karunadakote.R;
import com.karunadakote.adapters.ChallengeAdapter;
import com.karunadakote.models.Fort;
import com.karunadakote.models.PhotoChallenge;
import com.karunadakote.utils.FortDataRepository;
import com.karunadakote.utils.PrefsManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhotoChallengeActivity extends AppCompatActivity {

    private static final int REQ_CAMERA    = 200;
    private static final int REQ_CAPTURE   = 201;

    private Fort fort;
    private PrefsManager prefs;
    private String currentChallengeId;
    private Uri photoUri;
    private ChallengeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_challenge);

        String fortId = getIntent().getStringExtra(MainActivity.EXTRA_FORT_ID);
        List<Fort> forts = FortDataRepository.getAllForts();
        for (Fort f : forts) { if (f.getId().equals(fortId)) { fort = f; break; } }
        if (fort == null) { finish(); return; }

        prefs = new PrefsManager(this);

        ((TextView) findViewById(R.id.tv_challenge_fort)).setText(fort.getName());
        int done = 0;
        for (PhotoChallenge c : fort.getChallenges()) {
            if (prefs.isPhotoChallengeCompleted(c.getId())) done++;
        }
        ((TextView) findViewById(R.id.tv_challenge_score)).setText(
            done + " / " + fort.getChallenges().size() + " Completed");

        RecyclerView rv = findViewById(R.id.rv_challenges);
        adapter = new ChallengeAdapter(fort.getChallenges(), prefs, challenge -> {
            currentChallengeId = challenge.getId();
            if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        findViewById(R.id.btn_challenge_back).setOnClickListener(v -> finish());
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = createImageFile();
                photoUri = FileProvider.getUriForFile(this,
                    getPackageName() + ".fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQ_CAPTURE);
            } catch (IOException e) {
                Toast.makeText(this, "Could not create photo file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String stamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile("KOTE_" + stamp + "_", ".jpg", dir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CAPTURE && resultCode == RESULT_OK) {
            if (currentChallengeId != null) {
                prefs.markPhotoChallengeCompleted(currentChallengeId);
                Toast.makeText(this, "🎉 Challenge Complete! Great photo!", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                // Update score
                int done = 0;
                for (PhotoChallenge c : fort.getChallenges()) {
                    if (prefs.isPhotoChallengeCompleted(c.getId())) done++;
                }
                ((TextView) findViewById(R.id.tv_challenge_score)).setText(
                    done + " / " + fort.getChallenges().size() + " Completed");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int req, @NonNull String[] perms,
                                           @NonNull int[] results) {
        super.onRequestPermissionsResult(req, perms, results);
        if (req == REQ_CAMERA && results.length > 0
            && results[0] == PackageManager.PERMISSION_GRANTED) {
            launchCamera();
        }
    }
}
