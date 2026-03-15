package guru.springframework.spring_di.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.GreetingService;

@Profile("DE")
@Service("i18NService")
public class GermanGreetingService implements GreetingService {

	@Override
	public String getGreeting() {

		return "Hallo Welt - DE";

	}

}
