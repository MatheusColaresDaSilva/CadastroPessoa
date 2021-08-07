package com.elotech.cadastropessoa.integration;

import com.elotech.dto.response.ErroResponseDTO;
import com.elotech.dto.response.ResponseDTO;
import com.elotech.repository.PessoaRepository;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public class IntegrationBaseTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected PessoaRepository pessoaRepository;

    @BeforeEach
    public void before() {

        RestAssured.port = port;

        pessoaRepository.deleteAll();
    }

    @AfterEach
    public void after() {
;
        pessoaRepository.deleteAll();
    }

    protected <T> void assertMensagemErro(ResponseDTO<T> response, String mesagem) {
        MatcherAssert.assertThat(response.getErros(), Matchers.hasSize(1));
        ErroResponseDTO erroResponseDTO = response.getErros().stream().findFirst().get();
        MatcherAssert.assertThat(erroResponseDTO.getMensagem(), CoreMatchers.containsString(mesagem));
    }

}
