package br.com.forja.domain.venda;

import br.com.forja.domain.cliente.Cliente;
import br.com.forja.domain.usuario.Usuario;
import br.com.forja.shared.audit.Auditavel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venda extends Auditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "valor_bruto", nullable = false)
    private BigDecimal valorBruto;

    @Column(name = "valor_desconto", nullable = false)
    private BigDecimal valorDesconto;

    @Column(name = "valor_acrescimo", nullable = false)
    private BigDecimal valorAcrescimo;

    @Column(name = "valor_liquido", nullable = false)
    private BigDecimal valorLiquido;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusVenda status;

    @Column(name = "cancelada_em")
    private LocalDateTime canceladaEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelada_por")
    private Usuario canceladaPor;

    @Column(name = "motivo_cancelamento", length = 500)
    private String motivoCancelamento;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PagamentoVenda> pagamentos = new ArrayList<>();

}