package br.com.forja.domain.raia;

import br.com.forja.domain.raia.dto.RaiaRequest;
import br.com.forja.domain.raia.dto.RaiaResponse;
import br.com.forja.exception.RaiaNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RaiaService {

    private final RaiaRepository repository;

    public List<RaiaResponse> listar() {

        return repository.findByAtivaTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RaiaResponse buscarPorId(
            Long id
    ) {

        Raia raia =
                repository.findById(id)
                        .orElseThrow(
                                () -> new RaiaNaoEncontradaException(id)
                        );

        return toResponse(raia);
    }

    public RaiaResponse salvar(
            RaiaRequest request
    ) {

        Raia raia =
                Raia.builder()
                        .numero(request.numero())
                        .descricao(request.descricao())
                        .ativa(true)
                        .criadoEm(LocalDateTime.now())
                        .build();

        raia = repository.save(raia);

        return toResponse(raia);
    }

    public RaiaResponse atualizar(
            Long id,
            RaiaRequest request
    ) {

        Raia raia =
                repository.findById(id)
                        .orElseThrow(
                                () -> new RaiaNaoEncontradaException(id)
                        );

        raia.setNumero(
                request.numero()
        );

        raia.setDescricao(
                request.descricao()
        );

        raia = repository.save(raia);

        return toResponse(raia);
    }

    public void excluir(
            Long id
    ) {

        Raia raia =
                repository.findById(id)
                        .orElseThrow(
                                () -> new RaiaNaoEncontradaException(id)
                        );

        raia.setAtiva(false);

        repository.save(raia);
    }

    private RaiaResponse toResponse(
            Raia raia
    ) {

        return new RaiaResponse(
                raia.getId(),
                raia.getNumero(),
                raia.getDescricao(),
                raia.getAtiva()
        );
    }
}