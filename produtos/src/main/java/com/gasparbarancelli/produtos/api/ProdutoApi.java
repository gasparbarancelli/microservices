package com.gasparbarancelli.produtos.api;

import com.gasparbarancelli.produtos.dto.ProdutoPersistDto;
import com.gasparbarancelli.produtos.dto.ProdutoUpdateDto;
import com.gasparbarancelli.produtos.event.ProdutoPersistEvent;
import com.gasparbarancelli.produtos.model.Produto;
import com.gasparbarancelli.produtos.exception.ProdutoNotFoundException;
import com.gasparbarancelli.produtos.repository.ProdutoRepository;
import com.gasparbarancelli.produtos.util.JsonUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping
public class ProdutoApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoApi.class);

    private final ProdutoRepository repository;

    private final ProdutoModelAssembler produtoModelAssembler;

    private final ModelMapper modelMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    public ProdutoApi(
            ProdutoRepository repository,
            ProdutoModelAssembler produtoModelAssembler,
            ModelMapper modelMapper,
            ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.produtoModelAssembler = produtoModelAssembler;
        this.modelMapper = modelMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public CollectionModel<EntityModel<Produto>> all(
            @NonNull @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @NonNull @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        LOGGER.info("Buscando os produtos: size = {}, page = {}", size, page);

        var pageable = PageRequest.of(page, size);

        var produtos = repository.findAll(pageable).stream()
                .map(produtoModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(produtos, linkTo(methodOn(ProdutoApi.class).all(size, page))
                .withSelfRel());
    }

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping("{id}")
    public EntityModel<Produto> one(@NonNull @PathVariable("id") Long id) {
        LOGGER.info("Buscando o produto: id = {}", id);

        var produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        return produtoModelAssembler.toModel(produto);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Produto>> newProduto(@NonNull @Valid @RequestBody ProdutoPersistDto produtoPersist) {
        LOGGER.info("Inserindo o produto: {}", produtoPersist);

        var produto = modelMapper.map(produtoPersist, Produto.class);
        var entityModel = produtoModelAssembler.toModel(repository.save(produto));

        applicationEventPublisher.publishEvent(new ProdutoPersistEvent(this, produto));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Produto>> update(
            @NonNull @Valid @RequestBody ProdutoPersistDto produtoPersist,
            @NonNull @PathVariable Long id) {
        LOGGER.info("Alterando o produto: id = {}, {}", id, produtoPersist);

        var produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        modelMapper.map(produtoPersist, produto);

        var entityModel = produtoModelAssembler.toModel(repository.save(produto));

        applicationEventPublisher.publishEvent(new ProdutoPersistEvent(this, produto));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@NonNull @PathVariable Long id) {
        LOGGER.info("Excluindo o produto: id = {}", id);

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new ProdutoNotFoundException(id);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateProduto(
            @NonNull @PathVariable("id") Long id,
            @Valid @RequestBody ProdutoUpdateDto produtoUpdateDto) {
        LOGGER.info("Alerando o produto: id = {}, {}", id, produtoUpdateDto);

        var produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        JsonUtil.changeIfPresent(produtoUpdateDto.getDescricao(), produto::setDescricao);
        JsonUtil.changeIfPresent(produtoUpdateDto.getValor(), produto::setValor);

        var entityModel = produtoModelAssembler.toModel(repository.save(produto));

        return ResponseEntity.ok(entityModel);
    }

}
