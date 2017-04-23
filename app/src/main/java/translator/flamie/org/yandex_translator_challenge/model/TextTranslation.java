package translator.flamie.org.yandex_translator_challenge.model;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by flamie on 21.04.17 :3
 */

public class TextTranslation {

    private final String translatedText;

    private TextTranslation(String translatedText) {
        this.translatedText = translatedText;
    }

    @Nullable
    public static TextTranslation deserializeFromJSON(JSONObject response) {
        try {
            String textFromResponse = response.getJSONArray("text").getString(0);
            return new TextTranslation(textFromResponse);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTranslatedText() {
        return translatedText;
    }
}
