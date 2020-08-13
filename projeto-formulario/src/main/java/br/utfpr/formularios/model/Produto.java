package br.utfpr.formularios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Produto {

    public enum Status {
        Disponivel, Indisponivel
    }
    private Long id;

    private String nome;

    private String descricao;

    private String fornecedor;

    private String emailFornecedor;

    private Date dataUltimaCompra;

    private BigDecimal preco;

    private Produto.Status status;
}
