package it.fsal.webclient;

import it.fsal.webclient.api.InheritableComponent;
import it.fsal.webclient.api.commands.CommandExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "it.fsal.webclient", includeFilters =  @ComponentScan.Filter(InheritableComponent.class))
public class WebClientApplication implements CommandLineRunner {

	private static final Logger log = LogManager.getLogger(WebClientApplication.class);

	public static void main(String... args) {
		if (args == null || args.length == 0) {
			args = new String[] {
					"-WSAuthToken",
					"-IPusername=user",
					"-IPpassword=pass"
			};
		}
		SpringApplication.run(WebClientApplication.class, args).close();
		System.out.println("Execution terminated");
	}

	@Autowired
	private CommandExecutor executor;

	@Autowired
	private ExceptionManager exceptionManager;

	@Override
	public void run(String... args) {
		try {
			log.info(String.format("Invoke WS with parameters %s", Arrays.toString(args)));
			WebServiceCommandParser parser = new WebServiceCommandParser(args);
			executor.dispatch(parser.getService(),parser.getParameters());
		} catch (Exception e) {
			exceptionManager.manageException(e);
		}
	}

}
