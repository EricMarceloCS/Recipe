package dev.ericmarcelo.recipe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.ericmarcelo.recipe.domain.Recipe;
import dev.ericmarcelo.recipe.repositories.RecipeRepository;
import dev.ericmarcelo.recipe.services.RecipeServiceImpl;

class RecipeServiceImplTest {

	RecipeServiceImpl recipeServiceImpl;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		this.recipeServiceImpl = new RecipeServiceImpl(recipeRepository, null, null);
	}

	@Test
	void test() {
		assert 2 > 1;
	}
	
	@Test
	public void getRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		
		when(recipeServiceImpl.getRecipes()).thenReturn(recipeData);
		
		Set<Recipe> recipes = recipeServiceImpl.getRecipes();
		
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1));
	}

}
