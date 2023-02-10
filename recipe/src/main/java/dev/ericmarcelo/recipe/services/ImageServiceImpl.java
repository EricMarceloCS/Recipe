package dev.ericmarcelo.recipe.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.ericmarcelo.recipe.commands.RecipeCommand;
import dev.ericmarcelo.recipe.converters.RecipeToRecipeCommand;
import dev.ericmarcelo.recipe.domain.Recipe;
import dev.ericmarcelo.recipe.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
	
	private final RecipeRepository recipeRepository;
	private final RecipeService recipeService;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Autowired
	public ImageServiceImpl(RecipeRepository recipeRepository, RecipeService recipeService, RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeService = recipeService;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}



	@Override
	@Transactional
	public void saveImageFile(Long recipeId, MultipartFile file) {
		
		try {
			Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
			//RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
			Byte[] byteObject = new Byte[file.getBytes().length];
			
			int i = 0;
			for(byte b : file.getBytes()) {
				byteObject[i++] = b;
			}
			recipe.setImage(byteObject);
			recipeRepository.save(recipe);
			recipeToRecipeCommand.convert(recipe);
		} catch (IOException ioe){
			log.error("Error occurred" + ioe.getMessage());
			ioe.printStackTrace();
		}
		
		
	}

}
