package guru.springframework.spring_di.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.EnvService;

@Profile("uat")
@Service("envService")
public class UatEnvServiceImpl implements EnvService {

	@Override
	public String getEnv() {

		return "You're in UAT environment";

	}

}
