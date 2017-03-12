package us.pomorscy.expenses.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.pomorscy.expenses.domain.Category;
import us.pomorscy.expenses.services.CategoryService;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController{

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> listAll(){
        return categoryService.readAll();
    }

    @RequestMapping(path = "/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") String categoryId){
        Optional<Category> category = categoryService.findById(categoryId);
        if (category.isPresent()){
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Category> add(@RequestBody Category category){
        Optional<Category> createdCategory = categoryService.create(category);
        if (createdCategory.isPresent()){
            return new ResponseEntity<>(createdCategory.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Category> edit(@RequestBody Category category){
        Optional<Category> updatedCategory = categoryService.update(category);
        if (updatedCategory.isPresent()){
            return new ResponseEntity<>(updatedCategory.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> delete(@PathVariable("categoryId") String categoryId){
        boolean isDeleted = categoryService.delete(categoryId);
        if (isDeleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
