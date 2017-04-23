package translator.flamie.org.yandex_translator_challenge.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flamie on 22.04.17 :3
 */

public class Word {

    private final String text;
    private final String partOfSpeech;
    private final List<Word> synonyms;
    private final List<String> meanings;

    public Word(String text, String partOfSpeech, List<Word> synonyms, List<String> meanings) {
        this.text = text;
        this.partOfSpeech = partOfSpeech;
        this.synonyms = synonyms;
        this.meanings = meanings;
    }

    public String getText() {
        return text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Word> getSynonyms() {
        return synonyms;
    }

    public List<String> getMeanings() {
        return meanings;
    }

    public static Word deserializeWord(JSONObject wordJSON) {
        try {
            String text = wordJSON.getString("text");
            String partOfSpeech = wordJSON.getString("pos");
            List<Word> synonyms = new ArrayList<>();
            JSONArray possibleSynonyms = wordJSON.optJSONArray("syn");
            if (possibleSynonyms != null) {
                for (int i = 0; i < possibleSynonyms.length(); i++) {
                    synonyms.add(Word.deserializeWord(possibleSynonyms.getJSONObject(i)));
                }
            }

            List<String> meanings = new ArrayList<>();
            JSONArray possibleMeanings = wordJSON.optJSONArray("mean");
            if (possibleMeanings != null) {
                for (int i = 0; i < possibleMeanings.length(); i++) {
                    meanings.add(possibleMeanings.getString(i));
                }
            }

            return new Word(text, partOfSpeech, synonyms, meanings);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
