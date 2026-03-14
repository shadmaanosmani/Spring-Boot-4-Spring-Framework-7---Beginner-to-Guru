package guru.springframework.spring_di.service.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {

	@Override
	public String getGreeting() {

		return "Hello Everyone from Base Service!!!";

	}

}
