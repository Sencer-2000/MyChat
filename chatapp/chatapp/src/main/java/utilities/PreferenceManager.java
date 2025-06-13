package utilities;

import java.util.prefs.Preferences;
import java.util.*;

public class PreferenceManager {

    private final Preferences prefs;

    public PreferenceManager(String nodeName) {
        // nodeName = Constants.KEY_PREFERENCE_NAME yerine geçer
        prefs = Preferences.userRoot().node(nodeName);
    }

    public void putBoolean(String key, boolean value) {
        prefs.putBoolean(key, value);
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        prefs.put(key, value);
    }

    public String getString(String key) {
        return prefs.get(key, null);
    }

    // Set<String> desteği olmadığı için string olarak saklayacağız (JSON gibi)
    public void putElementstoArray(String key, Set<String> array) {
        String joined = String.join(";;", array); // özel bir ayraç
        prefs.put(key, joined);
    }

    public Set<String> getElementsfromArray(String key) {
        String raw = prefs.get(key, "");
        if (raw.isEmpty()) return new HashSet<>();
        return new HashSet<>(Arrays.asList(raw.split(";;")));
    }

    public void clear() {
        try {
            prefs.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}