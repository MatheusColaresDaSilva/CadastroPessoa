package com.elotech.cadastropessoa;

import com.elotech.entity.Contato;
import com.elotech.entity.Pessoa;
import com.elotech.repository.ContatoRepository;
import com.elotech.repository.PessoaRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class AppStartRunner implements ApplicationRunner {

    private PessoaRepository pessoaRepository;
    private ContatoRepository contatoRepository;


    public AppStartRunner(PessoaRepository pessoaRepository,ContatoRepository contatoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.contatoRepository = contatoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Nois");


        Pessoa pessoa1 = new Pessoa();

        pessoa1.setNome("Matheus Colares");
        pessoa1.setCpf("04789089307");
        pessoa1.setDataNascimento(LocalDate.of(2021,01,10));

        Contato contato1 = new Contato();
        contato1.setNome("Danielle");
        contato1.setEmail("nnnnn@gmail.com");
        contato1.setTelefone(999999999999L);
        //contato1.setPessoa(pessoa1);

        //contatoRepository.save(contato2);

        pessoa1.setContatos(Arrays.asList(contato1));
        pessoaRepository.save(pessoa1);
    }
}
