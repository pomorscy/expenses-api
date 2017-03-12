package us.pomorscy.expenses.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.pomorscy.expenses.domain.Category;
import us.pomorscy.expenses.persistance.CategoryRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> readAll(){
        try{
            return categoryRepository.findAll();
        } catch (Exception ex){
            logger.error("Failed to find categories.", ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Category> create(Category category){
        try{
            return Optional.of(categoryRepository.save(category));
        } catch (Exception ex){
            logger.error("Failed to save category: " + category, ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(String categoryId){
        try{
            categoryRepository.delete(categoryId);
        } catch (Exception ex){
            logger.error("Failed to delete category by id: " + categoryId, ex);
            return false;
        }
        return true;
    }

    @Override
    public Iterable<Category> findByName(String categoryName){
        try{
            return categoryRepository.findByNameIgnoreCaseContaining(categoryName);
        } catch (Exception ex){
            logger.error("Failed to find category by name: " + categoryName, ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Category> findById(String categoryId){
        Category category;
        try{
            category = categoryRepository.findOne(categoryId);
            if (category != null){
                return Optional.of(category);
            }
        } catch (Exception ex){
            logger.error("Failed to find category by id: " + categoryId, ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> update(Category category){
        try{
            Category oldCategory = categoryRepository.findOne(category.getId());
            if (oldCategory != null){
                return Optional.of(categoryRepository.save(category));
            }
        } catch (Exception ex){
            logger.error("Failed to update category: " + category, ex);
        }
        return Optional.empty();
    }
}
