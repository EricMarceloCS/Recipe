package dev.ericmarcelo.recipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ericmarcelo.recipe.commands.RecipeCommand;
import dev.ericmarcelo.recipe.converters.RecipeCommandToRecipe;
import dev.ericmarcelo.recipe.converters.RecipeToRecipeCommand;
import dev.ericmarcelo.recipe.domain.Recipe;
import dev.ericmarcelo.recipe.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I'm in the service");
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().forEach(recipeSet::add);
		return recipeSet;
	}
	


	@Override
	public Recipe findById(Long id) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		
		return recipe.orElseThrow();
	}

	@Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

}
