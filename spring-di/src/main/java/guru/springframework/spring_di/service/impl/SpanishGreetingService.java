package guru.springframework.spring_di.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.GreetingService;

@Profile("ES")
@Service("i18NService")
public class SpanishGreetingService implements GreetingService {

	@Override
	public String getGreeting() {

		return "Hola Mundo - ES";

	}

}
