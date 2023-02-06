package dev.ericmarcelo.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.ericmarcelo.recipe.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
