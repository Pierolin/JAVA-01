package Week_02;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ClientApplication {
    public static String SERVER_URL = "http://localhost:8803";
    public static int SUCCESS_CODE = 200;
    public static int TIMEOUT = 2000;

    public static void main(String[] arg) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(SERVER_URL);
        RequestConfig config = RequestConfig.custom().setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
        httpGet.setConfig(config);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("Status: " + statusCode);
            if (statusCode != SUCCESS_CODE) {
                System.out.println("Request processing failure.");
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            String body = EntityUtils.toString(httpEntity, "utf-8");
            System.out.println(body);
            httpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
    }
}