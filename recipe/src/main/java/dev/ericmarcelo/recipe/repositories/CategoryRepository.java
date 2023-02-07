package dev.ericmarcelo.recipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.ericmarcelo.recipe.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String description);
}
