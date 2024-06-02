package com.elotech.cadastropessoa.integration;

import com.elotech.dto.request.ContatoRequestDTO;
import com.elotech.dto.request.PessoaRequestDTO;
import com.elotech.dto.response.PessoaResponseDTO;
import com.elotech.dto.response.ResponseDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PessoaControllerTest extends IntegrationBaseTest{

    private final String URL = "/api/v1/pessoa";

    @Test
    public void criaPessoaSemContatoComErro() {
        PessoaRequestDTO pessoaResquestDTO = criaPessoaSemContatoResquestDTO();
        final ResponseDTO<PessoaResponseDTO> response = CadastroPessoaTestUtil.createRequestPost(URL, pessoaResquestDTO, HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<PessoaResponseDTO>>() {
                });

        assertMensagemErro(response, "List must contain on element at least");
    }

    @Test
    public void criaPessoaCpfInvalidoComErro() {
        PessoaRequestDTO pessoaResquestDTO = criaPessoaSemContatoResquestDTO();
        pessoaResquestDTO.setCpf("1234567891W");
        final ResponseDTO<PessoaResponseDTO> response = CadastroPessoaTestUtil.createRequestPost(URL, pessoaResquestDTO, HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<PessoaResponseDTO>>() {
                });

        assertMensagemErro(response, "CPF Inválido");
    }

    @Test
    public void criaPessoaDataNascimentoSuperiorComErro() {
        PessoaRequestDTO pessoaResquestDTO = criaPessoaSemContatoResquestDTO();
        pessoaResquestDTO.setDataNascimento(LocalDate.of(9999,01,01));
        final ResponseDTO<PessoaResponseDTO> response = CadastroPessoaTestUtil.createRequestPost(URL, pessoaResquestDTO, HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<PessoaResponseDTO>>() {
                });

        assertMensagemErro(response, "Data de nascimento superior a data atual");
    }

    @Test
    public void criaPessoaContatoEmailInalidoComErro() {
        PessoaRequestDTO pessoaResquestDTO = criaPessoaComContatoRequestDTO();
        pessoaResquestDTO.getContatos().stream().findFirst().get().setEmail("joao!.yahoo");
        final ResponseDTO<PessoaResponseDTO> response = CadastroPessoaTestUtil.createRequestPost(URL, pessoaResquestDTO, HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<PessoaResponseDTO>>() {
                });

        assertMensagemErro(response, "Email Inválido");
    }

    @Test
    public void criaPessoaComContatoComSucesso() {
        PessoaRequestDTO pessoaResquestDTO = criaPessoaComContatoRequestDTO();
        CadastroPessoaTestUtil.createRequestPost(URL, pessoaResquestDTO, HttpStatus.CREATED)
                .rootPath("dados")
                .body("id", Matchers.greaterThan(0))
                .body("nome", Matchers.equalTo("Jose"))
                .body("cpf",Matchers.equalTo("40906378052"))
                .body("dataNascimento",Matchers.equalTo("2021-05-02"))
                .body("contatos[0].id",Matchers.greaterThan(0))
                        .body("contatos[0].nome",Matchers.equalTo("Maria"))
                        .body("contatos[0].telefone", Matchers.equalTo(44999989635L))
                        .body("contatos[0].email", Matchers.equalTo("maria@gmail.com"));
    }

    @Test
    public void consultaPessoPorIdSucesso() {
        PessoaRequestDTO pessoaResquestDTO = criaPessoaComContatoRequestDTO();
        ResponseDTO<PessoaResponseDTO> response = CadastroPessoaTestUtil.createRequestPost(URL, pessoaResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<PessoaResponseDTO>>() {
                });

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addPathParam("id", response.getContent().getId())
                .build();

        CadastroPessoaTestUtil.createRequestGet(URL+"/{id}",requestSpecification, HttpStatus.OK);
    }

    @Test
    public void consultaPessoaPorIdPessoaNaoExistente() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addPathParam("id", 1)
                .build();

        ResponseDTO<PessoaResponseDTO> response = CadastroPessoaTestUtil.createRequestGet(URL + "/{id}", requestSpecification, HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<PessoaResponseDTO>>() {});

        assertMensagemErro(response, "Pessoa não encontrada");
    }

    @Test
    public void atualizaPessoa() {
        PessoaRequestDTO pessoaResquestDTO = criaPessoaComContatoRequestDTO();
        ResponseDTO<PessoaResponseDTO> response = CadastroPessoaTestUtil.createRequestPost(URL, pessoaResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<PessoaResponseDTO>>() {
                });

        pessoaResquestDTO.setNome("Nome Atualizado");
        pessoaResquestDTO.setDataNascimento(LocalDate.of(2021, 10, 1));
        pessoaResquestDTO.setCpf("48779827012");

        CadastroPessoaTestUtil.createRequestPut(URL+"/"+response.getContent().getId(),pessoaResquestDTO, HttpStatus.OK)
                .rootPath("dados")
                .body("id", Matchers.greaterThan(0))
                .body("nome", Matchers.equalTo("Nome Atualizado"))
                .body("cpf",Matchers.equalTo("48779827012"))
                .body("dataNascimento",Matchers.equalTo("2021-10-01"))
                .body("contatos[0].id",Matchers.greaterThan(0))
                    .body("contatos[0].nome",Matchers.equalTo("Maria"))
                    .body("contatos[0].telefone", Matchers.equalTo(44999989635L))
                    .body("contatos[0].email", Matchers.equalTo("maria@gmail.com"));
    }

    @Test
    public void deletaPessoa() {
        PessoaRequestDTO pessoaResquestDTO = criaPessoaComContatoRequestDTO();
        ResponseDTO<PessoaResponseDTO> response = CadastroPessoaTestUtil.createRequestPost(URL, pessoaResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<PessoaResponseDTO>>() {
                });

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addPathParam("id", response.getContent().getId())
                .build();

        CadastroPessoaTestUtil.createRequestDelete(URL+"/{id}",requestSpecification, HttpStatus.OK);
    }

    private PessoaRequestDTO criaPessoaSemContatoResquestDTO() {
        List< ContatoRequestDTO > contatos = new ArrayList<>();
        return PessoaRequestDTO.builder()
                .nome("Jose")
                .cpf("40906378052")
                .dataNascimento(LocalDate.of(2021,05,02))
                .contatos(contatos)
                .build();
    }

    private PessoaRequestDTO criaPessoaComContatoRequestDTO() {
        List<ContatoRequestDTO> contatos = new ArrayList<>();
        contatos.add(criaContatoRequestDTO());
        return PessoaRequestDTO.builder()
                .nome("Jose")
                .cpf("40906378052")
                .dataNascimento(LocalDate.of(2021,05,02))
                .contatos(contatos)
                .build();
    }

    private ContatoRequestDTO criaContatoRequestDTO() {
        return ContatoRequestDTO.builder()
                .nome("Maria")
                .telefone(44999989635L)
                .email("maria@gmail.com")
                .build();
    }

}
