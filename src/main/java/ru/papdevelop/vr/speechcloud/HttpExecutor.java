package ru.papdevelop.vr.speechcloud;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HttpExecutor {

    private static final String HOST = "asr.yandex.net";
    private static final String PATH = "asr_xml";


    public String execute(Request request) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        Objects.requireNonNull(request);

        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            HttpPost post = new HttpPost(buildUri(request));
            post.setHeaders(createHeaders(request));
            post.setEntity(new ByteArrayEntity(request.getPayload()));

            httpclient.start();
            Future<HttpResponse> future = httpclient.execute(post, null);
            HttpResponse response = future.get();
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }

    private Header[] createHeaders(Request request) {
        Header[] headers = {
            new BasicHeader("Content-Type", "audio/x-wav")};
            //new BasicHeader("Content-Length", String.valueOf(request.getPayload().length))};
        return headers;
    }

    private URI buildUri(Request request) throws URISyntaxException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("uuid", request.getUuid()));
        nameValuePairs.add(new BasicNameValuePair("key", request.getKey()));
        nameValuePairs.add(new BasicNameValuePair("topic", request.getTopic().toString()));
        nameValuePairs.add(new BasicNameValuePair("lang", request.getLang().toString()));
        nameValuePairs.add(new BasicNameValuePair("disableAntimat", request.getDisableAntimat().toString()));

        return new URIBuilder()
            .setScheme("https")
            .setHost(HOST)
            .setPath(PATH)
            .setParameters(nameValuePairs)
            .build();
    }
}
