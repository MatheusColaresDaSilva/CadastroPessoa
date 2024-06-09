package com.elotech.service;

import com.elotech.business.RegraValidator;
import com.elotech.dto.request.ContatoRequestDTO;
import com.elotech.dto.request.PessoaRequestDTO;
import com.elotech.dto.response.ContatoResponseDTO;
import com.elotech.dto.response.PessoaResponseDTO;
import com.elotech.entity.Contato;
import com.elotech.entity.Pessoa;
import com.elotech.exception.PessoaNaoEncontradaException;
import com.elotech.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;
    private RegraValidator regraValidator = new RegraValidator();
    private final Clock clock;

    public PessoaService(PessoaRepository pessoaRepository,Clock clock) {
        this.pessoaRepository = pessoaRepository;
        this.clock = clock;
    }

    public Page<PessoaResponseDTO> consultaTodos(Pageable page) {
        List<PessoaResponseDTO> pessoasResponse = new ArrayList<>();

        Page<Pessoa> pessoas = pessoaRepository.findAll(page);
        pessoas.forEach(pessoa -> pessoasResponse.add(entityToPessoaDto(pessoa)));

        return  new PageImpl<>(pessoasResponse, page, pessoas.getTotalElements()) ;

    }

    public PessoaResponseDTO consultaPorId(Long id) {
        final Pessoa pessoa = getPessoaPorId(id);
        return entityToPessoaDto(pessoa);
    }

    @Transactional
    public PessoaResponseDTO criaPessoa(PessoaRequestDTO pessoaRequestDTO) {
        RegraValidator regraValidator = new RegraValidator();
        regraValidator.isCpfValido(pessoaRequestDTO.getSin());
        regraValidator.isDataNascimentoValida(pessoaRequestDTO.getBirthDate(), getDataAtual());
        pessoaRequestDTO.getContacts().forEach(contatoRequestDTO ->
                regraValidator.isEmailValido(contatoRequestDTO.getEmail()));

        final Pessoa pessoa = pessoaDtoToEntity(pessoaRequestDTO, new Pessoa());
        return entityToPessoaDto(pessoaRepository.save(pessoa));
    }

    public LocalDate getDataAtual() {
        return LocalDate.now(clock);
    }

    @Transactional
    public PessoaResponseDTO atualizaPessoa(PessoaRequestDTO pessoaRequestDTO, Long id) {
        final Pessoa pessoa = getPessoaPorId(id);
        final Pessoa pessoaAtualizada = atualizaPessoaDtoToEntity(pessoaRequestDTO, pessoa);

        return entityToPessoaDto(pessoaRepository.save(pessoaAtualizada));
    }

    @Transactional
    public void deletePessoa(Long id) {
        final Pessoa pessoa = getPessoaPorId(id);
        pessoaRepository.delete(pessoa);
    }

    private PessoaResponseDTO entityToPessoaDto(Pessoa pessoa) {

        List<ContatoResponseDTO> contatos = new ArrayList<>();
        pessoa.getContatos().forEach(contato -> contatos.add(entityToContatoDto(contato)));

       return PessoaResponseDTO.builder()
                .id(pessoa.getId())
                .name(pessoa.getNome())
                .sin(pessoa.getCpf())
                .birthDate(pessoa.getDataNascimento())
                .contacts(contatos)
                .build();
    }

    private ContatoResponseDTO entityToContatoDto(Contato contato) {
        return ContatoResponseDTO.builder()
                .id(contato.getId())
                .nome(contato.getName())
                .telefone(contato.getPhone())
                .email(contato.getEmail())
                .build();
    }

    private Pessoa pessoaDtoToEntity(PessoaRequestDTO pessoaRequestDTO, Pessoa pessoa) {
            List<Contato> contatos = new ArrayList<>();

            pessoaRequestDTO.getContacts().forEach(contatoRequestDTO -> contatos.add(contatoDtoToEntity(contatoRequestDTO, new Contato())));

            pessoa.setNome(pessoaRequestDTO.getName());
            pessoa.setDataNascimento(pessoaRequestDTO.getBirthDate());
            pessoa.setCpf(pessoaRequestDTO.getSin());
            pessoa.setContatos(contatos);

            return pessoa;
    }

    private Contato contatoDtoToEntity(ContatoRequestDTO contatoRequestDTO, Contato contato) {
        contato.setName(contatoRequestDTO.getName());
        contato.setEmail(contatoRequestDTO.getEmail());
        contato.setPhone(contatoRequestDTO.getPhone());

        return contato;
    }

    private Pessoa atualizaPessoaDtoToEntity(PessoaRequestDTO pessoaRequestDTO, Pessoa pessoa) {
        List<Contato> contatos = new ArrayList<>();

        pessoaRequestDTO.getContacts().forEach(contatoRequestDTO -> contatos.add(contatoDtoToEntity(contatoRequestDTO, new Contato())));
        pessoa.getContatos().addAll(contatos);

        pessoa.setNome(pessoaRequestDTO.getName()==null?pessoa.getNome():pessoaRequestDTO.getName());
        pessoa.setDataNascimento(pessoaRequestDTO.getBirthDate()==null?pessoa.getDataNascimento():pessoaRequestDTO.getBirthDate());
        pessoa.setCpf(pessoaRequestDTO.getSin()==null?pessoa.getCpf():pessoaRequestDTO.getSin());
        pessoa.setContatos(pessoa.getContatos());

        return pessoa;
    }

    private Pessoa getPessoaPorId(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException());
    }


}
