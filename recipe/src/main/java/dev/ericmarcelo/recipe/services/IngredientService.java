package dev.ericmarcelo.recipe.services;

import dev.ericmarcelo.recipe.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
	
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	
	void deleteIngredientById(Long recipeId, Long ingredientId);
}
