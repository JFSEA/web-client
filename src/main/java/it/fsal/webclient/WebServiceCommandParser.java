package it.fsal.webclient;

import it.fsal.webclient.api.util.StringsUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class WebServiceCommandParser {

    private final String service;
    private final Map<String,String> parameters;

    public WebServiceCommandParser(String[] input) {
        service = extractServiceFromArgs(input);
        parameters = extractParametersFromArgs(input);
    }

    public String getService() {
        return service;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    private String extractServiceFromArgs(String... args) {
        return Arrays.stream(args)
                .filter(StringsUtil::isNotBlank)
                .filter(s ->  s.startsWith("-WS"))
                .map(s -> s.substring(3)).findAny().orElse(null);
    }

    private static Map<String,String> extractParametersFromArgs(String... args) {
        return Arrays.stream(args)
                .filter(StringsUtil::isNotBlank)
                .filter(s -> s.startsWith("-IP"))
                .map(WebServiceCommandParser::fromString)
                .collect(Collectors.toMap(InputParameter::getKey,InputParameter::getValue));
    }

    private static InputParameter fromString(String s) {
        s = s.substring(3); // rimuove -IP
        int sep = s.indexOf("=");
        String key = sep != -1 ? s.substring(0,sep) : s;
        String value = sep != -1 ? s.substring(sep) : null;
        return new InputParameter(key,value);
    }

    private static class InputParameter {
        private final String key;
        private final String value;

        public InputParameter(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
