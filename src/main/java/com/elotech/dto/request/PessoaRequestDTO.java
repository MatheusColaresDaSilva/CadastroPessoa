package com.elotech.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaRequestDTO {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private List<ContatoRequestDTO> contatos = new ArrayList<>();

}
