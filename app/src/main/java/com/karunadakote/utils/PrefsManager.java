package com.karunadakote.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class PrefsManager {
    private static final String PREFS_NAME = "karunada_kote_prefs";
    private static final String KEY_VISITED_PREFIX   = "visited_";
    private static final String KEY_COMPLETED_PHOTOS = "completed_photos";
    private static final String KEY_LANGUAGE         = "language";

    private final SharedPreferences prefs;

    public PrefsManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void markPointVisited(String pointId) {
        prefs.edit().putBoolean(KEY_VISITED_PREFIX + pointId, true).apply();
    }

    public boolean isPointVisited(String pointId) {
        return prefs.getBoolean(KEY_VISITED_PREFIX + pointId, false);
    }

    public void markPhotoChallengeCompleted(String challengeId) {
        Set<String> completed = new HashSet<>(getCompletedPhotoChallenges());
        completed.add(challengeId);
        prefs.edit().putStringSet(KEY_COMPLETED_PHOTOS, completed).apply();
    }

    public Set<String> getCompletedPhotoChallenges() {
        return prefs.getStringSet(KEY_COMPLETED_PHOTOS, new HashSet<>());
    }

    public boolean isPhotoChallengeCompleted(String challengeId) {
        return getCompletedPhotoChallenges().contains(challengeId);
    }

    public void setLanguage(String lang) { // "en" or "kn"
        prefs.edit().putString(KEY_LANGUAGE, lang).apply();
    }

    public String getLanguage() {
        return prefs.getString(KEY_LANGUAGE, "en");
    }
}
