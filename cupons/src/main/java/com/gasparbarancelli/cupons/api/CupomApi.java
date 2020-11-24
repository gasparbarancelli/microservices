package com.gasparbarancelli.cupons.api;

import com.gasparbarancelli.cupons.exception.CupomNotFoundException;
import com.gasparbarancelli.cupons.model.Cupom;
import com.gasparbarancelli.cupons.repository.CupomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping
public class CupomApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(CupomApi.class);

    private final CupomRepository repository;

    public CupomApi(CupomRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    public BigDecimal one(@NonNull @PathVariable("id") String id) {
        LOGGER.info("Buscando o cupom: id = {}", id);

        var cupom = repository.findById(id)
                .orElseThrow(() -> new CupomNotFoundException(id));

        return cupom.getDesconto();
    }

    @PostMapping
    public ResponseEntity<Cupom> newCupom(
            @NonNull @RequestParam("desconto") BigDecimal desconto) {
        LOGGER.info("Inserindo um novo cupom: desconto = {}", desconto);

        var cupom = new Cupom(desconto);
        repository.save(cupom);

        return ResponseEntity.ok(cupom);
    }

}
