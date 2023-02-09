package dev.ericmarcelo.recipe.services;

import java.util.Set;

import dev.ericmarcelo.recipe.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	
	public Set<UnitOfMeasureCommand> listAllUoms();

}
