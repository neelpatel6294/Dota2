package com.example.android.dota2.Utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.dota2.Data.Heroes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by PATEL on 2/2/2018.
 */

public class NetworkUtils {

    final static String DOTA_2_URL =
            "https://api.opendota.com/api/heroStats";


    //fetching Json response

    public static List<Heroes> fetchDotaHeroesData(URL url) {

        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Heroes> heroes = extractFeaturesFromJson(jsonResponse);
        return heroes;
    }


    //Building URL
    public static URL builtURL() {
        Uri builtUri = Uri.parse(DOTA_2_URL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    //Getting Response from Network
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    //Json Parsing

    private static List<Heroes> extractFeaturesFromJson(String dotaJson) {

        if (TextUtils.isEmpty((dotaJson))) {
            return null;
        }

        //Creating empty array list to add the heroes
        List<Heroes> heroes = new ArrayList<>();

        try {

            JSONArray ar = new JSONArray(dotaJson);

            for (int i = 0; i < ar.length(); i++) {


                JSONObject ob = ar.getJSONObject(i);
                String heroImage = ob.getString("icon");
                String heroid = ob.getString("id");

                String heroName = ob.getString("localized_name");
                String heroAttri = ob.getString("primary_attr");
                String heroAttack_type = ob.getString("attack_type");

                Heroes heroes1 = new Heroes( heroImage,heroid, heroName, heroAttri, heroAttack_type);
                Log.i("name",  heroImage + " | "+ heroName + " | " + heroAttri + " | " + heroAttack_type);

                heroes.add(heroes1);

            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return heroes;
    }

}
