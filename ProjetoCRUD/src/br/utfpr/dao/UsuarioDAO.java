package br.utfpr.dao;

import java.util.ArrayList;
import java.util.List;

import br.utfpr.model.Usuario;

public class UsuarioDAO {
	
	protected static final ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	public UsuarioDAO() {}
	
	public void save(Usuario entity) {
		usuarios.add(entity);
	}
	
	public List<Usuario> getList() {
		return usuarios;
	}
	
	public void delete(String login) {
		Usuario user = this.getUsuarioByLogin(login);
		if (user != null) {
			usuarios.remove(user);
		}
	}
	
	public void update(Usuario entity, String login) {
		Usuario user = this.getUsuarioByLogin(login);
		user.setLogin(entity.getLogin());
		user.setNome(entity.getNome());
		user.setSenha(entity.getSenha());
		user.setIdade(entity.getIdade());
	}
	
	public Usuario getUsuarioByLogin(String login) {
		for(Usuario user: usuarios) {
			if(user.getLogin().equals(login)) {
				return user;
			}
		}
		
		return null;
	}
}
