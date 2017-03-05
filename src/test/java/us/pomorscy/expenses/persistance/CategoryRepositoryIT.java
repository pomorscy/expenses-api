package us.pomorscy.expenses.persistance;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import us.pomorscy.expenses.ExpensesApplication;
import us.pomorscy.expenses.domain.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExpensesApplication.class)
@DatabaseSetup(CategoryRepositoryIT.DATASET)
@DirtiesContext
@Rollback
@Transactional
public class CategoryRepositoryIT{

    private static final String UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    static final String DATASET = "classpath:datasets/categories.xml";

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldFindAllCategories(){
        //when
        Iterable<Category> actualCategories = categoryRepository.findAll();
        //then
        assertThat(actualCategories).hasSize(3);
    }

    @Test
    public void shouldFindCategoryByNameContaining(){
        //given
        Category expectedCategory = new Category("26a70b69-d8fd-4a16-9099-ef939392f2cc", "Food");
        //when
        List<Category> actualCategories = categoryRepository.findByNameIgnoreCaseContaining(expectedCategory.getName());
        //then
        assertThat(actualCategories)
                .hasSize(1)
                .contains(expectedCategory);
    }

    @Test
    public void shouldFindCategoryByNameContainingShouldIgnoreCase(){
        //when
        List<Category> actualCategories = categoryRepository.findByNameIgnoreCaseContaining("O");
        //then
        assertThat(actualCategories)
                .hasSize(2);
    }

    @Test
    public void shouldReturnEmptyIterableWhenCategoryNotFoundByName(){
        //given
        String name = "Nonexistent name";
        //when
        List<Category> actualCategories = categoryRepository.findByNameIgnoreCaseContaining(name);
        //then
        assertThat(actualCategories).isEmpty();
    }

    @Test
    public void shouldSaveCategoryWithName(){
        //given
        String expectedName = "Trip";
        //when
        Category actualCategory = categoryRepository.save(new Category(expectedName));
        //then
        assertThat(actualCategory.getName()).isEqualTo(expectedName);
        assertThat(actualCategory.getId().matches(UUID_PATTERN)).isTrue();
    }

    @Test
    public void shouldDeleteCategoryById(){
        //given
        String categoryId = "26a70b69-d8fd-4a16-9099-ef939392f2cc";
        //when
        categoryRepository.delete(categoryId);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowEmptyResultDataAccessExceptionWhenCategoryDoesNotExist(){
        //given
        String categoryId = "wrong id";
        //when
        categoryRepository.delete(categoryId);
    }
}