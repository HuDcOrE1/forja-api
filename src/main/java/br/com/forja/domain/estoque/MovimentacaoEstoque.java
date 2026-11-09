package br.com.forja.domain.estoque;

import br.com.forja.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}