package translator.flamie.org.yandex_translator_challenge.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by flamie on 24.04.17 :3
 */

public class FileUtils {

    public static void writeToFile(Context context, String fileName, JSONObject object) throws JSONException, IOException {
        File file = context.getFileStreamPath(fileName);

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(object.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static JSONObject readFile(Context context, String fileName) throws IOException, JSONException {
        File file = context.getFileStreamPath(fileName);
        if (!file.exists()) {
            return new JSONObject();
        } else {
            Log.d("File", "File is exist");
        }

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

        return new JSONObject(fileContents);
    }

}
