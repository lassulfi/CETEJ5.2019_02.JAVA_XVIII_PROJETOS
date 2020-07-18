package br.utfpr.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.utfpr.dao.UsuarioDAO;
import br.utfpr.model.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String login; 
	
	private Usuario usuarioEntity;
	private UsuarioDAO usuarioDao;
	
	public UsuarioController() {
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
		if(login == null || login.isEmpty()) {
			usuarioDao.save(usuarioEntity);
		} else {
			usuarioDao.update(usuarioEntity, login);
		}
		login = null;
		usuarioEntity = new Usuario();
		
		return "index.xhtml";
	}
	
	public String edit(String login) {
		usuarioEntity = usuarioDao.getUsuarioByLogin(login);
		this.login = login;
		
		return "index.xhtml";
	}
	
	public String delete(String login) {
		usuarioDao.delete(login);
		
		return "index.xhtml";
	}
}
