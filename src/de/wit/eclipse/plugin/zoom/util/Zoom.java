package de.wit.eclipse.plugin.zoom.util;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.FontData;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class Zoom {

    private static final String PREFERENCES_QUALIFIER = "org.eclipse.ui.workbench";
    private static final String[] QUALIFIER_KEYS = { "org.eclipse.jdt.ui.editors.textfont", "org.eclipse.jface.textfont" };

    private static final int STEP = 1;

    public static void in() {
        zoom(STEP);
    }

    public static void out() {
        zoom(-STEP);
    }

    public static void reset() {
        zoom(0);
    }

    private static void zoom(int step) {
        for (String key : QUALIFIER_KEYS) {
            zoom(PREFERENCES_QUALIFIER, key, step);
        }
    }

    private static void zoom(String qualifier, String key, int step) {
        FontData fontData = getInstanceFontData(qualifier, key);

        if (step == 0) {
            fontData.setHeight(getDefaultFontData(qualifier, key).getHeight());
        } else {
            int newHeight = fontData.getHeight() + step;
            if (newHeight >= 1 && newHeight <= 250) {
                fontData.setHeight(newHeight);
            }
        }

        setInstanceFontData(qualifier, key, fontData);
    }

    private static FontData getInstanceFontData(String qualifier, String key) {
        String font = InstanceScope.INSTANCE.getNode(qualifier).get(key, null);
        if (font == null) {
            font = DefaultScope.INSTANCE.getNode(qualifier).get(key, null);
        }

        return PreferenceConverter.basicGetFontData(font)[0];
    }

    private static void setInstanceFontData(String qualifier, String key, FontData fontData) {
        Preferences preferences = InstanceScope.INSTANCE.getNode(qualifier);
        preferences.put(key, fontData.toString());

        try {
            preferences.flush();
        } catch (BackingStoreException e) {
        }
    }

    private static FontData getDefaultFontData(String qualifier, String key) {
        return PreferenceConverter.basicGetFontData(DefaultScope.INSTANCE.getNode(qualifier).get(key, null))[0];
    }

}
