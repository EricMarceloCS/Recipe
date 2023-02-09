package dev.ericmarcelo.recipe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.ericmarcelo.recipe.commands.RecipeCommand;
import dev.ericmarcelo.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {
	
	private final RecipeService recipeService;
	
	@Autowired
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		return "/recipe/show";
	}
	
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/recipeform";
	}
	
	@PostMapping
	@RequestMapping({"recipe", "/recipe/", "/recipe"})
	public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand, Model model) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
		model.addAttribute("recipe", recipeService.findById(savedCommand.getId()));
		return "recipe/show";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		log.debug("Deleteing id: " + id);
		recipeService.deleteById(Long.valueOf(id));
		
		return "redirect:/";
	}

}
