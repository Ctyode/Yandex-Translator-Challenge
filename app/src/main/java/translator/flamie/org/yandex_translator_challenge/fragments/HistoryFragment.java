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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import translator.flamie.org.yandex_translator_challenge.HistoryAdapter;
import translator.flamie.org.yandex_translator_challenge.R;
import translator.flamie.org.yandex_translator_challenge.model.HistoryItem;

/**
 * Created by flamie on 23.04.17 :3
 */

public class HistoryFragment extends Fragment {

    private String translatedWord;
    private String originalWord;
    private String languages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Translator");
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        JSONArray history;
        List<HistoryItem> historyItems = new ArrayList<>();
        try {
            history = readFile();
            for(int i = 0; i < history.length(); i++) {
                translatedWord = history.getJSONObject(i).getString("tr_word");
                originalWord = history.getJSONObject(i).getString("or_word");
                languages = history.getJSONObject(i).getString("lang");
                historyItems.add(new HistoryItem(originalWord, translatedWord, languages, false));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        HistoryAdapter adapter = new HistoryAdapter(historyItems);
        recyclerView.setAdapter(adapter);
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    private JSONArray readFile() throws IOException, JSONException {
        File file = getActivity().getFileStreamPath("history.json");
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
            System.out.println(fileContents);
        } finally {
            br.close();
        }

        return new JSONArray(fileContents);
    }
}
