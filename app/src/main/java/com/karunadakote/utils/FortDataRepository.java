package com.karunadakote.utils;

import com.karunadakote.models.Fort;
import com.karunadakote.models.FortPoint;
import com.karunadakote.models.PhotoChallenge;

import java.util.ArrayList;
import java.util.List;

public class FortDataRepository {

    public static List<Fort> getAllForts() {
        List<Fort> forts = new ArrayList<>();
        forts.add(createChitradurgaFort());
        forts.add(createBidarFort());
        forts.add(createBekalFort());
        return forts;
    }

    // ─── CHITRADURGA FORT ───────────────────────────────────────────────────────
    private static Fort createChitradurgaFort() {
        List<FortPoint> points = new ArrayList<>();

        points.add(new FortPoint(
            "ctr_gate1",
            "Ellamarasamma Gate",
            "ಎಲ್ಲಮ್ಮರಸಮ್ಮ ದ್ವಾರ",
            "The Ellamarasamma Gate is the first of seven gates that protect Chitradurga Fort. " +
            "Built in the 18th century by Hyder Ali, this gateway was designed as a defensive maze — " +
            "forcing enemies to turn multiple times, making cavalry charges impossible. " +
            "Notice the iron spikes on the door to repel war elephants.",
            "ಎಲ್ಲಮ್ಮರಸಮ್ಮ ದ್ವಾರವು ಚಿತ್ರದುರ್ಗ ಕೋಟೆಯ ಏಳು ದ್ವಾರಗಳಲ್ಲಿ ಮೊದಲನೆಯದು.",
            "audio_ctr_gate1_en",
            "audio_ctr_gate1_kn",
            14.2303, 76.3971,
            "gate"
        ));

        points.add(new FortPoint(
            "ctr_onake",
            "Onake Obavva Kindi",
            "ಒನಕೆ ಒಬವ್ವನ ಕಿಂಡಿ",
            "This narrow crevice in the fort wall is where the legendary Onake Obavva " +
            "single-handedly fought off an entire army of Hyder Ali in 1779. " +
            "Her husband was a watchman. While he was away fetching water, she discovered " +
            "enemy soldiers sneaking through this gap — and she fought them off using only " +
            "her onake (a wooden pestle used for grinding). She is Karnataka's greatest unsung hero.",
            "ಈ ಕಿಂಡಿಯ ಮೂಲಕ ಹೈದರ್ ಅಲಿಯ ಸೈನಿಕರು ನುಸುಳುತ್ತಿದ್ದಾಗ, ಒನಕೆ ಒಬವ್ವ ಒಂಟಿಯಾಗಿ ಹೋರಾಡಿದಳು.",
            "audio_ctr_onake_en",
            "audio_ctr_onake_kn",
            14.2298, 76.3965,
            "historical"
        ));

        points.add(new FortPoint(
            "ctr_temple",
            "Hidimbeshwara Temple",
            "ಹಿಡಿಂಬೇಶ್ವರ ದೇವಸ್ಥಾನ",
            "The Hidimbeshwara Temple sits at the highest point of Chitradurga Fort, " +
            "dedicated to Lord Shiva. Dating back to the 10th century, it was built by " +
            "the Rashtrakuta dynasty. The temple's unique feature is its water harvesting system — " +
            "16 interconnected tanks that collected rainwater for the fort's 40,000 residents " +
            "during sieges. Ancient engineers carved these from solid granite rock.",
            "ಹಿಡಿಂಬೇಶ್ವರ ದೇವಸ್ಥಾನವು ಶಿವನಿಗೆ ಸಮರ್ಪಿತವಾಗಿದ್ದು, ರಾಷ್ಟ್ರಕೂಟ ರಾಜವಂಶದಿಂದ ನಿರ್ಮಿಸಲ್ಪಟ್ಟಿದೆ.",
            "audio_ctr_temple_en",
            "audio_ctr_temple_kn",
            14.2312, 76.3980,
            "temple"
        ));

        points.add(new FortPoint(
            "ctr_watchtower",
            "Aakasha Deepa Tower",
            "ಆಕಾಶ ದೀಪ ಗೋಪುರ",
            "From this watchtower, sentinels could see 20 kilometers in every direction. " +
            "At night, a massive oil lamp — the Aakasha Deepa — was lit here as a beacon " +
            "for friendly armies. The Nayaka rulers stationed a relay of 300 watchmen here, " +
            "each serving 4-hour shifts. Signal fires could communicate messages to the " +
            "next fort in under 15 minutes — an ancient telegraph system.",
            "ಈ ಗೋಪುರದಿಂದ ಕಾವಲುಗಾರರು 20 ಕಿಲೋಮೀಟರ್ ದೂರ ನೋಡಬಹುದಾಗಿತ್ತು.",
            "audio_ctr_tower_en",
            "audio_ctr_tower_kn",
            14.2320, 76.3988,
            "watchtower"
        ));

        points.add(new FortPoint(
            "ctr_well",
            "Obavva's Well",
            "ಒಬವ್ವನ ಬಾವಿ",
            "This 300-year-old stepped well was the primary water source for the fort garrison. " +
            "The steps spiral down 40 feet into the earth — built so that even during dry season, " +
            "soldiers could access water. Notice the grooves worn into the stone steps by thousands " +
            "of feet over centuries. A hidden tunnel once connected this well directly to the " +
            "Hidimbeshwara Temple, allowing priests safe passage during attacks.",
            "ಈ 300 ವರ್ಷ ಹಳೆಯ ಮೆಟ್ಟಿಲು ಬಾವಿ ಕೋಟೆಯ ಮುಖ್ಯ ನೀರಿನ ಮೂಲವಾಗಿತ್ತು.",
            "audio_ctr_well_en",
            "audio_ctr_well_kn",
            14.2290, 76.3958,
            "well"
        ));

        // Photo Challenges
        List<PhotoChallenge> challenges = new ArrayList<>();
        challenges.add(new PhotoChallenge(
            "ch_onake",
            "Hero's Crevice",
            "Find the Onake Obavva Kindi — the narrow gap in the fort wall — and photograph it!",
            "Look for the narrowest point in the eastern wall, near a small shrine with flowers.",
            "ctr_onake"
        ));
        challenges.add(new PhotoChallenge(
            "ch_iron_spike",
            "Elephant Spikes",
            "Find the iron anti-elephant spikes on the main gate door and photograph them.",
            "Look at the lower half of the main wooden door — the spikes are iron and still sharp!",
            "ctr_gate1"
        ));
        challenges.add(new PhotoChallenge(
            "ch_water_tank",
            "Rock-Cut Tank",
            "Find one of the 16 ancient water tanks carved from granite and photograph its steps.",
            "Near the Hidimbeshwara Temple, there are rock-cut tanks on both sides.",
            "ctr_temple"
        ));

        return new Fort(
            "chitradurga",
            "Chitradurga Fort",
            "ಚಿತ್ರದುರ್ಗ ಕೋಟೆ",
            "Nayaka / Hyder Ali",
            "10th–18th Century",
            "Chitradurga Fort, also called 'Kallina Kote' (Stone Fort), is built on seven " +
            "concentric layers of granite hills. It has 19 gates, 38 bastions, 4 secret " +
            "entrances, and a sophisticated ancient water management system. " +
            "It is home to the legend of Onake Obavva — Karnataka's most celebrated warrior woman.",
            "",
            14.2303, 76.3971,
            points,
            challenges
        );
    }

    // ─── BIDAR FORT ─────────────────────────────────────────────────────────────
    private static Fort createBidarFort() {
        List<FortPoint> points = new ArrayList<>();

        points.add(new FortPoint(
            "bidar_gate",
            "Sharza Gate",
            "ಶರ್ಜಾ ದ್ವಾರ",
            "The Sharza Gate is named after the Persian word for lion. Built by Ahmad Shah I " +
            "of the Bahmani Sultanate in 1432, this gate combines Persian, Turkish, and " +
            "Deccan architectural styles. The decorative tile work — called Bidriware — " +
            "uses an alloy of zinc and copper inlaid with silver, a craft still practiced " +
            "in Bidar today and recognized by the Geographical Indication tag.",
            "ಶರ್ಜಾ ದ್ವಾರವನ್ನು ಅಹಮದ್ ಷಾ I 1432 ರಲ್ಲಿ ನಿರ್ಮಿಸಿದರು.",
            "audio_bidar_gate_en",
            "audio_bidar_gate_kn",
            17.9118, 77.5197,
            "gate"
        ));

        points.add(new FortPoint(
            "bidar_rangin",
            "Rangin Mahal",
            "ರಂಗಿನ್ ಮಹಲ್",
            "The Rangin Mahal — Palace of Colors — was the private residence of Bahmani Sultan " +
            "Ali Barid Shah. The walls are decorated with mother-of-pearl inlay and colored " +
            "tiles in geometric patterns. This palace influenced the architecture of the " +
            "Bijapur Sultanate and eventually the Mughal court. Notice how the corridors " +
            "create natural ventilation — the fort stayed cool even in 45°C summer heat.",
            "ರಂಗಿನ್ ಮಹಲ್ ಬಹಮನಿ ಸುಲ್ತಾನ ಅಲಿ ಬರಿದ್ ಷಾ ಅವರ ಖಾಸಗಿ ನಿವಾಸವಾಗಿತ್ತು.",
            "audio_bidar_rangin_en",
            "audio_bidar_rangin_kn",
            17.9105, 77.5180,
            "palace"
        ));

        points.add(new FortPoint(
            "bidar_moat",
            "The Triple Moat",
            "ತ್ರಿಪದ ಕಂದಕ",
            "Bidar Fort is protected by three concentric moats — unique in all of India. " +
            "The outer moat is 10 meters wide and was filled with water from a Persian " +
            "wheel system. The middle moat was kept dry and filled with thorny bushes. " +
            "The innermost moat surrounded the royal palace and was rumored to house " +
            "trained crocodiles. This triple defense made the fort nearly impenetrable " +
            "for over 200 years.",
            "ಬಿದರ್ ಕೋಟೆಯು ಭಾರತದಲ್ಲೇ ಅನನ್ಯವಾದ ಮೂರು ಕಂದಕಗಳಿಂದ ರಕ್ಷಿಸಲ್ಪಟ್ಟಿದೆ.",
            "audio_bidar_moat_en",
            "audio_bidar_moat_kn",
            17.9125, 77.5210,
            "watchtower"
        ));

        List<PhotoChallenge> challenges = new ArrayList<>();
        challenges.add(new PhotoChallenge(
            "ch_bidriware",
            "Bidriware Tiles",
            "Find the original Bidriware tile decoration at the Sharza Gate and photograph the geometric patterns.",
            "Look above the main archway — the dark metallic tiles with silver inlay are original 15th century work.",
            "bidar_gate"
        ));
        challenges.add(new PhotoChallenge(
            "ch_moat",
            "Triple Moat View",
            "Find a spot where all three moats are visible and photograph them.",
            "The northeastern corner gives the best view of all three moat levels.",
            "bidar_moat"
        ));

        return new Fort(
            "bidar",
            "Bidar Fort",
            "ಬಿದರ್ ಕೋಟೆ",
            "Bahmani Sultanate",
            "1427 CE",
            "Bidar Fort was the capital of the Bahmani Sultanate — one of the most powerful " +
            "Muslim kingdoms of medieval India. Famous for Bidriware craft, Persian architecture, " +
            "and the unique triple-moat defense system. The fort spans 5.5 km in circumference " +
            "and contains 30+ monuments inside its walls.",
            "",
            17.9118, 77.5197,
            points,
            challenges
        );
    }

    // ─── BEKAL FORT ─────────────────────────────────────────────────────────────
    private static Fort createBekalFort() {
        List<FortPoint> points = new ArrayList<>();

        points.add(new FortPoint(
            "bekal_observation",
            "Ocean Observation Tower",
            "ಸಾಗರ ವೀಕ್ಷಣ ಗೋಪುರ",
            "The observation tower of Bekal Fort stands 40 feet above sea level and gives " +
            "a panoramic view of the Arabian Sea. Built by Shivappa Nayaka in 1650, " +
            "this fort was the largest and best-preserved fort in Kerala — but actually " +
            "built on Karnataka territory. The tower was specifically designed to spot " +
            "Portuguese warships approaching from the sea, as Portugal controlled the " +
            "spice trade routes and frequently attacked coastal fortifications.",
            "ಬೇಕಲ್ ಕೋಟೆಯ ಗೋಪುರದಿಂದ ಅರಬ್ಬಿ ಸಮುದ್ರದ ಅದ್ಭುತ ನೋಟ ಕಾಣಸಿಗುತ್ತದೆ.",
            "audio_bekal_tower_en",
            "audio_bekal_tower_kn",
            12.3923, 75.0367,
            "watchtower"
        ));

        List<PhotoChallenge> challenges = new ArrayList<>();
        challenges.add(new PhotoChallenge(
            "ch_sunset",
            "Sunset from the Tower",
            "Capture the sunset over the Arabian Sea from the observation tower.",
            "Best time: 6:00 PM. The tower's western windows perfectly frame the setting sun.",
            "bekal_observation"
        ));

        return new Fort(
            "bekal",
            "Bekal Fort",
            "ಬೇಕಲ್ ಕೋಟೆ",
            "Shivappa Nayaka",
            "1650 CE",
            "Bekal Fort is a massive keyhole-shaped fort perched on a headland overlooking " +
            "the Arabian Sea. Karnataka's coastal guardian, it was built to defend against " +
            "Portuguese naval power and is remarkably well-preserved after 370 years.",
            "",
            12.3923, 75.0367,
            points,
            challenges
        );
    }
}
