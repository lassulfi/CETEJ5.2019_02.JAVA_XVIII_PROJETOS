package br.utfpr.formularios.repository;

import br.utfpr.formularios.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    private static List<Produto> repository = new ArrayList<>();

    public List<Produto> findAll() {
        return repository;
    }

    public void save(Produto produto) {
        this.repository.add(produto);
    }

    public Produto findById(int id) {
        return repository.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void update(Produto produto) {
        Produto entity = this.findById(produto.getId());
    }

    public void deleteById(int id) {
        repository.removeIf(p -> p.getId() == id);
    }
}
