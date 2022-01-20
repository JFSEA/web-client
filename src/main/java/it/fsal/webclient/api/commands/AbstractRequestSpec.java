package it.fsal.webclient.api.commands;

import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractRequestSpec {

    protected abstract HttpMethod getMethod();

    protected abstract String getUrl();

    protected abstract Map<String, List<String>> getHeaders();

    protected abstract Optional<String> getBody();

}
