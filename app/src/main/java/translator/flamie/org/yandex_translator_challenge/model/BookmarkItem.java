package translator.flamie.org.yandex_translator_challenge.model;

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
}
