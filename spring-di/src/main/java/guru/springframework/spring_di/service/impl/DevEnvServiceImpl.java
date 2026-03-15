package guru.springframework.spring_di.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.EnvService;

@Profile({ "dev", "default" })
@Service("envService")
public class DevEnvServiceImpl implements EnvService {

	@Override
	public String getEnv() {

		return "You're in DEV environment";

	}

}
