package com.elotech.service;

import com.elotech.business.RegraValidator;
import com.elotech.dto.response.ContatoResponseDTO;
import com.elotech.dto.response.PessoaResponseDTO;
import com.elotech.entity.Contato;
import com.elotech.entity.Pessoa;
import com.elotech.exception.PessoaNaoEncontradaException;
import com.elotech.repository.PessoaRepository;
import org.springframework.stereotype.Service;

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

    private Pessoa getPessoaPorId(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException());
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
                //.pessoa(contato.getPessoa().getId())
                .build();
    }

}
