package translator.flamie.org.yandex_translator_challenge.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by flamie on 23.04.17 :3
 */

public class BookmarkItem {

    private String originalWord;
    private String translatedWord;
    private String languages;
    private boolean isFavorite;

    public BookmarkItem(String originalWord, String translatedWord, String languages, boolean isFavorite) {
        this.originalWord = originalWord;
        this.translatedWord = translatedWord;
        this.languages = languages;
        this.isFavorite = isFavorite;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public String getLanguages() {
        return languages;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public static BookmarkItem fromJSONObject(JSONObject jsonObject) {
        try {
            return new BookmarkItem(jsonObject.getString("or_word"), jsonObject.getString("tr_word"), jsonObject.getString("lang"), jsonObject.getBoolean("is_fav"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject toJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("or_word", originalWord);
            object.put("tr_word", translatedWord);
            object.put("lang", languages);
            object.put("is_fav", isFavorite);
            return object;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
