package translator.flamie.org.yandex_translator_challenge;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import translator.flamie.org.yandex_translator_challenge.api.TranslatorApi;
import translator.flamie.org.yandex_translator_challenge.model.TextTranslation;
import translator.flamie.org.yandex_translator_challenge.model.TranslationPair;
import translator.flamie.org.yandex_translator_challenge.model.WordTranslation;
import translator.flamie.org.yandex_translator_challenge.util.Callback;
import translator.flamie.org.yandex_translator_challenge.util.Dimen;

/**
 * Created by flamie on 20.03.17 :3
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dimen.init(getApplicationContext());
        Context context = getApplicationContext();
        context.setTheme(R.style.AppTheme);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = ((TextView) findViewById(R.id.editText)).getText().toString();
                try {
                    if(text.contains(" ")) {
                            TranslatorApi.translateText(new TranslationPair("en", "ru"), text, new Callback<TextTranslation>() {
                                @Override
                                public void callback(final TextTranslation result) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView translatedText = (TextView) findViewById(R.id.translation);
                                            translatedText.setText(result.getTranslatedText());
                                        }
                                    });
                                }
                            });
                    } else {
                            TranslatorApi.translateWord(new TranslationPair("ru", "en"), text, new Callback<WordTranslation>() {
                                @Override
                                public void callback(final WordTranslation result) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView translatedText = (TextView) findViewById(R.id.translation);
                                            translatedText.setText(result.getTranslatedWord());
                                        }
                                    });
                                }
                            });
                    }

                } catch (UnsupportedEncodingException | MalformedURLException e) {
                e.printStackTrace();
                }
            }
        });
    }
}
