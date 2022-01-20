package it.fsal.webclient.api.commands;

import it.fsal.webclient.api.util.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class CommandExecutor {

    private final Map<String, AbstractWebServiceCommand> commandsMap = new TreeMap<>();

    @Autowired
    private CommandExecutor(List<AbstractWebServiceCommand> handlers) {
        handlers.forEach(h -> commandsMap.put(h.getName(),h));
    }

    public void dispatch(String requestedService, Map<String,String> params) throws CommandException {

        if (StringsUtil.isBlank(requestedService)) {
            throw new InvalidCommandException(requestedService);
        }

        try {
            AbstractWebServiceCommand handler = commandsMap.get(requestedService);
            if (handler == null) {
                throw new InvalidCommandException(requestedService);
            }
            handler.execute(params);
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }


    public static class CommandException extends Exception {

        public CommandException() {

        }

        public CommandException(Throwable cause) {
            super(cause);
        }
    }

    public static class InvalidCommandException extends CommandException {

        private final String commandName;

        public InvalidCommandException(String commandName) {
            this.commandName = commandName;
        }

        public String getCommandName() {
            return commandName;
        }
    }
}
