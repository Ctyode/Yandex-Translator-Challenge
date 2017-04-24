package translator.flamie.org.yandex_translator_challenge.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import translator.flamie.org.yandex_translator_challenge.util.FileUtils;

/**
 * Created by flamie on 24.04.17 :3
 */

public class LocalData {

    private List<BookmarkItem> bookmarks;
    private Context context;

    public void save() {
        try {
            FileUtils.writeToFile(context, "local_data.json", toJSONObject());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static LocalData forContext(Context context) {
        try {
            JSONObject localDataJSONObject = FileUtils.readFile(context, "local_data.json");
            LocalData localData = fromJSONObject(localDataJSONObject);
            localData.context = context;
            return localData;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalData fromJSONObject(JSONObject jsonObject) {
        LocalData localData = new LocalData();
        localData.bookmarks = new ArrayList<>();
        JSONArray jsonBookmarsArray = jsonObject.optJSONArray("bookmarks");
        if(jsonBookmarsArray != null) {
            for(int i = 0; i < jsonBookmarsArray.length(); i++) {
                try {
                    localData.bookmarks.add(BookmarkItem.fromJSONObject(jsonBookmarsArray.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return localData;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray bookmarks = new JSONArray();
        for(BookmarkItem bookmarkItem : this.bookmarks) {
            bookmarks.put(bookmarkItem.toJSONObject());
        }
        try {
            jsonObject.put("bookmarks", bookmarks);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public List<BookmarkItem> getBookmarks() {
        return bookmarks;
    }
}
