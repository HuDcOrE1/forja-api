package br.com.forja.shared.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public abstract class Auditavel {

    @Column(name = "criado_por")
    private String criadoPor;

    @Column(name = "alterado_por")
    private String alteradoPor;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "alterado_em")
    private LocalDateTime alteradoEm;
}