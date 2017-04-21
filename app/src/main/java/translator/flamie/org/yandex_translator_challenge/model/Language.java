package translator.flamie.org.yandex_translator_challenge.model;

/**
 * Created by flamie on 21.04.17 :3
 */

public class Language {

    private final String code;
    private final String name;

    public Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name;
    }
}
