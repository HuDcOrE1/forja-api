package br.com.forja.domain.venda;

import br.com.forja.domain.cliente.Cliente;
import br.com.forja.domain.usuario.Usuario;
import br.com.forja.shared.audit.Auditavel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "forma_pagamento")
    private String formaPagamento;

    @Column(name = "status")
    private String status;

}