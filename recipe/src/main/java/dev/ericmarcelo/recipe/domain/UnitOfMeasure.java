package dev.ericmarcelo.recipe.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UnitOfMeasure {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
}
