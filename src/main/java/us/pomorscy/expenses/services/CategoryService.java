package us.pomorscy.expenses.services;

import us.pomorscy.expenses.domain.Category;

import java.util.Optional;

public interface CategoryService{

    Iterable<Category> readAll();

    Optional<Category> create(Category category);

    boolean delete(String categoryId);

    Iterable<Category> findByName(String categoryName);

    Optional<Category> findById(String categoryId);

    Optional<Category> update(Category category);

}
