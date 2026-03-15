package guru.springframework.spring_di.service.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.GreetingService;

@Service("setterInjectedGreetingService")
public class GreetingServiceSetterInjected implements GreetingService {

	@Override
	public String getGreeting() {

		return "Hey I'm setting a greeting!!!";

	}

}
