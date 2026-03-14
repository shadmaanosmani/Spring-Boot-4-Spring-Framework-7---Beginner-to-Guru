package guru.springframework.spring_di;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import guru.springframework.spring_di.controller.MyController;

@SpringBootTest
class SpringDiApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	MyController myController;

	@Test
	void getControllerFromContext() {

		MyController controller = applicationContext.getBean(MyController.class);
		System.out.println(controller.sayHello());

	}
	
	@Test
	void testAutowireOfMyController() {

		System.out.println(myController.sayHello());

	}

	@Test
	void contextLoads() {
	}

}
