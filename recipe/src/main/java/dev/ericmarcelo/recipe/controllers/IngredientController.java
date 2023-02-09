package dev.ericmarcelo.recipe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.ericmarcelo.recipe.commands.IngredientCommand;
import dev.ericmarcelo.recipe.commands.RecipeCommand;
import dev.ericmarcelo.recipe.commands.UnitOfMeasureCommand;
import dev.ericmarcelo.recipe.services.IngredientService;
import dev.ericmarcelo.recipe.services.RecipeService;
import dev.ericmarcelo.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;
	
	@Autowired
	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}
	
	@RequestMapping("recipe/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		
		model.addAttribute("ingredient", ingredientCommand);
		
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId,
			@PathVariable String ingredientId,
			Model model) {
		
		this.ingredientService.deleteIngredientById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
	
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting ingredient list for recipe id: " + recipeId);
		
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}
	
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId,
			@PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		
		return "recipe/ingredient/show";
	}
	
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,
			@PathVariable String id, Model model) {
	
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		log.debug("saved recipe id: " + savedIngredientCommand.getRecipeId());
		log.debug("saved ingredient id: " + savedIngredientCommand.getId());
		
		return "redirect:/recipe/" 
		+ savedIngredientCommand.getRecipeId() 
		+ "/ingredient/" 
		+ savedIngredientCommand.getId() 
		+ "/show";
	}

}
