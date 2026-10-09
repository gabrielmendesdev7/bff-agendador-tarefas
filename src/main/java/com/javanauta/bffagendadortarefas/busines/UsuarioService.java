package com.javanauta.bffagendadortarefas.busines;

import com.javanauta.bffagendadortarefas.busines.dto.request.EnderecoRequest;
import com.javanauta.bffagendadortarefas.busines.dto.request.LoginRequest;
import com.javanauta.bffagendadortarefas.busines.dto.request.TelefoneRequest;
import com.javanauta.bffagendadortarefas.busines.dto.request.UsuarioRequest;
import com.javanauta.bffagendadortarefas.busines.dto.request.UsuarioUpdateRequest;
import com.javanauta.bffagendadortarefas.busines.dto.response.EnderecoResponse;
import com.javanauta.bffagendadortarefas.busines.dto.response.TelefoneResponse;
import com.javanauta.bffagendadortarefas.busines.dto.response.UsuarioResponse;
import com.javanauta.bffagendadortarefas.busines.dto.response.ViaCepResponse;
import com.javanauta.bffagendadortarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient client;

    public UsuarioResponse salvaUsuario(UsuarioRequest request) {
        return client.salvaUsuario(request);
    }

    public String loginUsuario(LoginRequest request) {
        return client.login(request);
    }

    public UsuarioResponse buscarUsuarioPorEmail(String email, String token) {
        return client.buscarUsuarioPorEmail(email, token);
    }

    public void deletaUsuarioPorEmail(String email, String token) {
        client.deletaUsuarioPorEmail(email, token);
    }

    public UsuarioResponse atualizaDadosUsuario(String token, UsuarioRequest request) {
        return client.atualizaDadosUsuario(request, token);
    }

    public EnderecoResponse atualizaEndereco(Long idEndereco, UsuarioUpdateRequest request, String token) {
        return client.atualizaEndereco(request, idEndereco, token);
    }

    public TelefoneResponse atualizaTelefone(Long idTelefone, TelefoneRequest request, String token) {
        return client.atualizaTelefone(request, idTelefone, token);
    }

    public EnderecoResponse cadastraEndereco(String token, EnderecoRequest request) {
        return client.cadastraEndereco(request, token);
    }

    public TelefoneResponse cadastraTelefone(String token, TelefoneRequest request) {
      return client.cadastraTelefone(request, token);
    }

    public ViaCepResponse buscaEnderecoPorCep(String cep) {
        return client.buscarEnderecoPorCep(cep);
    }

}
