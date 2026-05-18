package com.karunadakote.models;

import java.util.List;

public class Fort {
    private String id;
    private String name;
    private String nameKannada;
    private String dynasty;
    private String builtYear;
    private String description;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private List<FortPoint> points;
    private List<PhotoChallenge> challenges;

    public Fort(String id, String name, String nameKannada, String dynasty,
                String builtYear, String description, String imageUrl,
                double latitude, double longitude,
                List<FortPoint> points, List<PhotoChallenge> challenges) {
        this.id = id;
        this.name = name;
        this.nameKannada = nameKannada;
        this.dynasty = dynasty;
        this.builtYear = builtYear;
        this.description = description;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.points = points;
        this.challenges = challenges;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getNameKannada() { return nameKannada; }
    public String getDynasty() { return dynasty; }
    public String getBuiltYear() { return builtYear; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public List<FortPoint> getPoints() { return points; }
    public List<PhotoChallenge> getChallenges() { return challenges; }
}
