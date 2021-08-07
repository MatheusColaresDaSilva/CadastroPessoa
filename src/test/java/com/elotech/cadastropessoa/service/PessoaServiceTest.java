package com.elotech.cadastropessoa.service;

import com.elotech.dto.request.ContatoRequestDTO;
import com.elotech.dto.request.PessoaRequestDTO;
import com.elotech.dto.response.ContatoResponseDTO;
import com.elotech.dto.response.PessoaResponseDTO;
import com.elotech.entity.Contato;
import com.elotech.entity.Pessoa;
import com.elotech.exception.CpfInvalidoException;
import com.elotech.exception.DataNascimentoFuturaException;
import com.elotech.exception.EmailInvalidoException;
import com.elotech.repository.PessoaRepository;
import com.elotech.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private Clock clock;

    @InjectMocks
    private PessoaService pessoaService;


    @BeforeEach
    public void before() {
        ;
    }

    @Test
    public void cadastraPessoaComSucesso() {
        PessoaRequestDTO pessoaRequestDTO = criaPessoaRequestDTO();
        PessoaResponseDTO pessoaResponseMock = criaPessoaResponseDTO();
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(criaEntidadePessoa());
        when(clock.instant()).thenReturn(Instant.now());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        PessoaResponseDTO pessoaResponseDTO = pessoaService.criaPessoa(pessoaRequestDTO);

        verify(pessoaRepository).save(any(Pessoa.class));
        verifyNoMoreInteractions(pessoaRepository);

        comparePessoaDtos(pessoaResponseMock, pessoaResponseDTO);
    }

    @Test
    public void consultaPessoaTodos() {
        PessoaResponseDTO pessoaResponseMock = criaPessoaResponseDTO();
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(criaEntidadePessoa());
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<PessoaResponseDTO> pessoasResponseDTO = pessoaService.consultaTodos();

        verify(pessoaRepository).findAll();
        verifyNoMoreInteractions(pessoaRepository);

        comparePessoaDtos(pessoaResponseMock, pessoasResponseDTO.stream().findAny().get());
    }

    @Test
    public void consultaPessoaPorId() {
        Pessoa pessoa = criaEntidadePessoa();
        when(pessoaRepository.findById(eq(pessoa.getId()))).thenReturn(Optional.of(pessoa));

        PessoaResponseDTO pessoasResponseDTO = pessoaService.consultaPorId(pessoa.getId());

        verify(pessoaRepository).findById(eq(pessoa.getId()));
        verifyNoMoreInteractions(pessoaRepository);

        assertEquals(pessoasResponseDTO.getCpf(), pessoa.getCpf());
    }

    @Test
    public void atualizaPessoa() {
        PessoaRequestDTO pessoaRequestDTO = criaPessoaRequestDTO();
        Pessoa pessoa = criaEntidadePessoa();
        when(pessoaRepository.findById(eq(pessoa.getId()))).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        pessoaRequestDTO.setNome("Novo Nome");
        PessoaResponseDTO pessoasResponseDTO = pessoaService.atualizaPessoa(pessoaRequestDTO, pessoa.getId());

        verify(pessoaRepository).findById(eq(pessoa.getId()));
        verifyNoMoreInteractions(pessoaRepository);

        assertEquals(pessoasResponseDTO.getNome(), "Novo Nome");
    }

    @Test
    public void cadastraPessoaCpfInvalido() {
        PessoaRequestDTO pessoaRequestDTO = criaPessoaRequestDTO();
        pessoaRequestDTO.setCpf("13245678915");

        CpfInvalidoException serviceException = assertThrows(CpfInvalidoException.class, () -> pessoaService.criaPessoa(pessoaRequestDTO));

        assertEquals("CPF Inválido", serviceException.getMessage());
    }

   @Test
    public void cadastraPessoaDataNascimentoSuperior() {
        PessoaRequestDTO pessoaRequestDTO = criaPessoaRequestDTO();
        when(clock.instant()).thenReturn(Instant.ofEpochSecond(100000000L));
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        DataNascimentoFuturaException serviceException = assertThrows(DataNascimentoFuturaException.class, () -> pessoaService.criaPessoa(pessoaRequestDTO));

        assertEquals("Data de nascimento superior a data atual", serviceException.getMessage());
    }

    @Test
    public void cadastraPessoaEmailForaDoPadrao() {
        PessoaRequestDTO pessoaRequestDTO = criaPessoaRequestDTO();
        pessoaRequestDTO.getContatos().stream().findFirst().get().setEmail("joaozim#.com.br");
        when(clock.instant()).thenReturn(Instant.now());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        EmailInvalidoException serviceException = assertThrows(EmailInvalidoException.class, () -> pessoaService.criaPessoa(pessoaRequestDTO));

        assertEquals("Email Inválido", serviceException.getMessage());
    }


    private void comparePessoaDtos(PessoaResponseDTO pessoaResponseMock, PessoaResponseDTO pessoaResponseDTO) {
        assertEquals(pessoaResponseMock.getId(),pessoaResponseDTO.getId());
        assertEquals(pessoaResponseMock.getNome(),pessoaResponseDTO.getNome());
        assertEquals(pessoaResponseMock.getCpf(),pessoaResponseDTO.getCpf());
        assertEquals(pessoaResponseMock.getDataNascimento(),pessoaResponseDTO.getDataNascimento());

        assertEquals(pessoaResponseMock.getContatos().size(), pessoaResponseDTO.getContatos().size());
    }

    private PessoaRequestDTO criaPessoaRequestDTO() {
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

    private PessoaResponseDTO criaPessoaResponseDTO() {
        List<ContatoResponseDTO> pessoa = new ArrayList<>();
        pessoa.add(criaContatoResponseDTO());
        return PessoaResponseDTO.builder()
                .id(1L)
                .nome("Jose")
                .cpf("40906378052")
                .dataNascimento(LocalDate.of(2021,05,02))
                .contatos(pessoa)
                .build();
    }

    private ContatoResponseDTO criaContatoResponseDTO() {
        return ContatoResponseDTO.builder()
                .id(2L)
                .nome("Maria")
                .telefone(44999989635L)
                .email("maria@gmail.com")
                .build();
    }

    private Pessoa criaEntidadePessoa() {
        Pessoa pessoa = Pessoa.builder()
                .nome("Jose")
                .cpf("40906378052")
                .dataNascimento(LocalDate.of(2021,05,02))
                .contatos(criaEntidadeContato())
                .build();

        pessoa.setId(1L);
        return pessoa;
    }

    private List<Contato> criaEntidadeContato() {
        List<Contato> contatos = new ArrayList<>();

        Contato contato = Contato.builder()
                .nome("Maria")
                .telefone(44999989635L)
                .email("maria@gmail.com")
                .build();
        contato.setId(2L);
        contatos.add(contato);
        return contatos;
    }


}
