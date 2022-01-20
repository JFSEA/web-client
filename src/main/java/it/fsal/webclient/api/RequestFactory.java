package it.fsal.webclient.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.fsal.webclient.api.commands.auth.AcquireTokenRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RequestFactory {

    public enum Service {
        AUTH_TOKEN
    }

    private static final ObjectMapper DESERIALIZER = new ObjectMapper();

    public IRequest createRequest(Service service, Map<String,String> parameters) {
        switch (service) {
            case AUTH_TOKEN: return DESERIALIZER.convertValue(parameters, AcquireTokenRequest.class);
            default: throw new NoSuchService(service);
        }
    }

    public static class NoSuchService extends RuntimeException {
        private final Service service;

        private NoSuchService(Service service) {
            this.service = service;
        }

        public Service getService() {
            return service;
        }
    }
}
