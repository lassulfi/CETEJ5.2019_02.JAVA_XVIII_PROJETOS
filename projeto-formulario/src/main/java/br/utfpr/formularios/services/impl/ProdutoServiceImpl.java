package br.utfpr.formularios.services.impl;

import br.utfpr.formularios.model.Produto;
import br.utfpr.formularios.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private static List<Produto> repository = new ArrayList<>();

    @Override
    public List<Produto> findAll(String nameFilter) {
        if(nameFilter == null || nameFilter.isEmpty()) {
            return this.repository;
        } else {
            return this.repository
                    .stream()
                    .filter(p -> p.getNome().equals(nameFilter))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Produto findById(Long id) {
        return this.repository
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Produto findByName(String name) {
        return this.repository
                .stream()
                .filter(p -> p.getNome().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Produto save(Produto produto) {
        Long lastId = 0L;
        if(repository.size() > 0) {
            lastId = repository.get(repository.size() - 1).getId();
        }
        produto.setId(lastId + 1L);
        repository.add(produto);

        return produto;
    }

    @Override
    public Produto update(Produto produto) {
        Produto newProduto = this.findById(produto.getId());
        updateData(newProduto, produto);

        int index = this.repository.indexOf(newProduto);

        this.repository.add(index, newProduto);

        return newProduto;
    }

    @Override
    public void delete(Long id) {
        this.repository.removeIf(p -> p.getId() == id);
    }

    private void updateData(Produto newProduto, Produto produto) {
        newProduto = Produto.builder()
                .id(newProduto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .fornecedor(produto.getDescricao())
                .emailFornecedor(produto.getEmailFornecedor())
                .dataUltimaCompra(produto.getDataUltimaCompra())
                .preco(produto.getPreco())
                .status(produto.getStatus())
                .build();
    }
}
