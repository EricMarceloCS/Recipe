package dev.ericmarcelo.recipe.services;

import java.util.Set;

import dev.ericmarcelo.recipe.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();
}
