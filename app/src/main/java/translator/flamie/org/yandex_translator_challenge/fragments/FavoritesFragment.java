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

public class FavoritesFragment extends Fragment {

    private boolean isFavorite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        JSONArray favorites;
        List<BookmarkItem> favoritesItems = new ArrayList<>();
        try {
            favorites = FileUtils.readFile(getActivity(), "favorites.json");
            for(int i = 0; i < favorites.length(); i++) {
                String translatedWord = favorites.getJSONObject(i).getString("tr_word");
                String originalWord = favorites.getJSONObject(i).getString("or_word");
                String languages = favorites.getJSONObject(i).getString("lang");
                isFavorite = favorites.getJSONObject(i).getBoolean("is_fav");
                favoritesItems.add(new BookmarkItem(originalWord, translatedWord, languages, false));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        HistoryAdapter adapter = new HistoryAdapter(favoritesItems);
        recyclerView.setAdapter(adapter);

        //Button


    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

}
