package br.utfpr.formularios.services.impl;

import br.utfpr.formularios.model.Produto;
import br.utfpr.formularios.repository.ProdutoRepository;
import br.utfpr.formularios.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;

    @Override
    public List<Produto> findAll(String nameFilter) {
        if(nameFilter == null || nameFilter.isEmpty()) {
            return this.repository.findAll();
        } else {
            return this.repository.findByNome(nameFilter);
        }
    }

    @Override
    public Produto findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Produto save(Produto produto) {
        produto.setId(null);
        Produto entity = this.repository.save(produto);
        return entity;
    }

    @Override
    public Produto update(Produto produto) {
        Produto newProduto = this.findById(produto.getId());
        updateData(newProduto, produto);

        return repository.save(newProduto);
    }

    @Override
    public void delete(Long id) {
        this.repository.findById(id);
    }

    @PostConstruct
    public void populateTestData() {
        if(repository.count() == 0) {
            repository.saveAll(Arrays.asList(Produto.builder().nome("Notebook Dell").descricao("Notebook Dell").fornecedor("Dell").emailFornecedor("contato@dell.com.br").dataUltimaCompra(new Date()).preco(new BigDecimal("2850.00")).status(Produto.Status.Disponivel).build(),
                    Produto.builder().nome("Monitor Bluecase 15in").descricao("Monitor Bluecase").fornecedor("Bluecase").emailFornecedor("contato@bluecase.com.br").dataUltimaCompra(new Date()).preco(new BigDecimal("390.00")).status(Produto.Status.Indisponivel).build(),
                    Produto.builder().nome("Mouse Red Dragon").descricao("Mouse Red Dragon").fornecedor("Red Dragon").emailFornecedor("contato@reddragon.com.br").dataUltimaCompra(new Date()).preco(new BigDecimal("100.00")).status(Produto.Status.Indisponivel).build(),
                    Produto.builder().nome("Teclado Logitec").descricao("Teclado mecânico Logitec").fornecedor("Logitec").emailFornecedor("contato@logitec.com.br").dataUltimaCompra(new Date()).preco(new BigDecimal("250.00")).status(Produto.Status.Disponivel).build()));
        }
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
