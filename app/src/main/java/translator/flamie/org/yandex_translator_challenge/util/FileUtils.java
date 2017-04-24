package translator.flamie.org.yandex_translator_challenge.util;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by flamie on 24.04.17 :3
 */

public class FileUtils {

    public static void writeToFile(Activity activity, String originalWord, String translatedWord, String languages, boolean isFavorite, String fileName) throws JSONException, IOException {
        File file = activity.getFileStreamPath(fileName);
        if (!file.exists()) {
            OutputStreamWriter out = new OutputStreamWriter(activity.openFileOutput(fileName, 0));
            out.write("[]");
            out.close();
        } else {
            Log.d("File", "File is exist");
        }

        JSONObject object = new JSONObject();
        object.put("or_word", originalWord);
        object.put("tr_word", translatedWord);
        object.put("lang", languages);
        object.put("is_fav", isFavorite);

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

    public static JSONArray readFile(Activity activity, String fileName) throws IOException, JSONException {
        File file = activity.getFileStreamPath(fileName);
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
