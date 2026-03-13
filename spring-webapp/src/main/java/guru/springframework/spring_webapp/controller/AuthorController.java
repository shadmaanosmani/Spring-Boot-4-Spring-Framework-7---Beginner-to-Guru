package guru.springframework.spring_webapp.controller;

import guru.springframework.spring_webapp.repository.AuthorRepository;
import guru.springframework.spring_webapp.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {

        this.authorService = authorService;

    }

    @RequestMapping("/authors")
    public  String getAuthors(Model model) {

        model.addAttribute("authors", authorService.findAll());
        return "authors";

    }

}
