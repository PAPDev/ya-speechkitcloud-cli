package ru.papdevelop.vr.speechcloud;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpPostBuilder {

    private static final Map<String, String> contentTypeMapping;

    static {
        contentTypeMapping = new HashMap<>();
        contentTypeMapping.put("wav", "audio/x-wav");
        contentTypeMapping.put("mp3", "audio/x-mpeg-3");
        contentTypeMapping.put("spx", "audio/x-speex");
        contentTypeMapping.put("opus", "audio/ogg;codecs=opus");
        contentTypeMapping.put("webm", "audio/webm;codecs=opus");
    }

    private String host;
    private String path;
    private Request request;

    private HttpPostBuilder() {
    }

    public static final HttpPostBuilder instnceOf() {
        return new HttpPostBuilder();
    }

    private static HttpEntity getPayload(Request request) throws IOException {
        return new ByteArrayEntity(
            Files.readAllBytes(request.getFile().toPath()));
    }

    private static Header[] createHeaders(Request request) throws IOException {
        Header[] headers = {
            new BasicHeader("Content-Type", getContentType(request.getFile()))};
        return headers;

    }

    private static String getContentType(File file) {
        String ext = FilenameUtils.getExtension(file.getPath());
        return contentTypeMapping.get(ext);
    }

    public HttpPostBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public HttpPostBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public HttpPostBuilder setRequest(Request request) {
        this.request = request;
        return this;
    }

    public HttpPost build() throws URISyntaxException, IOException {
        HttpPost post = new HttpPost(createURI());
        post.setHeaders(createHeaders(request));
        post.setEntity(getPayload(request));
        return post;
    }

    private URI createURI() throws URISyntaxException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

        if (request != null) {
            nameValuePairs.add(new BasicNameValuePair("uuid", request.getUuid()));
            nameValuePairs.add(new BasicNameValuePair("key", request.getKey()));
            nameValuePairs.add(new BasicNameValuePair("topic", request.getTopic().toString()));
            nameValuePairs.add(new BasicNameValuePair("lang", request.getLang().toString()));
            nameValuePairs.add(new BasicNameValuePair("disableAntimat", request.getDisableAntimat().toString()));
        }

        return new URIBuilder()
            .setScheme("https")
            .setHost(host)
            .setPath(path)
            .setParameters(nameValuePairs)
            .build();
    }
}
