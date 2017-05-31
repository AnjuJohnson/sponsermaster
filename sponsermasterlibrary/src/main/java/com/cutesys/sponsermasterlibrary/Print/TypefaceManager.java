package com.cutesys.sponsermasterlibrary.Print;

/**
 * Created by user on 3/15/2017.
 */

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * A helper loading {@link Typeface} avoiding the leak of the font when loaded
 * by multiple calls to {@link Typeface#createFromAsset(AssetManager, String)}
 * on pre-ICS versions.
 */
class TypefaceManager {

    /**
     * The cached typefaces.
     */
    private static final HashMap<String, Typeface> sTypefaces = new HashMap<>();

    /**
     * Load a typeface from the specified font data.
     *
     * @param assets The application's asset manager.
     * @param path   The file name of the font data in the assets directory.
     */
    static Typeface load(AssetManager assets, String path) {
        synchronized (sTypefaces) {
            Typeface typeface;
            if (sTypefaces.containsKey(path)) {
                typeface = sTypefaces.get(path);
            } else {
                typeface = Typeface.createFromAsset(assets, path);
                sTypefaces.put(path, typeface);
            }
            return typeface;
        }
    }

    private TypefaceManager() {
    }

}