package dev.ericmarcelo.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.ericmarcelo.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
