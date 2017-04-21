package translator.flamie.org.yandex_translator_challenge.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flamie on 22.04.17 :3
 */

public class Word {

    public enum PartOfSpeech {

        ;

        public static PartOfSpeech deserializePartOfSpeech(String partOfSpeech) {
            return PartOfSpeech.valueOf(partOfSpeech.toUpperCase());
        }
    }

    private final String text;
    private final PartOfSpeech partOfSpeech;
    private final List<Word> translations;

    public Word(String text, PartOfSpeech partOfSpeech, List<Word> translations) {
        this.text = text;
        this.partOfSpeech = partOfSpeech;
        this.translations = translations;
    }

    public String getText() {
        return text;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Word> getTranslations() {
        return translations;
    }

    public static Word deserealizeWord(JSONObject wordJSON) throws JSONException {
        String text = wordJSON.getString("text");
        PartOfSpeech partOfSpeech = PartOfSpeech.deserializePartOfSpeech(wordJSON.getString("pos"));
        List<Word> translations = new ArrayList<>();
        return new Word(text, partOfSpeech, translations);
    }
}
