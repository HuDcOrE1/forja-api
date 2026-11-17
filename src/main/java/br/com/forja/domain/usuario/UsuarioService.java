package br.com.forja.domain.usuario;

import br.com.forja.domain.perfil.Perfil;
import br.com.forja.domain.perfil.PerfilRepository;
import br.com.forja.domain.usuario.dto.UsuarioRequest;
import br.com.forja.domain.usuario.dto.UsuarioResponse;
import br.com.forja.exception.UsuarioNaoEncontradoException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

        private final UsuarioRepository repository;

        private final PerfilRepository perfilRepository;

        private final PasswordEncoder passwordEncoder;

        public java.util.List<UsuarioResponse> listar() {

                return repository.findAll()
                                .stream()
                                .map(this::toResponse)
                                .toList();

        }

        public UsuarioResponse buscarPorId(
                        Long id) {

                Usuario usuario = repository.findById(id)
                                .orElseThrow(
                                                () -> new UsuarioNaoEncontradoException(id));

                return toResponse(usuario);

        }

        public UsuarioResponse salvar(
                        UsuarioRequest request) {
                if (repository.findByEmail(request.email()).isPresent()) {

                        throw new IllegalArgumentException(
                                        "Já existe um usuário cadastrado com este email.");

                }

                if (request.senha() == null
                                ||
                                request.senha().isBlank()) {

                        throw new IllegalArgumentException(
                                        "Senha é obrigatória.");

                }

                Set<Perfil> perfis = perfilRepository.findAllById(
                                request.perfis())
                                .stream()
                                .collect(
                                                Collectors.toSet());

                Usuario usuario = Usuario.builder()
                                .nome(
                                                request.nome())
                                .email(
                                                request.email())
                                .senha(
                                                passwordEncoder.encode(
                                                                request.senha()))
                                .ativo(
                                                request.ativo() != null
                                                                ? request.ativo()
                                                                : true)
                                .perfis(
                                                perfis)
                                .build();

                usuario = repository.save(usuario);

                return toResponse(usuario);

        }

        public UsuarioResponse atualizar(
                        Long id,
                        UsuarioRequest request) {

                Optional<Usuario> existente = repository.findByEmail(
                                request.email());

                if (existente.isPresent()
                                &&
                                !existente.get().getId().equals(id)) {

                        throw new IllegalArgumentException(
                                        "Já existe um usuário cadastrado com este email.");

                }

                Usuario usuario = repository.findById(id)
                                .orElseThrow(
                                                () -> new UsuarioNaoEncontradoException(id));

                Set<Perfil> perfis = perfilRepository.findAllById(
                                request.perfis())
                                .stream()
                                .collect(
                                                Collectors.toSet());

                usuario.setNome(
                                request.nome());

                usuario.setEmail(
                                request.email());

                usuario.setAtivo(
                                request.ativo());

                usuario.setPerfis(
                                perfis);

                if (request.senha() != null
                                && !request.senha().isBlank()) {

                        usuario.setSenha(
                                        passwordEncoder.encode(
                                                        request.senha()));

                }

                usuario = repository.save(usuario);

                return toResponse(usuario);

        }

        public void inativar(
                        Long id) {

                Usuario usuario = repository.findById(id)
                                .orElseThrow(
                                                () -> new UsuarioNaoEncontradoException(id));

                usuario.setAtivo(false);

                repository.save(usuario);

        }

        private UsuarioResponse toResponse(
                        Usuario usuario) {

                return new UsuarioResponse(

                                usuario.getId(),

                                usuario.getNome(),

                                usuario.getEmail(),

                                usuario.getAtivo(),

                                usuario.getPerfis()
                                                .stream()
                                                .map(
                                                                Perfil::getNome)
                                                .collect(
                                                                Collectors.toSet())

                );

        }

}