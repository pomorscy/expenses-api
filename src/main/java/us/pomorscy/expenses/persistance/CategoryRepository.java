package us.pomorscy.expenses.persistance;

import org.springframework.data.repository.CrudRepository;
import us.pomorscy.expenses.domain.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, String>{

    List<Category> findByNameIgnoreCaseContaining(String name);

}
