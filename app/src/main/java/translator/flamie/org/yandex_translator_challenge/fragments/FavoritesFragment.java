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

import java.util.ArrayList;
import java.util.List;

import translator.flamie.org.yandex_translator_challenge.R;
import translator.flamie.org.yandex_translator_challenge.adapters.FavoritesAdapter;
import translator.flamie.org.yandex_translator_challenge.model.BookmarkItem;
import translator.flamie.org.yandex_translator_challenge.model.LocalData;

/**
 * Created by flamie on 23.04.17 :3
 */

public class FavoritesFragment extends Fragment {

    private LocalData localData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_favorites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        List<BookmarkItem> favoritesItems = new ArrayList<>();
        for (BookmarkItem bookmark : localData.getBookmarks()) {
            if(bookmark.getIsFavorite()) {
                favoritesItems.add(bookmark);
            }
        }

        FavoritesAdapter adapter = new FavoritesAdapter(favoritesItems, localData);
        recyclerView.setAdapter(adapter);

    }

    public static FavoritesFragment newInstance(LocalData localData) {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        favoritesFragment.localData = localData;
        return favoritesFragment;
    }

}
