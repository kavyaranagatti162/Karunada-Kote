package com.karunadakote.models;

public class PhotoChallenge {
    private String id;
    private String title;
    private String description;
    private String hint;
    private String relatedPointId;
    private boolean isCompleted;
    private String photoPath;

    public PhotoChallenge(String id, String title, String description,
                          String hint, String relatedPointId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hint = hint;
        this.relatedPointId = relatedPointId;
        this.isCompleted = false;
        this.photoPath = null;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getHint() { return hint; }
    public String getRelatedPointId() { return relatedPointId; }
    public boolean isCompleted() { return isCompleted; }
    public String getPhotoPath() { return photoPath; }

    public void setCompleted(boolean completed) { isCompleted = completed; }
    public void setPhotoPath(String path) { photoPath = path; }
}
