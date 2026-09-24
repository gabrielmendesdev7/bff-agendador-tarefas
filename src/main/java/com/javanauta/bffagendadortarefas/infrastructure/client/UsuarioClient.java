package com.javanauta.bffagendadortarefas.infrastructure.client;

import com.javanauta.bffagendadortarefas.busines.dto.request.EnderecoRequest;
import com.javanauta.bffagendadortarefas.busines.dto.request.LoginRequest;
import com.javanauta.bffagendadortarefas.busines.dto.request.TelefoneRequest;
import com.javanauta.bffagendadortarefas.busines.dto.request.UsuarioRequest;
import com.javanauta.bffagendadortarefas.busines.dto.request.UsuarioUpdateRequest;
import com.javanauta.bffagendadortarefas.busines.dto.response.EnderecoResponse;
import com.javanauta.bffagendadortarefas.busines.dto.response.TelefoneResponse;
import com.javanauta.bffagendadortarefas.busines.dto.response.UsuarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping
    UsuarioResponse buscarUsuarioPorEmail(@RequestParam("email") String email,
                                          @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioResponse salvaUsuario(@RequestBody UsuarioRequest request);

    @PostMapping("/login")
    String login(@RequestBody LoginRequest request);

    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail(@PathVariable String email,
                               @RequestHeader("Authorization") String token);

    @PutMapping
    UsuarioResponse atualizaDadosUsuario(@RequestBody UsuarioRequest request,
                                         @RequestHeader("Authorization") String token);

    @PutMapping("/endereco")
    EnderecoResponse atualizaEndereco(@RequestBody UsuarioUpdateRequest request,
                                      @RequestParam("id") Long id,
                                      @RequestHeader("Authorization") String token);

    @PutMapping("/telefone")
    TelefoneResponse atualizaTelefone(@RequestBody TelefoneRequest request,
                                      @RequestParam("id") Long id,
                                      @RequestHeader("Authorization") String token);

    @PostMapping("/endereco")
    EnderecoResponse cadastraEndereco(@RequestBody EnderecoRequest request,
                                      @RequestHeader("Authorization") String token);

    @PostMapping("/telefone")
    TelefoneResponse cadastraTelefone(@RequestBody TelefoneRequest request,
                                      @RequestHeader("Authorization") String token);

}
