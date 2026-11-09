package br.com.forja.domain.cliente;

import br.com.forja.shared.audit.Auditavel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente extends Auditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private String cr;

    private Boolean ativo;
}