package guru.springframework.spring_di.controller;

import org.springframework.stereotype.Controller;

import guru.springframework.spring_di.service.EnvService;

@Controller
public class EnvController {

	private final EnvService envService;

	public EnvController(EnvService envService) {

		this.envService = envService;

	}

	public String getEnv() {

		return envService.getEnv();

	}

}
