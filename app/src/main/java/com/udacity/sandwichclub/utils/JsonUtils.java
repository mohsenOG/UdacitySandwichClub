package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {
    public static final String JSON_KEY_NAME = "name";
    public static final String JSON_KEY_MAIN_NAME = "mainName";
    public static final String JSON_KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String JSON_KEY_ORIGIN = "placeOfOrigin";
    public static final String JSON_KEY_DESCRIPTION = "description";
    public static final String JSON_KEY_IMAGE = "image";
    public static final String JSON_KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = null;
            String mainName = null;
            if (sandwichJson.has(JSON_KEY_NAME)) {
                name = sandwichJson.getJSONObject(JSON_KEY_NAME);
                //main name
                if (name.has(JSON_KEY_MAIN_NAME)) {
                    mainName = name.getString(JSON_KEY_MAIN_NAME);
                }
            }
            // Also known as
            List<String> alsoKnownAs = null;
            if (name != null && name.has(JSON_KEY_ALSO_KNOWN_AS)) {
                JSONArray alsoKnownAsJsonArray = name.getJSONArray(JSON_KEY_ALSO_KNOWN_AS);
                alsoKnownAs = new ArrayList<>();
                for (int i = 0; i < alsoKnownAsJsonArray.length(); ++i)
                    alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }
            //place of origin / description / image
            String description = null;
            String image = null;
            String placeOfOrigin = null;
            if (sandwichJson.has(JSON_KEY_ORIGIN)) {
                placeOfOrigin = sandwichJson.getString(JSON_KEY_ORIGIN);
            }
            if (sandwichJson.has(JSON_KEY_DESCRIPTION)) {
                description = sandwichJson.getString(JSON_KEY_DESCRIPTION);
            }
            if (sandwichJson.has(JSON_KEY_IMAGE)) {
                image = sandwichJson.getString(JSON_KEY_IMAGE);
            }
            // ingredients
            List<String> ingredients = null;
            if (sandwichJson.has(JSON_KEY_INGREDIENTS)) {
                JSONArray ingredientsJsonArray = sandwichJson.getJSONArray(JSON_KEY_INGREDIENTS);
                ingredients = new ArrayList<>();
                for (int i = 0; i < ingredientsJsonArray.length(); ++i)
                    ingredients.add(ingredientsJsonArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            return null;
        }

        return sandwich;
    }
}
