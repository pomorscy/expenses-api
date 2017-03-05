package us.pomorscy.expenses.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import us.pomorscy.expenses.domain.Category;
import us.pomorscy.expenses.services.CategoryService;

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
}
