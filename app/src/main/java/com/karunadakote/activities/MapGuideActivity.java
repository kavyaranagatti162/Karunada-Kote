package com.karunadakote.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karunadakote.R;
import com.karunadakote.models.Fort;
import com.karunadakote.models.FortPoint;
import com.karunadakote.utils.AudioService;
import com.karunadakote.utils.FortDataRepository;
import com.karunadakote.utils.PrefsManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapGuideActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int PERM_LOCATION = 100;
    private static final float PROXIMITY_RADIUS_METERS = 50f;

    private GoogleMap gMap;
    private Fort fort;
    private PrefsManager prefs;
    private Map<String, Marker> markerMap = new HashMap<>();

    private FusedLocationProviderClient fusedClient;
    private LocationCallback locationCallback;

    private AudioService audioService;
    private boolean audioBound = false;
    private Handler seekHandler = new Handler(Looper.getMainLooper());

    // Bottom audio player views
    private View audioPlayerPanel;
    private TextView tvAudioTitle, tvAudioTime;
    private SeekBar seekBarAudio;
    private ImageButton btnPlayPause;
    private ToggleButton toggleLang;

    private FortPoint currentPlayingPoint = null;

    private final ServiceConnection audioConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioService.AudioBinder b = (AudioService.AudioBinder) service;
            audioService = b.getService();
            audioBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            audioBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_guide);

        String fortId = getIntent().getStringExtra(MainActivity.EXTRA_FORT_ID);
        List<Fort> forts = FortDataRepository.getAllForts();
        for (Fort f : forts) { if (f.getId().equals(fortId)) { fort = f; break; } }
        if (fort == null) { finish(); return; }

        prefs = new PrefsManager(this);
        fusedClient = LocationServices.getFusedLocationProviderClient(this);

        ((TextView) findViewById(R.id.tv_map_fort_name)).setText(fort.getName());
        findViewById(R.id.btn_map_back).setOnClickListener(v -> finish());

        // Audio player panel
        audioPlayerPanel = findViewById(R.id.audio_player_panel);
        tvAudioTitle     = findViewById(R.id.tv_audio_title);
        tvAudioTime      = findViewById(R.id.tv_audio_time);
        seekBarAudio     = findViewById(R.id.seekbar_audio);
        btnPlayPause     = findViewById(R.id.btn_play_pause);
        toggleLang       = findViewById(R.id.toggle_lang);

        btnPlayPause.setOnClickListener(v -> togglePlayPause());
        toggleLang.setOnCheckedChangeListener((btn, isChecked) -> {
            prefs.setLanguage(isChecked ? "kn" : "en");
            if (currentPlayingPoint != null) playPoint(currentPlayingPoint);
        });
        toggleLang.setChecked("kn".equals(prefs.getLanguage()));

        // Map
        SupportMapFragment mapFrag = (SupportMapFragment)
            getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFrag != null) mapFrag.getMapAsync(this);

        // Bind audio service
        Intent svcIntent = new Intent(this, AudioService.class);
        bindService(svcIntent, audioConnection, Context.BIND_AUTO_CREATE);

        // Location updates
        setupLocationCallback();
        requestLocationPermission();
    }

    // ─── Map ──────────────────────────────────────────────────────────────────

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMarkerClickListener(this);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setCompassEnabled(true);

        // Try historic sepia map style
        try {
            boolean ok = gMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_historic));
            if (!ok) android.util.Log.w("MapGuide", "Map style parse failed");
        } catch (Exception e) {
            android.util.Log.e("MapGuide", "Map style not found, using default");
        }

        LatLng fortCenter = new LatLng(fort.getLatitude(), fort.getLongitude());
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fortCenter, 16f));

        // Add markers for each point
        for (FortPoint point : fort.getPoints()) {
            LatLng pos = new LatLng(point.getLatitude(), point.getLongitude());
            BitmapDescriptor icon = getMarkerIcon(point);
            MarkerOptions opts = new MarkerOptions()
                .position(pos)
                .title(point.getTitle())
                .snippet(point.getTitleKannada())
                .icon(icon);

            Marker m = gMap.addMarker(opts);
            if (m != null) {
                m.setTag(point.getId());
                markerMap.put(point.getId(), m);
            }
        }

        if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        String pointId = (String) marker.getTag();
        if (pointId == null) return false;

        for (FortPoint p : fort.getPoints()) {
            if (p.getId().equals(pointId)) {
                showPointDialog(p);
                return true;
            }
        }
        return false;
    }

    private void showPointDialog(FortPoint point) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fort_point);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ((TextView) dialog.findViewById(R.id.tv_point_title)).setText(point.getTitle());
        ((TextView) dialog.findViewById(R.id.tv_point_title_kn)).setText(point.getTitleKannada());

        boolean useKn = "kn".equals(prefs.getLanguage());
        String history = useKn ? point.getHistoryKn() : point.getHistoryEn();
        ((TextView) dialog.findViewById(R.id.tv_point_history)).setText(history);

        boolean visited = prefs.isPointVisited(point.getId());
        TextView tvBadge = dialog.findViewById(R.id.tv_visited_badge);
        tvBadge.setVisibility(visited ? View.VISIBLE : View.GONE);

        dialog.findViewById(R.id.btn_listen).setOnClickListener(v -> {
            dialog.dismiss();
            playPoint(point);
            prefs.markPointVisited(point.getId());
            updateMarkerIcon(point);
        });

        dialog.findViewById(R.id.btn_close_dialog).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // ─── Audio ────────────────────────────────────────────────────────────────

    private void playPoint(FortPoint point) {
        currentPlayingPoint = point;
        boolean useKn = "kn".equals(prefs.getLanguage());

        String audioName = useKn ? point.getAudioFileKn() : point.getAudioFileEn();
        int resId = getAudioResource(audioName);

        if (resId == 0) {
            // No actual audio file found — show TTS fallback toast
            Toast.makeText(this,
                "Audio: " + (useKn ? point.getHistoryKn() : point.getHistoryEn()),
                Toast.LENGTH_LONG).show();
            showAudioPanel(point, false);
            return;
        }

        Intent intent = new Intent(this, AudioService.class);
        intent.setAction(AudioService.ACTION_PLAY);
        intent.putExtra(AudioService.EXTRA_AUDIO_RES, resId);
        intent.putExtra(AudioService.EXTRA_TITLE, point.getTitle());
        startService(intent);

        showAudioPanel(point, true);
        startSeekBarUpdater();
    }

    private int getAudioResource(String name) {
        if (name == null || name.isEmpty()) return 0;
        return getResources().getIdentifier(name, "raw", getPackageName());
    }

    private void showAudioPanel(FortPoint point, boolean hasAudio) {
        audioPlayerPanel.setVisibility(View.VISIBLE);
        tvAudioTitle.setText(point.getTitle() + " • " + point.getTitleKannada());
        btnPlayPause.setImageResource(hasAudio ?
            android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
    }

    private void togglePlayPause() {
        if (audioBound && audioService != null) {
            if (audioService.isPlaying()) {
                Intent i = new Intent(this, AudioService.class);
                i.setAction(AudioService.ACTION_PAUSE);
                startService(i);
                btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
            } else {
                audioService.resumeAudio();
                btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
            }
        }
    }

    private void startSeekBarUpdater() {
        seekHandler.post(new Runnable() {
            @Override
            public void run() {
                if (audioBound && audioService != null) {
                    int pos = audioService.getCurrentPosition();
                    int dur = audioService.getDuration();
                    if (dur > 0) {
                        seekBarAudio.setMax(dur);
                        seekBarAudio.setProgress(pos);
                        tvAudioTime.setText(
                            formatTime(pos) + " / " + formatTime(dur));
                    }
                }
                seekHandler.postDelayed(this, 500);
            }
        });
    }

    private String formatTime(int ms) {
        int s = ms / 1000;
        return String.format("%d:%02d", s / 60, s % 60);
    }

    // ─── Location ─────────────────────────────────────────────────────────────

    private void setupLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult result) {
                if (result.getLastLocation() == null) return;
                double userLat = result.getLastLocation().getLatitude();
                double userLng = result.getLastLocation().getLongitude();
                checkProximityToPoints(userLat, userLng);
            }
        };
    }

    private void checkProximityToPoints(double lat, double lng) {
        for (FortPoint point : fort.getPoints()) {
            float[] dist = new float[1];
            android.location.Location.distanceBetween(
                lat, lng, point.getLatitude(), point.getLongitude(), dist);
            if (dist[0] < PROXIMITY_RADIUS_METERS && !prefs.isPointVisited(point.getId())) {
                // Auto-unlock and notify
                point.setUnlocked(true);
                runOnUiThread(() -> {
                    Toast.makeText(this,
                        "📍 You are near: " + point.getTitle() + "\nTap the marker to listen!",
                        Toast.LENGTH_LONG).show();
                    updateMarkerIcon(point);
                });
            }
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERM_LOCATION);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int req, @NonNull String[] perms,
                                           @NonNull int[] results) {
        super.onRequestPermissionsResult(req, perms, results);
        if (req == PERM_LOCATION && results.length > 0
            && results[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
            if (gMap != null) {
                if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    gMap.setMyLocationEnabled(true);
                }
            }
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;

        LocationRequest req = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .setMinUpdateIntervalMillis(3000)
            .build();
        fusedClient.requestLocationUpdates(req, locationCallback, Looper.getMainLooper());
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private BitmapDescriptor getMarkerIcon(FortPoint point) {
        boolean visited = prefs.isPointVisited(point.getId());
        if (visited) return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        switch (point.getIconType()) {
            case "gate":       return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            case "temple":     return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            case "watchtower": return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            case "well":       return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
            case "palace":     return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
            default:           return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
        }
    }

    private void updateMarkerIcon(FortPoint point) {
        Marker m = markerMap.get(point.getId());
        if (m != null) m.setIcon(getMarkerIcon(point));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        seekHandler.removeCallbacksAndMessages(null);
        if (audioBound) unbindService(audioConnection);
        fusedClient.removeLocationUpdates(locationCallback);
    }
}
