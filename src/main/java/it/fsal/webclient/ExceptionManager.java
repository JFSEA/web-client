package it.fsal.webclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ExceptionManager {

    private static final Logger log = LogManager.getLogger(ExceptionManager.class);

    public void manageException(Exception e) {
        log.error(String.format("Exception occurred. Message: %s", e.getMessage()));
    }
}
