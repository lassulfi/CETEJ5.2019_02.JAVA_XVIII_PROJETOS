/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utfpr.model;

import java.io.Serializable;

/**
 *
 * @author lassulfi
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;
    private int idade;

    public Usuario() {}

    public Usuario(String login, String senha, String nome, int idade) {
            super();
            this.login = login;
            this.senha = senha;
            this.nome = nome;
            this.idade = idade;
    }

    public String getLogin() {
            return login;
    }

    public void setLogin(String login) {
            this.login = login;
    }

    public String getSenha() {
            return senha;
    }

    public void setSenha(String senha) {
            this.senha = senha;
    }

    public String getNome() {
            return nome;
    }

    public void setNome(String nome) {
            this.nome = nome;
    }

    public int getIdade() {
            return idade;
    }

    public void setIdade(int idade) {
            this.idade = idade;
    }
}
