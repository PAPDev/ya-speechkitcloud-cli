package ru.papdevelop.vr.speechcloud;

import org.apache.http.HttpResponse;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HttpExecutor {

    private static final String HOST = "asr.yandex.net";
    private static final String PATH = "asr_xml";


    public String execute(Request request) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        Objects.requireNonNull(request);

        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            httpclient.start();
            Future<HttpResponse> future = httpclient.execute(
                HttpPostBuilder.instnceOf()
                    .setHost(HOST)
                    .setPath(PATH)
                    .setRequest(request)
                    .build(),
                null);
            HttpResponse response = future.get();
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }
}
