package React.h2memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(App.class, args);
		String[] names = context.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
	}
}
