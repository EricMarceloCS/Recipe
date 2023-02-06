package dev.ericmarcelo.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.ericmarcelo.recipe.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

}
