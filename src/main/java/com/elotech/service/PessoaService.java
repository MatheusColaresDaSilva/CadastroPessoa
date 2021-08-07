package com.elotech.service;

import com.elotech.dto.response.ContatoResponseDTO;
import com.elotech.dto.response.PessoaResponseDTO;
import com.elotech.entity.Contato;
import com.elotech.entity.Pessoa;
import com.elotech.exception.PessoaNaoEncontradaException;
import com.elotech.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;

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

    public Boolean isCpfValido(String cpf) {

        Pattern p = Pattern.compile("^\\d{11}$");
        Matcher m = p.matcher(cpf);
        if(!m.find()) {
            return false;
        }

        int soma = 0;
        int resto;

        if (cpf == "00000000000") {
            return false;
        }

        for (int i=1; i<=9; i++) {
            soma = soma + Integer.parseInt(cpf.substring(i-1, i)) * (11 - i);
        }
        resto = (soma * 10) % 11;
        if ((resto == 10) || (resto == 11)) {
            resto = 0;
        }
        if (resto != Integer.parseInt(cpf.substring(9, 10)) ) {
            return false;
        }

        soma = 0;

        for (int i = 1; i <= 10; i++) {
            soma = soma + Integer.parseInt(cpf.substring(i-1, i)) * (12 - i);
        }
        resto = (soma * 10) % 11;
        if ((resto == 10) || (resto == 11)) {
            resto = 0;
        }
        if (resto != Integer.parseInt(cpf.substring(10, 11) ) ) {
            return false;
        }

        return true;
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
