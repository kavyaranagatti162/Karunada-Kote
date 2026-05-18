package com.karunadakote.models;

public class FortPoint {
    private String id;
    private String title;
    private String titleKannada;
    private String historyEn;
    private String historyKn;
    private String audioFileEn;   // raw resource name (no extension)
    private String audioFileKn;
    private double latitude;
    private double longitude;
    private String iconType;      // "gate", "temple", "watchtower", "well", "palace"
    private boolean isUnlocked;
    private boolean isVisited;

    public FortPoint(String id, String title, String titleKannada,
                     String historyEn, String historyKn,
                     String audioFileEn, String audioFileKn,
                     double latitude, double longitude, String iconType) {
        this.id = id;
        this.title = title;
        this.titleKannada = titleKannada;
        this.historyEn = historyEn;
        this.historyKn = historyKn;
        this.audioFileEn = audioFileEn;
        this.audioFileKn = audioFileKn;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iconType = iconType;
        this.isUnlocked = false;
        this.isVisited = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getTitleKannada() { return titleKannada; }
    public String getHistoryEn() { return historyEn; }
    public String getHistoryKn() { return historyKn; }
    public String getAudioFileEn() { return audioFileEn; }
    public String getAudioFileKn() { return audioFileKn; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getIconType() { return iconType; }
    public boolean isUnlocked() { return isUnlocked; }
    public boolean isVisited() { return isVisited; }

    public void setUnlocked(boolean unlocked) { isUnlocked = unlocked; }
    public void setVisited(boolean visited) { isVisited = visited; }
}
