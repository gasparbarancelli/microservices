package com.gasparbarancelli.vendas.api;

import com.gasparbarancelli.vendas.converter.VendaPersistDtoConverter;
import com.gasparbarancelli.vendas.dto.VendaPersistDto;
import com.gasparbarancelli.vendas.event.EmailVendaPersistEvent;
import com.gasparbarancelli.vendas.exception.VendaNotFoundException;
import com.gasparbarancelli.vendas.model.Venda;
import com.gasparbarancelli.vendas.repository.VendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping
public class VendaApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendaApi.class);
    
    private final VendaRepository repository;
    
    private final VendaModelAssembler vendaModelAssembler;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final VendaPersistDtoConverter vendaPersistDtoConverter;

    public VendaApi(
            VendaRepository repository,
            VendaModelAssembler vendaModelAssembler,
            ApplicationEventPublisher applicationEventPublisher, VendaPersistDtoConverter vendaPersistDtoConverter) {
        this.repository = repository;
        this.vendaModelAssembler = vendaModelAssembler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.vendaPersistDtoConverter = vendaPersistDtoConverter;
    }

    @GetMapping
    public CollectionModel<EntityModel<Venda>> all(
            @NonNull @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @NonNull @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        LOGGER.info("Buscando as vendas: size = {}, page = {}", size, page);

        var pageable = PageRequest.of(page, size);

        var vendas = repository.findAll(pageable).stream()
                .map(vendaModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(vendas, linkTo(methodOn(VendaApi.class).all(size, page))
                .withSelfRel());
    }

    @GetMapping("{id}")
    public EntityModel<Venda> one(@NonNull @PathVariable("id") Long id) {
        LOGGER.info("Buscando a venda: id = {}", id);

        var venda = repository.findById(id)
                .orElseThrow(() -> new VendaNotFoundException(id));
        return vendaModelAssembler.toModel(venda);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Venda>> newVenda(
            @NonNull @Valid @RequestBody VendaPersistDto vendaPersist) {
        LOGGER.info("Inserindo a venda: {}", vendaPersist);

        var venda = vendaPersistDtoConverter.toVenda(vendaPersist);
        var entityModel = vendaModelAssembler.toModel(repository.save(venda));

        applicationEventPublisher.publishEvent(new EmailVendaPersistEvent(this, venda));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Venda>> update(
            @NonNull @Valid @RequestBody VendaPersistDto vendaPersist,
            @NonNull @PathVariable Long id) {
        LOGGER.info("Alterando o venda: id = {}, {}", id, vendaPersist);

        var venda = repository.findById(id)
                .orElseThrow(() -> new VendaNotFoundException(id));

        var novaVenda = vendaPersistDtoConverter.toVenda(vendaPersist);
        venda.modify(novaVenda.getItens(), novaVenda.getDesconto(), novaVenda.getEmail());

        var entityModel = vendaModelAssembler.toModel(repository.save(venda));

        applicationEventPublisher.publishEvent(new EmailVendaPersistEvent(this, venda));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@NonNull @PathVariable Long id) {
        LOGGER.info("Excluindo a venda: id = {}", id);

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new VendaNotFoundException(id);
    }

}
