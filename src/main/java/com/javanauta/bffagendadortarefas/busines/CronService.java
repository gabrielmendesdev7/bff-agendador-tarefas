package com.javanauta.bffagendadortarefas.busines;

import com.javanauta.bffagendadortarefas.busines.dto.request.LoginRequest;
import com.javanauta.bffagendadortarefas.busines.dto.response.TarefasResponse;
import com.javanauta.bffagendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.bffagendadortarefas.infrastructure.message.producer.EmailProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {

    private final TarefasService tarefasService;
    private final EmailProducer emailProducer;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora() {
        String token = login(gerarLoginRequest());
        log.info("Iniciada a busca de tarefas");

        LocalDateTime horaAtualLocal = LocalDateTime.now();

        LocalDateTime horaFuturaLocal = horaAtualLocal.plusDays(1);

        List<TarefasResponse> listaTarefas = tarefasService
                .buscaTarefasAgendadasPorPeriodo(horaAtualLocal, horaFuturaLocal, token);

        log.info("Tarefas encontradas " + listaTarefas);

        listaTarefas.forEach(tarefa -> {
            emailProducer.enviarEmail(tarefa);
            log.info("Email enviado para o usuario " + tarefa.getEmailUsuario());
            tarefasService.alteraStatus(StatusNotificacaoEnum.AGUARDANDO,
                    tarefa.getId(),
                    token);
        });

        log.info("Finalizada a busca e notificação de tarefas");
    }

    public String login(LoginRequest request) {
        return usuarioService.loginUsuario(request);
    }

    public LoginRequest gerarLoginRequest() {
        return LoginRequest.builder()
                .email(email)
                .senha(senha)
                .build();
    }

}
