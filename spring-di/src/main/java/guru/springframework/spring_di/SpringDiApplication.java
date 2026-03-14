package guru.springframework.spring_di;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import guru.springframework.spring_di.controller.MyController;

@SpringBootApplication
public class SpringDiApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(SpringDiApplication.class, args);

		MyController myController = applicationContext.getBean(MyController.class);

		System.out.println("In Main method");
		System.out.println(myController.sayHello());

	}

}
