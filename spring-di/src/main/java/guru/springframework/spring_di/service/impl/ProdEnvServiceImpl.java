package guru.springframework.spring_di.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.EnvService;

@Profile("prod")
@Service("envService")
public class ProdEnvServiceImpl implements EnvService {

	@Override
	public String getEnv() {

		return "You're in PROD environment";

	}

}
