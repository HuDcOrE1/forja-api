package br.com.forja.domain.produto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.forja.shared.audit.Auditavel;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto extends Auditavel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private CategoriaProduto categoria;

    @Column(name = "estoque")
    private Integer estoque;

    @Column(name = "valor_custo")
    private BigDecimal valorCusto;

    @Column(name = "valor_venda")
    private BigDecimal valorVenda;

    @Column(name = "ativo")
    private Boolean ativo;

}