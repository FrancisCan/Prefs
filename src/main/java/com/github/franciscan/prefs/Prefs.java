package com.github.franciscan.prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Iterator;
import java.util.prefs.Preferences;

/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 Francesco Cannizzaro
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@SuppressWarnings("unchecked unused")
public class Prefs {

    public final static String INTEGERS = "integers";
    public final static String STRINGS = "strings";
    public final static String BOOLEANS = "booleans";
    public final static String FLOATS = "floats";
    public final static String DOUBLES = "doubles";
    public final static String LONGS = "longs";
    public final static String OBJECTS = "objects";

    private static String resource;

    private static JSONObject json;
    private static JSONObject integers;
    private static JSONObject strings;
    private static JSONObject booleans;
    private static JSONObject floats;
    private static JSONObject doubles;
    private static JSONObject longs;
    private static JSONObject objects;

    /**
     * Init library
     */
    public Prefs() {
        resource = "Prefs.data";
        json = new JSONObject();
    }

    /**
     * Create a custom data file
     */
    public Prefs(String res) {
        this();
        resource = res + ".data";
    }

    /**
     * Read content from data file
     */
    public void init() {
        try {

            File directory = new File("res");

            // if directory not exist create it
            if (!directory.exists() && !directory.mkdir())
                return;

            File file = new File("res/" + resource);

            // if file not exist create it
            if (!file.exists() && !file.createNewFile())
                System.out.println("Cannot create memory file");

            loadJSON(file);
            checkJSON();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load data file as JSON
     */
    private void loadJSON(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = br.readLine()) != null) content.append(line);
        try {
            json = new JSONObject(content.toString());
        } catch (JSONException e) {
            json = new JSONObject();
        }
    }

    /**
     * Check json keys
     */
    private void checkJSON() {
        integers = ifNotExist(INTEGERS);
        strings = ifNotExist(STRINGS);
        booleans = ifNotExist(BOOLEANS);
        floats = ifNotExist(FLOATS);
        doubles = ifNotExist(DOUBLES);
        longs = ifNotExist(LONGS);
        objects = ifNotExist(OBJECTS);
    }

    /**
     * Create JSON Object if not exist
     */
    private static JSONObject ifNotExist(String key) {
        if (!json.has(key)) json.put(key, new JSONObject());
        return json.getJSONObject(key);
    }

    /**
     * Write each change on the file
     */
    private static void updateMemory() {
        try {
            PrintWriter output = new PrintWriter(new FileWriter("res/" + resource));
            output.print(json.toString());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* PUT METHODS */

    /**
     * Save Integer Value
     *
     * @param key   of value
     * @param value to store
     */
    public static <T extends Integer> void putInt(String key, T value) {
        integers.put(key, value);
        updateMemory();
    }

    /**
     * Save String Value
     *
     * @param key   of value
     * @param value to store
     */
    public static <T extends String> void putString(String key, T value) {
        strings.put(key, value);
        updateMemory();
    }

    /**
     * Save Boolean Value
     *
     * @param key   of value
     * @param value to store
     */
    public static <T extends Boolean> void putBoolean(String key, T value) {
        booleans.put(key, value);
        updateMemory();
    }

    /**
     * Save Float Value
     *
     * @param key   of value
     * @param value to store
     */
    public static <T extends Float> void putFloat(String key, T value) {
        floats.put(key, value);
        updateMemory();
    }

    /**
     * Save Double Value
     *
     * @param key   of value
     * @param value to store
     */
    public static <T extends Double> void putDouble(String key, T value) {
        doubles.put(key, value);
        updateMemory();
    }

    /**
     * Save Long Value
     *
     * @param key   of value
     * @param value to store
     */
    public static <T extends Long> void putLong(String key, T value) {
        longs.put(key, value);
        updateMemory();
    }

    /**
     * Save Object (Serializable) Value
     *
     * @param key   of value
     * @param value to store
     */
    public static <T extends Serializable> void putObject(String key, T value) {
        String outputName = "res/" + key.toLowerCase() + ".data";
        try {
            FileOutputStream os = new FileOutputStream(outputName);
            ObjectOutputStream object = new ObjectOutputStream(os);
            object.writeObject(value);
            objects.put(key, outputName);
            updateMemory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove all values with same key
     *
     * @param key of values to remove
     */
    public static void remove(String key) {
        integers.remove(key);
        strings.remove(key);
        floats.remove(key);
        doubles.remove(key);
        booleans.remove(key);
        objects.remove(key);
        updateMemory();
    }

    /**
     * Remove value with that key and class (to prevent multiple values deletions)
     *
     * @param key of value
     * @param c   class of value
     */
    public static void remove(String key, Class c) {

        if (c.equals(Integer.class))
            integers.remove(key);

        else if (c.equals(Float.class))
            floats.remove(key);

        else if (c.equals(Boolean.class))
            booleans.remove(key);

        else if (c.equals(String.class))
            strings.remove(key);

        else if (c.equals(Double.class))
            doubles.remove(key);

        else
            objects.remove(key);

        updateMemory();
    }


    /**
     * Get Integer Value
     *
     * @param key          of value
     * @param defaultValue if absent
     */
    public static Integer getInt(String key, Integer defaultValue) {
        return defaultIfNull(integers, key, defaultValue);
    }

    /**
     * Get String Value
     *
     * @param key          of value
     * @param defaultValue if absent
     */
    public static String getString(String key, String defaultValue) {
        return defaultIfNull(strings, key, defaultValue);
    }

    /**
     * Get Boolean Value
     *
     * @param key          of value
     * @param defaultValue if absent
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return defaultIfNull(booleans, key, defaultValue);
    }

    /**
     * Get Float Value
     *
     * @param key          of value
     * @param defaultValue if absent
     */
    public static Float getFloat(String key, Float defaultValue) {
        return defaultIfNull(floats, key, defaultValue);
    }

    /**
     * Get Double Value
     *
     * @param key          of value
     * @param defaultValue if absent
     */
    public static Double getDouble(String key, Double defaultValue) {
        return defaultIfNull(doubles, key, defaultValue);
    }

    /**
     * Get Long Value
     *
     * @param key          of value
     * @param defaultValue if absent
     */
    public static Long getLong(String key, Long defaultValue) {
        return defaultIfNull(longs, key, defaultValue);
    }

    /**
     * Get Object Value
     *
     * @param key          of value
     * @param defaultValue if absent
     */
    public static <T> T getObject(String key, T defaultValue) {
        T result = null;
        try {
            FileInputStream is = new FileInputStream(objects.getString(key));
            ObjectInputStream object = new ObjectInputStream(is);
            result = (T) object.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result != null ? result : defaultValue;
    }

    /**
     * Return default value if value is null
     */
    private static <T> T defaultIfNull(JSONObject objet, String key, T def) {
        return objet.has(key) ? (T) objet.get(key) : def;
    }

    /**
     * Iterate a kind of values (String, Boolean , Integer ..)
     */
    public static <T> Iterator<Item<T>> iterator(Class c) {

        JSONObject obj = objects;

        if (c.equals(Integer.class))
            obj = integers;
        else if (c.equals(Float.class))
            obj = floats;
        else if (c.equals(Boolean.class))
            obj = booleans;
        else if (c.equals(String.class))
            obj = strings;
        else if (c.equals(Double.class))
            obj = doubles;

        final Iterator<String> keys = obj.keys();
        final JSONObject finalObj = obj;

        return new Iterator<Item<T>>() {

            @Override
            public boolean hasNext() {
                return keys.hasNext();
            }

            @Override
            public Item<T> next() {
                String key = keys.next();
                return new Item(key, finalObj.get(key));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}

