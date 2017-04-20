package translator.flamie.org.yandex_translator_challenge.model;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by flamie on 21.04.17 :3
 */

public class WordTranslation {

    private final String translatedWord;

    private WordTranslation(String textFromResponse) {
        this.translatedWord = textFromResponse;
    }

    @Nullable
    public static WordTranslation deserializeFromJSON(JSONObject response) {
        try {
            String textFromResponse = "";
            for (int i = 0; i < response.length(); i++) {
                textFromResponse = response.getJSONArray("def").getJSONObject(3).getJSONArray("text").getString(i) + "\n";
            }
            return new WordTranslation(textFromResponse);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTranslatedWord() {
        return translatedWord;
    }
}
