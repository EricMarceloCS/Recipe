package dev.ericmarcelo.recipe.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ericmarcelo.recipe.commands.UnitOfMeasureCommand;
import dev.ericmarcelo.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.ericmarcelo.recipe.domain.UnitOfMeasure;
import dev.ericmarcelo.recipe.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	
	@Autowired
	public UnitOfMeasureServiceImpl(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureCommand;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}



	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		Set<UnitOfMeasureCommand> uomSet = new HashSet<>();
		Iterator<UnitOfMeasure> it  = unitOfMeasureRepository.findAll().iterator();
			
		while(it.hasNext()) {
			uomSet.add(unitOfMeasureToUnitOfMeasureCommand.convert(it.next()));
		}
		
		return uomSet;
	}

}
