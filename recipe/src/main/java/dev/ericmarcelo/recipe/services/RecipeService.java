package dev.ericmarcelo.recipe.services;

import java.util.Set;

import dev.ericmarcelo.recipe.commands.RecipeCommand;
import dev.ericmarcelo.recipe.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long id);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
}
