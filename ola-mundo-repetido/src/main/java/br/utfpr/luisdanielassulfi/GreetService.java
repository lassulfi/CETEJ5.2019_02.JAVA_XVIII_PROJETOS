package br.utfpr.luisdanielassulfi;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public class GreetService implements Serializable {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Olá, usuário anônimo!";
        } else {
            return "Olá, " + name + "!";
        }
    }

}
