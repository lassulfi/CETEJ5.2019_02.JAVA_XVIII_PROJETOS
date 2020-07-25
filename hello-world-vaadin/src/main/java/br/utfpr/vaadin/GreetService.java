package br.utfpr.vaadin;

import org.springframework.stereotype.Service;

@Service
public class GreetService {
    public String greet(String name) {
        if(name == null || name.isEmpty()) {
            return "Informe seu nome!";
        } else {
            return "Olá " + name + "!";
        }
    }
}
