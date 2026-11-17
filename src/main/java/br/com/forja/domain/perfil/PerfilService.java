package br.com.forja.domain.perfil;

import br.com.forja.domain.perfil.dto.PerfilResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository repository;

    public List<PerfilResponse> listar() {

        return repository.findAll()
                .stream()
                .map(
                        perfil -> new PerfilResponse(
                                perfil.getId(),
                                perfil.getNome()))
                .toList();

    }

}