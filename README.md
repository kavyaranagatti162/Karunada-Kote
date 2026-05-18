
# ಕರ್ನಾಡ ಕೋಟೆ ಗೈಡ್ — Karunada Kote Guide
### Karnataka Pride: AI-Powered Heritage Fort Guide

---

## 📱 Project Overview
A location-aware Android tour guide app that brings Karnataka's magnificent forts to life
through audio narration, interactive maps, and photo challenges.

**Forts included:** Chitradurga, Bidar, Bekal

---

## 🛠 Setup Instructions

### Step 1: Open in Android Studio
1. Open Android Studio (Hedgehog 2023.1.1 or newer recommended)
2. File → Open → select the `KarunadaKote` folder
3. Wait for Gradle sync to complete

### Step 2: Add Google Maps API Key
1. Go to https://console.cloud.google.com
2. Create a project → Enable **Maps SDK for Android** + **Places API**
3. Create an API Key → restrict it to your app's package name `com.karunadakote`
4. Open `app/src/main/res/values/strings.xml`
5. Replace `YOUR_GOOGLE_MAPS_API_KEY_HERE` with your actual key

### Step 3: Add Audio Files (Optional but Recommended)
Place `.mp3` audio narration files in `app/src/main/res/raw/`:

| File name               | Content                              |
|------------------------|--------------------------------------|
| `audio_ctr_gate1_en.mp3` | Ellamarasamma Gate — English narration |
| `audio_ctr_gate1_kn.mp3` | Ellamarasamma Gate — Kannada narration |
| `audio_ctr_onake_en.mp3` | Onake Obavva story — English          |
| `audio_ctr_onake_kn.mp3` | Onake Obavva story — Kannada          |
| `audio_ctr_temple_en.mp3`| Hidimbeshwara Temple — English        |
| `audio_ctr_temple_kn.mp3`| Hidimbeshwara Temple — Kannada        |
| `audio_ctr_tower_en.mp3` | Aakasha Deepa Tower — English         |
| `audio_ctr_tower_kn.mp3` | Aakasha Deepa Tower — Kannada         |
| `audio_ctr_well_en.mp3`  | Obavva's Well — English               |
| `audio_ctr_well_kn.mp3`  | Obavva's Well — Kannada               |
| `audio_bidar_gate_en.mp3`| Sharza Gate — English                 |
| `audio_bidar_gate_kn.mp3`| Sharza Gate — Kannada                 |

> **Without audio files:** The app still works — it shows a toast with the text history
> instead. The MediaPlayer will gracefully skip if the file doesn't exist.

### Step 4: Run the App
- Connect an Android device (API 24+) or use an emulator
- Click ▶ Run in Android Studio

---

## 🏗 Project Architecture

```
KarunadaKote/
├── activities/
│   ├── SplashActivity.java        ← Animated historic splash
│   ├── MainActivity.java          ← Fort selection list
│   ├── FortDetailActivity.java    ← Fort info + navigation hub
│   ├── MapGuideActivity.java      ← 🗺 Core: Map + GPS + Audio Player
│   └── PhotoChallengeActivity.java← 📷 Photo missions
│
├── adapters/
│   ├── FortListAdapter.java       ← RecyclerView for fort list
│   └── ChallengeAdapter.java      ← RecyclerView for challenges
│
├── models/
│   ├── Fort.java                  ← Fort data model
│   ├── FortPoint.java             ← Individual landmark model
│   └── PhotoChallenge.java        ← Photo challenge model
│
└── utils/
    ├── FortDataRepository.java    ← All fort/landmark data (seed data)
    ├── AudioService.java          ← Foreground service: background audio
    └── PrefsManager.java          ← SharedPreferences: visited/unlocked state
```

---

## ✅ Success Criteria Checklist

| Requirement | Implementation |
|-------------|---------------|
| Audio plays with screen locked | `AudioService` (Foreground Service) + `PARTIAL_WAKE_LOCK` |
| Map shows user position | `FusedLocationProviderClient` + `gMap.setMyLocationEnabled(true)` |
| Historic UI (parchment) | Custom color palette: `#2C1A0E`, `#C9A84C`, `#FDF3DC` |
| State tracking (visited/unlocked) | `PrefsManager` with SharedPreferences |
| Proximity detection (beacon sim) | `Location.distanceBetween()` within 50m radius |
| Kannada + English support | Language toggle on map screen |
| Photo challenges | Camera intent + FileProvider + completion tracking |

---

## 🔑 Key Technical Features

### Background Audio (Screen Lock)
```java
// AudioService.java — runs as Foreground Service
mediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
startForeground(NOTIFICATION_ID, buildNotification(...));
```

### Proximity-Based Unlock (Simulated Beacon)
```java
// MapGuideActivity.java
float[] dist = new float[1];
Location.distanceBetween(userLat, userLng, pointLat, pointLng, dist);
if (dist[0] < 50f) { /* auto-unlock and notify */ }
```

### Historic Map Style
The `res/raw/map_style_historic.json` applies a sepia-toned Google Maps style
that looks like an aged parchment map — matching the app's visual theme.

---

## 🚀 Enhancements You Can Add

1. **Text-to-Speech fallback** — Use Android's `TextToSpeech` API to auto-narrate
   history text if no audio file exists (great for adding new forts quickly)

2. **Offline maps** — Use Google Maps Offline or OpenStreetMap for fort areas

3. **AR overlay** — Use ARCore to overlay fort gate names on camera view

4. **Leaderboard** — Firebase Realtime Database to rank most-visited students

5. **Fort Quiz** — After visiting all points, unlock a knowledge quiz

---

## 📞 Credits
- Fort historical data: Karnataka State Department of Archaeology and Museums
- Map styling: Google Maps Styling Wizard (https://mapstyle.withgoogle.com)
- App concept: Karnataka Pride Heritage Tourism Initiative
=======
Added README.md with complete project overview, features, technologies used, installation steps, and application objectives for Karunada Kote. Improved repository documentation and project presentation for better understanding and collaboration.

