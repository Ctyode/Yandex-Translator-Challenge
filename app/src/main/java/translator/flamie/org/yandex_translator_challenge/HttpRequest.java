package translator.flamie.org.yandex_translator_challenge;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by flamie on 20.04.17 :3
 */


public class HttpRequest extends Thread {

    public interface HttpRequestCallback {

        void success(String data);
        void error(Exception e);

    }

    private URL url;
    private HttpRequestCallback callback;

    public HttpRequest(URL url, HttpRequestCallback callback) {
        this.url = url;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String data;
            StringBuffer dataBuffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                dataBuffer.append(line);
            }
            data = dataBuffer.toString();
            br.close();
            urlConnection.disconnect();
            callback.success(data);
        } catch (IOException e) {
            callback.error(e);
        }
    }
}