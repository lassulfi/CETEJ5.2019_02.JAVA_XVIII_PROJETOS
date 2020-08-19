package br.utfpr.formularios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "produtos")
public class Produto {

    public enum Status {
        Disponivel, Indisponivel
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable=false)
    @NotEmpty
    private String nome;

    @Column(name = "descricao", nullable = false)
    @NotEmpty
    private String descricao;

    @Column(name = "fornecedor", nullable = false)
    @NotEmpty
    private String fornecedor;

    @Column(name = "email", nullable = false)
    @Email
    @NotEmpty
    private String emailFornecedor;

    @Column(name="data_ultima_compra", nullable=false)
    private LocalDate dataUltimaCompra;

    @Column(name="preco", nullable=false)
    @Min(0)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private Produto.Status status;

    public boolean isPersisted() {
        return id != null;
    }
}
