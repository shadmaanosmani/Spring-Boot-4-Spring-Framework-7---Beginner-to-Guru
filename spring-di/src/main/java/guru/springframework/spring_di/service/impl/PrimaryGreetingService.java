package guru.springframework.spring_di.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.GreetingService;

@Primary
@Service
public class PrimaryGreetingService implements GreetingService {

	@Override
	public String getGreeting() {

		return "Hello from the Primary Bean!!!";

	}

}
