package com.javanauta.bffagendadortarefas.busines;

import com.javanauta.bffagendadortarefas.busines.dto.request.LoginRequest;
import com.javanauta.bffagendadortarefas.busines.dto.response.TarefasResponse;
import com.javanauta.bffagendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
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
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora() {
        String token = login(gerarLoginRequest());
        log.info("Iniciada a busca de tarefas");

        ZoneId localZone = ZoneId.of("America/Sao_Paulo");
        ZoneId utcZone = ZoneId.of("UTC");

        LocalDateTime horaAtualLocal = LocalDateTime.now(localZone)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        LocalDateTime horaFuturaLocal = horaAtualLocal.plusDays(1).withNano(0);

        LocalDateTime horaAtualDb = horaAtualLocal
                .atZone(localZone)
                .withZoneSameInstant(utcZone)
                .toLocalDateTime()
                .withNano(0);

        LocalDateTime horaFuturaDb = horaFuturaLocal
                .atZone(localZone)
                .withZoneSameInstant(utcZone)
                .toLocalDateTime()
                .withNano(0);

        List<TarefasResponse> listaTarefas = tarefasService
                .buscaTarefasAgendadasPorPeriodo(horaAtualDb, horaFuturaDb, token);
        log.info("Tarefas encontradas " + listaTarefas);

        listaTarefas.forEach(tarefa -> {
            emailService.enviarEmail(tarefa);
            log.info("Email enviado para o usuario " + tarefa.getEmailUsuario());
            tarefasService.alteraStatus(StatusNotificacaoEnum.NOTIFICADO,
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
