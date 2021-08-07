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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;
    private RegraValidator regraValidator = new RegraValidator();

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<PessoaResponseDTO> consultaTodos() {
        List<PessoaResponseDTO> pessoasResponse = new ArrayList<>();

        List<Pessoa> pessoas = pessoaRepository.findAll();
        pessoas.forEach(pessoa -> pessoasResponse.add(entityToPessoaDto(pessoa)));

        return pessoasResponse;

    }

    public PessoaResponseDTO consultaPorId(Long id) {
        final Pessoa pessoa = getPessoaPorId(id);
        return entityToPessoaDto(pessoa);
    }

    @Transactional
    public PessoaResponseDTO criaPessoa(PessoaRequestDTO pessoaRequestDTO) {
        RegraValidator regraValidator = new RegraValidator();
        regraValidator.isCpfValido(pessoaRequestDTO.getCpf());
        regraValidator.isDataNascimentoValida(pessoaRequestDTO.getDataNascimento(), LocalDate.now());
        pessoaRequestDTO.getContatos().forEach(contatoRequestDTO ->
                regraValidator.isEmailValido(contatoRequestDTO.getEmail()));

        final Pessoa pessoa = pessoaDtoToEntity(pessoaRequestDTO, new Pessoa());
        return entityToPessoaDto(pessoaRepository.save(pessoa));
    }

    public PessoaResponseDTO atualizaPessoa(PessoaRequestDTO pessoaRequestDTO, Long id) {
        final Pessoa pessoa = getPessoaPorId(id);
        final Pessoa pessoaAtualizada = atualizaPessoaDtoToEntity(pessoaRequestDTO, pessoa);

        return entityToPessoaDto(pessoaRepository.save(pessoaAtualizada));
    }

    public void deletePessoa(Long id) {
        final Pessoa pessoa = getPessoaPorId(id);
        pessoaRepository.delete(pessoa);
    }

    private PessoaResponseDTO entityToPessoaDto(Pessoa pessoa) {

        List<ContatoResponseDTO> contatos = new ArrayList<>();
        pessoa.getContatos().forEach(contato -> contatos.add(entityToContatoDto(contato)));

       return PessoaResponseDTO.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .cpf(pessoa.getCpf())
                .dataNascimento(pessoa.getDataNascimento())
                .contatos(contatos)
                .build();
    }

    private ContatoResponseDTO entityToContatoDto(Contato contato) {
        return ContatoResponseDTO.builder()
                .id(contato.getId())
                .nome(contato.getNome())
                .telefone(contato.getTelefone())
                .email(contato.getEmail())
                .build();
    }

    private Pessoa pessoaDtoToEntity(PessoaRequestDTO pessoaRequestDTO, Pessoa pessoa) {
            List<Contato> contatos = new ArrayList<>();

            pessoaRequestDTO.getContatos().forEach(contatoRequestDTO -> contatos.add(contatoDtoToEntity(contatoRequestDTO, new Contato())));

            pessoa.setNome(pessoaRequestDTO.getNome());
            pessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());
            pessoa.setCpf(pessoaRequestDTO.getCpf());
            pessoa.setContatos(contatos);

            return pessoa;
    }

    private Contato contatoDtoToEntity(ContatoRequestDTO contatoRequestDTO, Contato contato) {
        contato.setNome(contatoRequestDTO.getNome());
        contato.setEmail(contatoRequestDTO.getEmail());
        contato.setTelefone(contatoRequestDTO.getTelefone());

        return contato;
    }

    private Pessoa atualizaPessoaDtoToEntity(PessoaRequestDTO pessoaRequestDTO, Pessoa pessoa) {
        List<Contato> contatos = new ArrayList<>();

        pessoaRequestDTO.getContatos().forEach(contatoRequestDTO -> contatos.add(contatoDtoToEntity(contatoRequestDTO, new Contato())));
        pessoa.getContatos().addAll(contatos);

        pessoa.setNome(pessoaRequestDTO.getNome()==null?pessoa.getNome():pessoaRequestDTO.getNome());
        pessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento()==null?pessoa.getDataNascimento():pessoaRequestDTO.getDataNascimento());
        pessoa.setCpf(pessoaRequestDTO.getCpf()==null?pessoa.getCpf():pessoaRequestDTO.getCpf());
        pessoa.setContatos(pessoa.getContatos());

        return pessoa;
    }

    private Pessoa getPessoaPorId(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException());
    }


}
