package dev.ericmarcelo.recipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ericmarcelo.recipe.domain.Recipe;
import dev.ericmarcelo.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepository;
	
	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
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

}
