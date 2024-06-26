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
    private String name;
    private String sin;
    private LocalDate birthDate;
    private List<ContatoRequestDTO> contacts = new ArrayList<>();

}
