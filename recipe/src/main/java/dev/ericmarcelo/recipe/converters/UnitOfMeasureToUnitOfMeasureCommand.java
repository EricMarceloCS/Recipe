package dev.ericmarcelo.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import dev.ericmarcelo.recipe.commands.UnitOfMeasureCommand;
import dev.ericmarcelo.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
 	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if (source != null) {
            final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
            uomc.setId(source.getId());
            uomc.setDescription(source.getDescription());
            return uomc;
        }
		return null;
	}
}