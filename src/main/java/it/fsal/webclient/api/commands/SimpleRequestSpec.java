package it.fsal.webclient.api.commands;

import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimpleRequestSpec extends AbstractRequestSpec {

    private final SpecProvider<HttpMethod> methodProvider;
    private final SpecProvider<String> urlProvider;
    private final SpecProvider<Map<String,List<String>>> headersProvider;
    private final SpecProvider<String> bodyProvider;

    public SimpleRequestSpec(SpecProvider<HttpMethod> methodProvider, SpecProvider<String> urlProvider, SpecProvider<Map<String, List<String>>> headersProvider, SpecProvider<String> bodyProvider) {
        this.methodProvider = methodProvider;
        this.urlProvider = urlProvider;
        this.headersProvider = headersProvider;
        this.bodyProvider = bodyProvider;
    }

    @Override
    protected HttpMethod getMethod() {
        return methodProvider.getValue();
    }

    @Override
    protected String getUrl() {
        return urlProvider.getValue();
    }

    @Override
    protected Map<String, List<String>> getHeaders() {
        return headersProvider.getValue();
    }

    @Override
    protected Optional<String> getBody() {
        return Optional.ofNullable(bodyProvider.getValue());
    }

    public interface SpecProvider<T> {
        T getValue();
    }

}
