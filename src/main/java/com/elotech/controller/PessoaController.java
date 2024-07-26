package com.elotech.controller;

import com.elotech.dto.request.PessoaRequest;
import com.elotech.dto.request.PessoaRequestDTO;
import com.elotech.dto.response.PessoaResponseDTO;
import com.elotech.dto.response.ResponseDTO;
import com.elotech.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
public class PessoaController extends BaseController{

    private PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PessoaResponseDTO>> consultaPorId(@PathVariable Long id) {
        PessoaResponseDTO response = pessoaService.consultaPorId(id);
        return ResponseEntity.ok(new ResponseDTO<>(response));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<PessoaResponseDTO>>> consultaTodos(Pageable page) {
        Page<PessoaResponseDTO> response = pessoaService.consultaTodos(page);
        return ResponseEntity.ok(new ResponseDTO<>(response));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<ResponseDTO<PessoaResponseDTO>> criaPessoa(@RequestBody PessoaRequestDTO pessoaRequestDTO) {
        PessoaResponseDTO response = pessoaService.criaPessoa(pessoaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO<>(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PessoaResponseDTO>> atualizaPessoa(@RequestBody PessoaRequestDTO pessoaRequestDTO, @PathVariable Long id) {
        PessoaResponseDTO response = pessoaService.atualizaPessoa(pessoaRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePessoa(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
