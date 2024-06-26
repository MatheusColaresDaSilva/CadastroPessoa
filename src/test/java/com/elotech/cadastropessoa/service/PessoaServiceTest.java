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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

        Pageable pageable = PageRequest.of(0, 8);
        Page<PessoaResponseDTO> pessoasResponseDTO = pessoaService.consultaTodos(pageable);

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

        assertEquals(pessoasResponseDTO.getSin(), pessoa.getCpf());
    }

    @Test
    public void atualizaPessoa() {
        PessoaRequestDTO pessoaRequestDTO = criaPessoaRequestDTO();
        Pessoa pessoa = criaEntidadePessoa();
        when(pessoaRepository.findById(eq(pessoa.getId()))).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        pessoaRequestDTO.setName("Novo Nome");
        PessoaResponseDTO pessoasResponseDTO = pessoaService.atualizaPessoa(pessoaRequestDTO, pessoa.getId());

        verify(pessoaRepository).findById(eq(pessoa.getId()));
        verifyNoMoreInteractions(pessoaRepository);

        assertEquals(pessoasResponseDTO.getName(), "Novo Nome");
    }

    @Test
    public void cadastraPessoaCpfInvalido() {
        PessoaRequestDTO pessoaRequestDTO = criaPessoaRequestDTO();
        pessoaRequestDTO.setSin("13245678915");

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
        pessoaRequestDTO.getContacts().stream().findFirst().get().setEmail("joaozim#.com.br");
        when(clock.instant()).thenReturn(Instant.now());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        EmailInvalidoException serviceException = assertThrows(EmailInvalidoException.class, () -> pessoaService.criaPessoa(pessoaRequestDTO));

        assertEquals("Email Inválido", serviceException.getMessage());
    }


    private void comparePessoaDtos(PessoaResponseDTO pessoaResponseMock, PessoaResponseDTO pessoaResponseDTO) {
        assertEquals(pessoaResponseMock.getId(),pessoaResponseDTO.getId());
        assertEquals(pessoaResponseMock.getName(),pessoaResponseDTO.getName());
        assertEquals(pessoaResponseMock.getSin(),pessoaResponseDTO.getSin());
        assertEquals(pessoaResponseMock.getBirthDate(),pessoaResponseDTO.getBirthDate());

        assertEquals(pessoaResponseMock.getContacts().size(), pessoaResponseDTO.getContacts().size());
    }

    private PessoaRequestDTO criaPessoaRequestDTO() {
        List<ContatoRequestDTO> contatos = new ArrayList<>();
        contatos.add(criaContatoRequestDTO());
        return PessoaRequestDTO.builder()
                .name("Jose")
                .sin("40906378052")
                .birthDate(LocalDate.of(2021,05,02))
                .contacts(contatos)
                .build();
    }

    private ContatoRequestDTO criaContatoRequestDTO() {
        return ContatoRequestDTO.builder()
                .name("Maria")
                .phone(44999989635L)
                .email("maria@gmail.com")
                .build();
    }

    private PessoaResponseDTO criaPessoaResponseDTO() {
        List<ContatoResponseDTO> pessoa = new ArrayList<>();
        pessoa.add(criaContatoResponseDTO());
        return PessoaResponseDTO.builder()
                .id(1L)
                .name("Jose")
                .sin("40906378052")
                .birthDate(LocalDate.of(2021,05,02))
                .contacts(pessoa)
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
                .name("Maria")
                .phone(44999989635L)
                .email("maria@gmail.com")
                .build();
        contato.setId(2L);
        contatos.add(contato);
        return contatos;
    }


}
