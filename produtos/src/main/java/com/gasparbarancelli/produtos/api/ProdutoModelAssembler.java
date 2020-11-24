package com.gasparbarancelli.produtos.api;

import com.gasparbarancelli.produtos.api.ProdutoApi;
import com.gasparbarancelli.produtos.model.Produto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProdutoModelAssembler implements RepresentationModelAssembler<Produto, EntityModel<Produto>> {

    @Override
    public EntityModel<Produto> toModel(Produto produto) {
        return EntityModel.of(produto,
                linkTo(methodOn(ProdutoApi.class).one(produto.getId())).withSelfRel(),
                linkTo(methodOn(ProdutoApi.class).all(null, null)).withRel("produtos"));
    }

}
