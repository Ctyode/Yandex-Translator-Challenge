package translator.flamie.org.yandex_translator_challenge.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import translator.flamie.org.yandex_translator_challenge.R;
import translator.flamie.org.yandex_translator_challenge.fragments.HistoryFragment;
import translator.flamie.org.yandex_translator_challenge.model.BookmarkItem;
import translator.flamie.org.yandex_translator_challenge.model.LocalData;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<BookmarkItem> dataset;
    private LocalData localData;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView originalWord;
        public TextView translatedWord;
        public TextView language;
        public Button isFavorite;

        public ViewHolder(View v) {
            super(v);

            originalWord = (TextView) v.findViewById(R.id.original_word);
            translatedWord = (TextView) v.findViewById(R.id.translated_word);
            language = (TextView) v.findViewById(R.id.language);
            isFavorite = (Button) v.findViewById(R.id.favorites_button);
        }
    }

    public HistoryFragment historyFragment;

    public HistoryAdapter(List<BookmarkItem> myDataset, HistoryFragment historyFragment, LocalData localData) {
        this.historyFragment = historyFragment;
        this.localData = localData;
        dataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.originalWord.setText(dataset.get(position).getOriginalWord());
        holder.translatedWord.setText(dataset.get(position).getTranslatedWord());
        holder.language.setText(dataset.get(position).getLanguages());
        holder.isFavorite.setSelected(dataset.get(position).getIsFavorite());

        holder.isFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataset.get(position).setFavorite(true);
                localData.save();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}