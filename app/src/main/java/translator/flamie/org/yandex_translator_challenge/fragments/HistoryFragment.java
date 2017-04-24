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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import translator.flamie.org.yandex_translator_challenge.R;
import translator.flamie.org.yandex_translator_challenge.adapters.HistoryAdapter;
import translator.flamie.org.yandex_translator_challenge.model.BookmarkItem;
import translator.flamie.org.yandex_translator_challenge.util.FileUtils;

/**
 * Created by flamie on 23.04.17 :3
 */

public class HistoryFragment extends Fragment {

    private String translatedWord;
    private String originalWord;
    private String languages;
    private boolean isFavorite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        JSONArray history;
        List<BookmarkItem> historyItems = new ArrayList<>();
        try {
            history = FileUtils.readFile(getActivity(), "history.json");
            for(int i = 0; i < history.length(); i++) {
                translatedWord = history.getJSONObject(i).getString("tr_word");
                originalWord = history.getJSONObject(i).getString("or_word");
                languages = history.getJSONObject(i).getString("lang");
                isFavorite = history.getJSONObject(i).getBoolean("is_fav");
                historyItems.add(new BookmarkItem(originalWord, translatedWord, languages, isFavorite));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        HistoryAdapter adapter = new HistoryAdapter(historyItems);
        recyclerView.setAdapter(adapter);

//        Button favoriteButton = (Button) view.findViewById(R.id.favorites_button);
//        favoriteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    System.out.println("hi");
//                    FileUtils.writeToFile(getActivity(), originalWord, translatedWord, languages, isFavorite, "history.json");
//                } catch (JSONException | IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

}
