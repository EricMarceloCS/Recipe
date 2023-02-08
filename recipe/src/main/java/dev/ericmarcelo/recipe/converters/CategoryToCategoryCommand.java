package dev.ericmarcelo.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import dev.ericmarcelo.recipe.commands.CategoryCommand;
import dev.ericmarcelo.recipe.domain.Category;
import lombok.Synchronized;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

	@Override
	@Synchronized
	@Nullable
	public CategoryCommand convert(Category source) {
		if(source == null) {
			return null;
		}
		
		final CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(source.getId());
		categoryCommand.setDescription(source.getDescription());
		
		return categoryCommand;
	}

}
