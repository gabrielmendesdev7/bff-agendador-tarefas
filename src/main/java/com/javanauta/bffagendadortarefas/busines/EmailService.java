package com.javanauta.bffagendadortarefas.busines;

import com.javanauta.bffagendadortarefas.busines.dto.response.TarefasResponse;
import com.javanauta.bffagendadortarefas.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void enviarEmail(TarefasResponse response) {
        emailClient.enviarEmail(response);
    }

}
