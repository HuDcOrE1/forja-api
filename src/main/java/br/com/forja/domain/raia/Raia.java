package br.com.forja.domain.raia;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "raia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Raia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativa")
    private Boolean ativa;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}