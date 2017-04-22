package translator.flamie.org.yandex_translator_challenge;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import translator.flamie.org.yandex_translator_challenge.api.TranslatorApi;
import translator.flamie.org.yandex_translator_challenge.model.Language;
import translator.flamie.org.yandex_translator_challenge.model.TextTranslation;
import translator.flamie.org.yandex_translator_challenge.model.TranslationPair;
import translator.flamie.org.yandex_translator_challenge.model.WordTranslation;
import translator.flamie.org.yandex_translator_challenge.util.Callback;
import translator.flamie.org.yandex_translator_challenge.util.Dimen;

import static android.support.v7.recyclerview.R.attr.layoutManager;

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

        Map<String, String> languages = ResourceParser.getHashMapResource(context, R.xml.languages);
        List<Language> vals = new ArrayList<>();
        for(Map.Entry<String, String> entry : languages.entrySet()) {
            vals.add(new Language(entry.getKey(), entry.getValue()));
        }
        Object[] array = vals.toArray(new Object[vals.size()]);

        final Spinner fromSpinner = (Spinner) findViewById(R.id.from_spinner);
        ArrayAdapter<Object> fromAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, array);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);

        Spinner toSpinner = (Spinner) findViewById(R.id.to_spinner);
        ArrayAdapter<Object> toAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, array);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);

        Button translateButton = (Button) findViewById(R.id.translate_button);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = ((TextView) findViewById(R.id.editText)).getText().toString();
                String from = ((Language)((Spinner) findViewById(R.id.from_spinner)).getSelectedItem()).getCode();
                String to = ((Language)((Spinner) findViewById(R.id.to_spinner)).getSelectedItem()).getCode();
                try {
                    if(text.contains(" ")) {
                        TranslatorApi.translateText(new TranslationPair(from, to), text, new Callback<TextTranslation>() {
                            @Override
                            public void callback(final TextTranslation result) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        TextView translatedText = (TextView) findViewById(R.id.translation);
//                                        translatedText.setText(result.getTranslatedText());
                                    }
                                });
                            }
                        });
                    } else {
                        TranslatorApi.translateWord(new TranslationPair(from, to), text, new Callback<WordTranslation>() {
                            @Override
                            public void callback(final WordTranslation result) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        TextView translatedText = (TextView) findViewById(R.id.translation);
                                        // TODO
                                        //translatedText.setText(result.getTranslations());
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


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        String[] strings = new String[3];
        strings[0] = "1";
        strings[1] = "2";
        strings[2] = "3";
        TranslatorAdapter adapter = new TranslatorAdapter(strings);
        recyclerView.setAdapter(adapter);
    }



}
