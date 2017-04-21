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

    public static List<WordTranslation> deserializeFromJSON(JSONObject response) throws JSONException {
        List<WordTranslation> wordTranslations = new ArrayList<>();
        JSONArray defs = response.getJSONArray("def");
        for(int i = 0; i < defs.length(); i++) {
            JSONObject def = defs.getJSONObject(i);
            wordTranslations.add(deserializeSingleWordTranslation(def));
        }
        return wordTranslations;
    }

    public static WordTranslation deserializeSingleWordTranslation(JSONObject response) throws JSONException {
        Word originalWord = new Word(response.getString("text"), Word.PartOfSpeech.deserializePartOfSpeech(response.getString("pos")), new ArrayList<Word>());
        List<Word> translations = new ArrayList<>();
        JSONArray trs = response.getJSONArray("tr");
        for(int i = 0; i < trs.length(); i++) {
            JSONObject tr = trs.getJSONObject(i);
            translations.add(Word.deserealizeWord(tr));
        }
        return new WordTranslation(originalWord, translations);
    }

}
