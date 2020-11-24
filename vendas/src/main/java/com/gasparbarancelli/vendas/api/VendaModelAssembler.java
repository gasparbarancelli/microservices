package com.gasparbarancelli.vendas.api;

import com.gasparbarancelli.vendas.model.Venda;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VendaModelAssembler implements RepresentationModelAssembler<Venda, EntityModel<Venda>> {

    @Override
    public EntityModel<Venda> toModel(Venda venda) {
        return EntityModel.of(venda,
                linkTo(methodOn(VendaApi.class).one(venda.getId())).withSelfRel(),
                linkTo(methodOn(VendaApi.class).all(null, null)).withRel("vendas"));
    }

}
