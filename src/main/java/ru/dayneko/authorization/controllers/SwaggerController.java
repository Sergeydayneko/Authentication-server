package ru.dayneko.authorization.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@RequestMapping("/v1")
public class SwaggerController {
    @GetMapping("/swagger")
    public String swaggerRedirect() {
        return "redirect:/swagger-ui.html";
    }
}
