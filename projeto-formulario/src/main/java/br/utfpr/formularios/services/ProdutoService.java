package br.utfpr.formularios.services;

import br.utfpr.formularios.model.Produto;

import java.util.List;

public interface ProdutoService {

    List<Produto> findAll(String nameFilter);

    Produto findById(Long id);

    Produto findByName(String name);

    Produto save(Produto produto);

    Produto update(Produto produto);

    void delete(Long id);
}
