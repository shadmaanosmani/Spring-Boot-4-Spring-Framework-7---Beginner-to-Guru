package guru.springframework.spring_di.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.EnvService;

@Profile("qa")
@Service("envService")
public class QaEnvServiceImpl implements EnvService {

	@Override
	public String getEnv() {

		return "You're in QA environment";

	}

}
