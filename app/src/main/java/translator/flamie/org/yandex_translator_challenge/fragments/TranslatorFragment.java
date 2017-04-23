package translator.flamie.org.yandex_translator_challenge.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import translator.flamie.org.yandex_translator_challenge.R;
import translator.flamie.org.yandex_translator_challenge.ResourceParser;
import translator.flamie.org.yandex_translator_challenge.TranslatorAdapter;
import translator.flamie.org.yandex_translator_challenge.api.TranslatorApi;
import translator.flamie.org.yandex_translator_challenge.model.Language;
import translator.flamie.org.yandex_translator_challenge.model.TextTranslation;
import translator.flamie.org.yandex_translator_challenge.model.TranslationPair;
import translator.flamie.org.yandex_translator_challenge.model.WordTranslation;
import translator.flamie.org.yandex_translator_challenge.util.Callback;

/**
 * Created by flamie on 23.04.17 :3
 */

public class TranslatorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.translator_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Map<String, String> languages = ResourceParser.getHashMapResource(getActivity().getApplicationContext(), R.xml.languages);
        List<Language> vals = new ArrayList<>();
        for(Map.Entry<String, String> entry : languages.entrySet()) {
            vals.add(new Language(entry.getKey(), entry.getValue()));
        }
        Object[] array = vals.toArray(new Object[vals.size()]);

        final Spinner fromSpinner = (Spinner) view.findViewById(R.id.from_spinner);
        ArrayAdapter<Object> fromAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, array);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);

        Spinner toSpinner = (Spinner) view.findViewById(R.id.to_spinner);
        ArrayAdapter<Object> toAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, array);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        final ArrayList<String> translations = new ArrayList<>();

        Button translateButton = (Button) view.findViewById(R.id.translate_button);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = ((TextView) view.findViewById(R.id.editText)).getText().toString();
                String from = ((Language)((Spinner) view.findViewById(R.id.from_spinner)).getSelectedItem()).getCode();
                String to = ((Language)((Spinner) view.findViewById(R.id.to_spinner)).getSelectedItem()).getCode();
                try {
                    if(text.contains(" ")) {
                        TranslatorApi.translateText(new TranslationPair(from, to), text, new Callback<TextTranslation>() {
                            @Override
                            public void callback(final TextTranslation result) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        translations.clear();
                                        translations.add(result.getTranslatedText());
                                        TranslatorAdapter adapter = new TranslatorAdapter(translations);
                                        recyclerView.setAdapter(adapter);
                                    }
                                });
                            }
                        });
                    } else {
                        TranslatorApi.translateWord(new TranslationPair(from, to), text, new Callback<WordTranslation>() {
                            @Override
                            public void callback(final WordTranslation result) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        translations.clear();
                                        for(int i = 0; i < result.getTranslations().size(); i++) {
                                            translations.add(result.getTranslations().get(i).getText());
                                        }
                                        TranslatorAdapter adapter = new TranslatorAdapter(translations);
                                        recyclerView.setAdapter(adapter);
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

    public static TranslatorFragment newInstance() {
        return new TranslatorFragment();
    }
}
