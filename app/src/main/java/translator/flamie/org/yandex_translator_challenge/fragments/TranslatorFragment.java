package translator.flamie.org.yandex_translator_challenge.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import translator.flamie.org.yandex_translator_challenge.R;
import translator.flamie.org.yandex_translator_challenge.TranslatorAdapter;
import translator.flamie.org.yandex_translator_challenge.api.TranslatorApi;
import translator.flamie.org.yandex_translator_challenge.model.Language;
import translator.flamie.org.yandex_translator_challenge.model.TextTranslation;
import translator.flamie.org.yandex_translator_challenge.model.TranslationPair;
import translator.flamie.org.yandex_translator_challenge.model.WordTranslation;
import translator.flamie.org.yandex_translator_challenge.util.Callback;
import translator.flamie.org.yandex_translator_challenge.util.ResourceParser;

/**
 * Created by flamie on 23.04.17 :3
 */

public class TranslatorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Translator");
        return inflater.inflate(R.layout.translator_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Map<String, String> languages = ResourceParser.getHashMapResource(getActivity().getApplicationContext(), R.xml.languages);
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
                final String from = ((Language)((Spinner) view.findViewById(R.id.from_spinner)).getSelectedItem()).getCode();
                final String to = ((Language)((Spinner) view.findViewById(R.id.to_spinner)).getSelectedItem()).getCode();
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
                                        try {
                                            writeToFile(text, result.getTranslatedText(), from + "-" + to);
                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();
                                        }
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
                                        if (result != null) {
                                            for (int i = 0; i < result.getTranslations().size(); i++) {
                                                translations.add(result.getTranslations().get(i).getText());
                                            }
                                        }
                                        TranslatorAdapter adapter = new TranslatorAdapter(translations);
                                        recyclerView.setAdapter(adapter);
                                        try {
                                            if(result != null)
                                                writeToFile(text, result.getTranslations().get(0).getText(), from + "-" + to);
                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();
                                        }
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

    private void writeToFile(String originalWord, String translatedWord, String languages) throws JSONException, IOException {
        File file = getActivity().getFileStreamPath("history.json");
        if (!file.exists()) {
            OutputStreamWriter out = new OutputStreamWriter(getActivity().openFileOutput("history.json", 0));
            out.write("[]");
            out.close();
        } else {
            Log.d("File", "File is exist");
        }

        JSONObject object = new JSONObject();
        object.put("or_word", originalWord);
        object.put("tr_word", translatedWord);
        object.put("lang", languages);

        String fileContents = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            fileContents = sb.toString();
        } finally {
            br.close();
        }

        JSONArray history = new JSONArray(fileContents);
        history.put(object);

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(history.toString());
        fileWriter.flush();
        fileWriter.close();
    }
}
