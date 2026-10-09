package com.javanauta.bffagendadortarefas.busines;

import com.javanauta.bffagendadortarefas.busines.dto.request.TarefasRequest;
import com.javanauta.bffagendadortarefas.busines.dto.response.TarefasResponse;
import com.javanauta.bffagendadortarefas.infrastructure.client.TarefasClient;
import com.javanauta.bffagendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasClient tarefasClient;

    public TarefasResponse gravarTarefas(String token, TarefasRequest request) {
        return tarefasClient.gravarTarefas(request, token);
    }

    public List<TarefasResponse> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial,
                                                                 LocalDateTime dataFinal,
                                                                 String token) {
        return tarefasClient.buscaListaDeTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    public List<TarefasResponse> buscaTarefasPorEmail(String token) {
        return tarefasClient.buscaTarefasPorEmail(token);
    }

    public void deletaTarefaPorId(String id, String token) {
        tarefasClient.deletaTarefaPorId(id, token);
    }

    public TarefasResponse alteraStatus(StatusNotificacaoEnum status, String id, String token) {
        return tarefasClient.alteraStatusNotificacao(status, id, token);
    }

    public TarefasResponse updateTarefas(TarefasRequest request, String id, String token) {
        return tarefasClient.updateTarefas(request, id, token);
    }

}
