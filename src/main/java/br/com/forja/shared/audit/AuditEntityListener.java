package br.com.forja.shared.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(
            Object entity
    ) {

        if (entity instanceof Auditavel auditavel) {

            auditavel.setCriadoEm(
                    LocalDateTime.now()
            );

            auditavel.setCriadoPor(
                    usuarioLogado()
            );
        }
    }

    @PreUpdate
    public void preUpdate(
            Object entity
    ) {

        if (entity instanceof Auditavel auditavel) {

            auditavel.setAlteradoEm(
                    LocalDateTime.now()
            );

            auditavel.setAlteradoPor(
                    usuarioLogado()
            );
        }
    }

    private String usuarioLogado() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null) {
            return "SYSTEM";
        }

        return authentication.getName();
    }
}