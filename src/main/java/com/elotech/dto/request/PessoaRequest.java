package com.elotech.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaRequest {

    @NotNull
    private PessoaRequestDTO pessoaRequestDTO;
}
