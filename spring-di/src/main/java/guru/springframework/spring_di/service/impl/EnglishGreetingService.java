package guru.springframework.spring_di.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.GreetingService;

@Profile("EN")
@Service("i18NService")
public class EnglishGreetingService implements GreetingService {

	@Override
	public String getGreeting() {

		return "Hello World - EN";

	}

}
