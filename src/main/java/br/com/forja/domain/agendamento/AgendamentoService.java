package br.com.forja.domain.agendamento;

import br.com.forja.domain.agendamento.dto.AgendamentoRequest;
import br.com.forja.domain.agendamento.dto.AgendamentoResponse;
import br.com.forja.domain.cliente.Cliente;
import br.com.forja.domain.cliente.ClienteRepository;
import br.com.forja.domain.raia.Raia;
import br.com.forja.domain.raia.RaiaRepository;
import br.com.forja.exception.AgendamentoNaoEncontradoException;
import br.com.forja.exception.ConflitoAgendamentoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

        private final AgendamentoRepository repository;
        private final ClienteRepository clienteRepository;
        private final RaiaRepository raiaRepository;

        public List<AgendamentoResponse> listar() {

                return repository.findAll()
                                .stream()
                                .map(this::toResponse)
                                .toList();
        }

        public AgendamentoResponse buscarPorId(
                        Long id) {

                Agendamento agendamento = repository.findById(id)
                                .orElseThrow(
                                                () -> new AgendamentoNaoEncontradoException(id));

                return toResponse(agendamento);
        }

        public AgendamentoResponse salvar(
                        AgendamentoRequest request) {
                validarPeriodo(
                                request.dataInicio(),
                                request.dataFim());

                validarConflitoHorario(
                                request.raiaId(),
                                request.dataInicio(),
                                request.dataFim(),
                                null);

                Cliente cliente = clienteRepository.findById(
                                request.clienteId()).orElseThrow();

                Raia raia = raiaRepository.findById(
                                request.raiaId()).orElseThrow();

                Agendamento agendamento = Agendamento.builder()
                                .cliente(cliente)
                                .raia(raia)
                                .dataInicio(request.dataInicio())
                                .dataFim(request.dataFim())
                                .status("AGENDADO")
                                .observacao(request.observacao())
                                .build();

                agendamento = repository.save(agendamento);

                return toResponse(agendamento);
        }

        public AgendamentoResponse atualizar(
                        Long id,
                        AgendamentoRequest request) {

                Agendamento agendamento = repository.findById(id)
                                .orElseThrow(
                                                () -> new AgendamentoNaoEncontradoException(id));

                validarPeriodo(
                                request.dataInicio(),
                                request.dataFim());

                validarConflitoHorario(
                                request.raiaId(),
                                request.dataInicio(),
                                request.dataFim(),
                                id);

                Cliente cliente = clienteRepository.findById(
                                request.clienteId()).orElseThrow();

                Raia raia = raiaRepository.findById(
                                request.raiaId()).orElseThrow();

                agendamento.setCliente(cliente);
                agendamento.setRaia(raia);
                agendamento.setDataInicio(request.dataInicio());
                agendamento.setDataFim(request.dataFim());
                agendamento.setObservacao(request.observacao());

                agendamento = repository.save(agendamento);

                return toResponse(agendamento);
        }

        public void cancelar(
                        Long id) {

                Agendamento agendamento = repository.findById(id)
                                .orElseThrow(
                                                () -> new AgendamentoNaoEncontradoException(id));

                agendamento.setStatus("CANCELADO");

                repository.save(agendamento);
        }

        public void finalizar(
                        Long id) {

                Agendamento agendamento = repository.findById(id)
                                .orElseThrow(
                                                () -> new AgendamentoNaoEncontradoException(id));

                agendamento.setStatus("FINALIZADO");

                repository.save(agendamento);
        }

        private void validarConflitoHorario(
                        Long raiaId,
                        LocalDateTime inicio,
                        LocalDateTime fim,
                        Long agendamentoAtualId) {

                List<Agendamento> existentes = repository.findByRaiaId(raiaId);

                boolean conflito = existentes.stream()
                                .filter(a -> agendamentoAtualId == null
                                                || !a.getId().equals(agendamentoAtualId))
                                .anyMatch(a -> !a.getStatus().equals("CANCELADO")
                                                &&
                                                inicio.isBefore(a.getDataFim())
                                                &&
                                                fim.isAfter(a.getDataInicio()));

                if (conflito) {

                        throw new ConflitoAgendamentoException();
                }
        }

        private void validarPeriodo(
                        LocalDateTime inicio,
                        LocalDateTime fim) {

                if (!fim.isAfter(inicio)) {

                        throw new IllegalArgumentException(
                                        "Data fim deve ser maior que data início.");
                }
        }

        private AgendamentoResponse toResponse(
                        Agendamento agendamento) {

                return new AgendamentoResponse(
                                agendamento.getId(),

                                agendamento.getCliente().getId(),
                                agendamento.getCliente().getNome(),

                                agendamento.getRaia().getId(),
                                agendamento.getRaia().getDescricao(),

                                agendamento.getDataInicio(),
                                agendamento.getDataFim(),

                                agendamento.getStatus(),
                                agendamento.getObservacao());
        }
}