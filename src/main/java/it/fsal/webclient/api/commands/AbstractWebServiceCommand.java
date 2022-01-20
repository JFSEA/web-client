package it.fsal.webclient.api.commands;

import it.fsal.webclient.api.InheritableComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.Map;
import java.util.Optional;

@InheritableComponent
public abstract class AbstractWebServiceCommand {

    protected final Logger log = LogManager.getLogger(getClass());

    public abstract String getName();

    public void execute(Map<String, String> params) {

        AbstractRequestSpec requestSpec = getRequestSpecFromParameters(params);

        log.info("Inside " + getName() + " execute");

        WebClient webClient = WebClient.create(requestSpec.getUrl());
        WebClient.RequestBodyUriSpec method = webClient.method(requestSpec.getMethod());
        WebClient.RequestBodySpec headers = method.headers(h -> requestSpec.getHeaders().forEach(h::addAll));

        Optional<String> body = requestSpec.getBody();

        WebClient.RequestHeadersSpec<?> spec = body.isPresent() ? headers.bodyValue(body.get()) : headers;


        handleResponse(spec
                .retrieve()
                .bodyToMono(String.class)
                .block()
        );
    }

    protected void handleResponse(String responseBody) {
        log.info("Inside " + getName() + " response handling");
    }

    protected abstract AbstractRequestSpec getRequestSpecFromParameters(Map<String, String> params);

}
