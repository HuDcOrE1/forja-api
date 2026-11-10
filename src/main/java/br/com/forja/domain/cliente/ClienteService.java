package br.com.forja.domain.cliente;

import br.com.forja.domain.cliente.dto.ClienteRequest;
import br.com.forja.domain.cliente.dto.ClienteResponse;
import br.com.forja.exception.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public List<ClienteResponse> listar() {

        return repository.findByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ClienteResponse buscarPorId(Long id) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException(id)
                );

        return toResponse(cliente);
    }

    public ClienteResponse salvar(ClienteRequest request) {

        System.out.println(request);

        Cliente cliente = Cliente.builder()
                .nome(request.nome())
                .cpf(request.cpf())
                .telefone(request.telefone())
                .email(request.email())
                .cr(request.cr())
                .ativo(
                    request.ativo() != null
                        ? request.ativo()
                        : true
                )

                .cep(request.cep())
                .logradouro(request.logradouro())
                .numero(request.numero())
                .complemento(request.complemento())
                .bairro(request.bairro())
                .cidade(request.cidade())
                .uf(request.uf())

                .build();

        cliente = repository.save(cliente);

        return toResponse(cliente);
    }

    public ClienteResponse atualizar(
            Long id,
            ClienteRequest request
    ) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException(id)
                );

        cliente.setNome(request.nome());
        cliente.setCpf(request.cpf());
        cliente.setTelefone(request.telefone());
        cliente.setEmail(request.email());
        cliente.setCr(request.cr());
        cliente.setAtivo(request.ativo());

        cliente.setCep(request.cep());
        cliente.setLogradouro(request.logradouro());
        cliente.setNumero(request.numero());
        cliente.setComplemento(request.complemento());
        cliente.setBairro(request.bairro());
        cliente.setCidade(request.cidade());
        cliente.setUf(request.uf());

        cliente = repository.save(cliente);

        return toResponse(cliente);
    }

    public void excluir(Long id) {

        Cliente cliente =
                repository.findById(id)
                        .orElseThrow(
                                () ->
                                        new ClienteNaoEncontradoException(id)
                        );

        cliente.setAtivo(false);

        repository.save(cliente);
    }

    private ClienteResponse toResponse(
            Cliente cliente
    ) {

        return new ClienteResponse(

                cliente.getId(),

                cliente.getNome(),

                cliente.getCpf(),

                cliente.getTelefone(),

                cliente.getEmail(),

                cliente.getCr(),

                cliente.getAtivo(),

                cliente.getCep(),

                cliente.getLogradouro(),

                cliente.getNumero(),

                cliente.getComplemento(),

                cliente.getBairro(),

                cliente.getCidade(),

                cliente.getUf()
        );
    }
}