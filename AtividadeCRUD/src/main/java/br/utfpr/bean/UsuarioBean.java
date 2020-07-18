/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utfpr.bean;

import br.utfpr.dao.UsuarioDAO;
import br.utfpr.model.Usuario;
import br.utfpr.utils.FacesMessageUtils;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author lassulfi
 */
@Named(value = "usuarioBean")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String login = "";

    private Usuario usuarioEntity;
    private UsuarioDAO usuarioDao;

    public UsuarioBean() {
        usuarioEntity = new Usuario();
        usuarioDao = new UsuarioDAO();
    }

    public Usuario getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(Usuario usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Usuario> getList() {
        return usuarioDao.getList();
    }

    public String save() {
        if (login == null || login.isEmpty()) {
            usuarioDao.save(usuarioEntity);
        } else {
            usuarioDao.update(usuarioEntity, login);
        }
        login = null;
        usuarioEntity = new Usuario();

        return "index.xhtml";
    }

    public void edit(String login) {
        usuarioEntity = usuarioDao.getUsuarioByLogin(login);
        this.login = login;
    }

    public String delete(String login) {
        usuarioDao.delete(login);

        return "index.xhtml";
    }
    
    public void validateUniqueLogin(FacesContext context, UIComponent toValidate, Object value) {
        String login = (String) value;
        Usuario usuario = usuarioDao.getUsuarioByLogin(login);
        if (usuario != null) {
            FacesMessageUtils.setErrorMessage(context, toValidate, "Login já existente");
        }        
    }
    
    public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
        String senha = (String) value;
        Pattern patternSenha = Pattern.compile("^[A-z|0-9]+$");
        Matcher matcher = patternSenha.matcher(senha);
        if(!matcher.matches()) {
            FacesMessageUtils.setErrorMessage(context, toValidate, "Senha deve conter letras e números apenas");
        }
    }
}
