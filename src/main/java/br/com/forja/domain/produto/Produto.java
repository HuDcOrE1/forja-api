package br.com.forja.domain.produto;

import br.com.forja.shared.audit.Auditavel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto extends Auditavel {

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

    @Column(name = "marca")
    private String marca;

    @Column(name = "unidade_medida")
    private String unidadeMedida;

    @Column(name = "estoque_atual")
    private Integer estoqueAtual;

    @Column(name = "estoque_minimo")
    private Integer estoqueMinimo;

    @Column(name = "valor_custo")
    private BigDecimal valorCusto;

    @Column(name = "valor_venda")
    private BigDecimal valorVenda;

    @Column(name = "ativo")
    private Boolean ativo;

}