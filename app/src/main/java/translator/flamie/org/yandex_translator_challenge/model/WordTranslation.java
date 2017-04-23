package translator.flamie.org.yandex_translator_challenge.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flamie on 21.04.17 :3
 */

public class WordTranslation {

    private final Word originalWord;
    private final List<Word> translations;

    public WordTranslation(Word originalWord, List<Word> translations) {
        this.originalWord = originalWord;
        this.translations = translations;
    }

    public Word getOriginalWord() {
        return originalWord;
    }

    public List<Word> getTranslations() {
        return translations;
    }

    public static WordTranslation deserializeFrom(JSONObject response) {
        try {
            JSONObject def = response.getJSONArray("def").optJSONObject(0);
            if (def != null) {
                return deserializeSingleWordTranslation(def);
            } else {
                return null;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static WordTranslation deserializeSingleWordTranslation(JSONObject response) {
        try {
            Word originalWord = Word.deserializeWord(response);
            List<Word> translations = new ArrayList<>();
            JSONArray trs = response.getJSONArray("tr");
            for (int i = 0; i < trs.length(); i++) {
                JSONObject tr = trs.getJSONObject(i);
                translations.add(Word.deserializeWord(tr));
            }
            return new WordTranslation(originalWord, translations);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
