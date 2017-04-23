package translator.flamie.org.yandex_translator_challenge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import translator.flamie.org.yandex_translator_challenge.model.HistoryItem;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryItem> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView originalWord;
        public TextView translatedWord;
        public TextView language;

        public ViewHolder(View v) {
            super(v);

            originalWord = (TextView) v.findViewById(R.id.original_word);
            translatedWord = (TextView) v.findViewById(R.id.translated_word);
            language = (TextView) v.findViewById(R.id.language);
        }
    }

    public HistoryAdapter(List<HistoryItem> myDataset) {
        dataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.originalWord.setText(dataset.get(position).getOriginalWord());
        holder.translatedWord.setText(dataset.get(position).getTranslatedWord());
        holder.language.setText(dataset.get(position).getLanguages().toString());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}