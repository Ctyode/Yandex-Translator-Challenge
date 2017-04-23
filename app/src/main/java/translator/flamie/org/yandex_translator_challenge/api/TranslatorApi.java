package translator.flamie.org.yandex_translator_challenge.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import translator.flamie.org.yandex_translator_challenge.HttpRequest;
import translator.flamie.org.yandex_translator_challenge.model.TextTranslation;
import translator.flamie.org.yandex_translator_challenge.model.TranslationPair;
import translator.flamie.org.yandex_translator_challenge.model.WordTranslation;
import translator.flamie.org.yandex_translator_challenge.util.Callback;

/**
 * Created by flamie on 21.04.17 :3
 */

public class TranslatorApi {

    public static void translateText(TranslationPair translationPair, final String text, final Callback<TextTranslation> callback) throws UnsupportedEncodingException, MalformedURLException {
        String httpRequestString = String.format(
                "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170420T182446Z.e46a60b86ece957e.eba1c5dc07ffac5444d933ea30bc7e238b1fe70d&text=%s&lang=%s",
                URLEncoder.encode(text, "UTF-8"),
                URLEncoder.encode(translationPair.toString(), "UTF-8"));
        HttpRequest httpRequest;
            httpRequest = new HttpRequest(new URL(httpRequestString), new HttpRequest.HttpRequestCallback() {
                @Override
                public void success(final String data) {
                    try {
                        callback.callback(TextTranslation.deserializeFromJSON(new JSONObject(data)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void error(Exception e) {

                }
            });
        httpRequest.start();
    }

    public static void translateWord(TranslationPair translationPair, final String word, final Callback<WordTranslation> callback) throws UnsupportedEncodingException, MalformedURLException {
        String httpRequestString = String.format(
                "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20170420T195123Z.b6deed58844d2227.a6646b21cc804dc6f305e0a2469c47a59f64064f&text=%s&lang=%s",
                URLEncoder.encode(word, "UTF-8"),
                URLEncoder.encode(translationPair.toString(), "UTF-8"));
        HttpRequest httpRequest;
        httpRequest = new HttpRequest(new URL(httpRequestString), new HttpRequest.HttpRequestCallback() {
            @Override
            public void success(final String data) {
                try {
                    callback.callback(WordTranslation.deserializeFrom(new JSONObject(data)));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void error(Exception e) {

            }
        });
        httpRequest.start();
    }
 }
