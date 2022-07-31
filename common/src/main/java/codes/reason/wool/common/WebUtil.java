package codes.reason.wool.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebUtil {

    public static String getContent(String targetUrl) throws IOException {
        StringBuilder builder = new StringBuilder();
        URL url = new URL(targetUrl);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line).append('\n');
        }
        return builder.toString();
    }

}
