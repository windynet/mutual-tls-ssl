package nl.altindag.client.service;

import static nl.altindag.client.Constants.GOOGLE_HTTP_CLIENT;
import static nl.altindag.client.Constants.HEADER_KEY_CLIENT_TYPE;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;

import nl.altindag.client.model.ClientResponse;

@Service
public class GoogleHttpClientWrapper extends RequestService {

    private final HttpTransport httpTransport;

    @Autowired
    public GoogleHttpClientWrapper(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    @Override
    public ClientResponse executeRequest(String url) throws Exception {
        HttpResponse response = httpTransport.createRequestFactory()
                                             .buildGetRequest(new GenericUrl(url))
                                             .setHeaders(new HttpHeaders().set(HEADER_KEY_CLIENT_TYPE, GOOGLE_HTTP_CLIENT))
                                             .execute();

        return new ClientResponse(IOUtils.toString(response.getContent(), StandardCharsets.UTF_8), response.getStatusCode());
    }

}
