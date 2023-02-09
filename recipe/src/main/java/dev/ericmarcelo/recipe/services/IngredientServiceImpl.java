package dev.ericmarcelo.recipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ericmarcelo.recipe.commands.IngredientCommand;
import dev.ericmarcelo.recipe.converters.IngredientCommandToIngredient;
import dev.ericmarcelo.recipe.converters.IngredientToIngredientCommand;
import dev.ericmarcelo.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dev.ericmarcelo.recipe.domain.Ingredient;
import dev.ericmarcelo.recipe.domain.Recipe;
import dev.ericmarcelo.recipe.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
	
	@Autowired
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			RecipeRepository recipeRepository,
			UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		super();
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
	}



	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
		
		Optional<Recipe> optionalRecipe = this.recipeRepository.findById(recipeId);
		
		Recipe recipe = optionalRecipe.orElseThrow();
		
		Optional<IngredientCommand> optionalIngredientCommand = 
				recipe.getIngredients()
				.stream()
				.filter(i -> i.getId() == id)
				.map(ingredientToIngredientCommand::convert)
				.findAny();
		
		return optionalIngredientCommand.orElseThrow();
	}



	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());
		Recipe savedRecipe = optionalRecipe.orElseThrow();
		
		Optional<Ingredient> optionalIngredient = savedRecipe.getIngredients()
		.stream()
		.filter(i -> i.getId() == ingredientCommand.getId())
		.findFirst();
		
		if(optionalIngredient.isPresent()) {
			Ingredient savedIngredient = optionalIngredient.orElseThrow();
			savedIngredient.setAmount(ingredientCommand.getAmount());
			savedIngredient.setDescription(ingredientCommand.getDescription());
			savedIngredient.setUom(unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUom()));
		} else {
			Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
			ingredient.setRecipe(savedRecipe);
			savedRecipe.addIngredient(ingredient);
		}
		
		savedRecipe = recipeRepository.save(savedRecipe);
		
		Optional<Ingredient> savedIngredientOptional = savedRecipe
				.getIngredients()
				.stream()
				.filter(i -> i.getId() == ingredientCommand.getId())
				.findFirst();
		
		if(savedIngredientOptional.isEmpty()) {
			savedIngredientOptional = savedRecipe
					.getIngredients()
					.stream()
					.filter(i -> i.getDescription().equals(ingredientCommand.getDescription()))
					.filter(i -> i.getAmount().equals(ingredientCommand.getAmount()))
					.filter(i -> i.getUom().getId().equals(ingredientCommand.getUom().getId()))
					.findAny();
		}
		
		
		return ingredientToIngredientCommand.convert(savedIngredientOptional.orElseThrow());
	}



	@Override
	public void deleteIngredientById(Long recipeId, Long ingredientId) {
		
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		
		if(optionalRecipe.isPresent()) {
			Recipe recipe = optionalRecipe.get();
			
			Optional<Ingredient> optionalIngredient = recipe.getIngredients()
					.stream()
					.filter(i -> i.getId() == ingredientId)
					.findAny();
				
			if(optionalIngredient.isPresent()) {
				Ingredient ingredientToDelete = optionalIngredient.orElseThrow();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(ingredientToDelete);
				recipeRepository.save(recipe);
			} 
		} else {
			log.debug("Recipe not found: " + recipeId);
		}
	}

}
