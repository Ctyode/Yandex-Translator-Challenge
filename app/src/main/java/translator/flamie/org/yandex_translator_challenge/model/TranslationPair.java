package translator.flamie.org.yandex_translator_challenge.model;

/**
 * Created by flamie on 21.04.17 :3
 */

public class TranslationPair {

    private final String from;
    private final String to;

    public TranslationPair(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return from + "-" + to;
    }
}
