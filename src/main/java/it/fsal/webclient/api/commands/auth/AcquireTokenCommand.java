package it.fsal.webclient.api.commands.auth;

import it.fsal.webclient.api.IRequest;
import it.fsal.webclient.api.RequestFactory;
import it.fsal.webclient.api.commands.AbstractRequestSpec;
import it.fsal.webclient.api.commands.AbstractWebServiceCommand;
import it.fsal.webclient.api.commands.SimpleRequestSpec;
import it.fsal.webclient.api.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.*;

public class AcquireTokenCommand extends AbstractWebServiceCommand {

    @Autowired
    private RequestFactory requestFactory;

    @Override
    public String getName() {
        return "AuthToken";
    }

    @Override
    protected AbstractRequestSpec getRequestSpecFromParameters(Map<String, String> params) {
        return new SimpleRequestSpec(
                () -> HttpMethod.POST,
                () -> "localhost:8080/auth/token",
                () -> getHeaders(),
                () -> getBody(params)
        );
    }

    private Map<String, List<String>> getHeaders() {
        Map<String, List<String>> map = new HashMap<>();
        map.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        return map;
    }

    private String getBody(Map<String, String> params) {
        IRequest request = requestFactory.createRequest(RequestFactory.Service.AUTH_TOKEN, params);
        return JsonUtils.toJsonString(request);
    }
}
