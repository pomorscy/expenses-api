package us.pomorscy.expenses.services;

import org.junit.Before;
import org.junit.Test;
import us.pomorscy.expenses.domain.Category;
import us.pomorscy.expenses.persistance.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static us.pomorscy.expenses.TestDataHelper.SAMPLE_CATEGORIES;

public class CategoryServiceImplTest{

    private CategoryRepository categoryRepositoryMock;

    private CategoryService categoryService;

    @Before
    public void setUp(){
        categoryRepositoryMock = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepositoryMock);
    }

    @Test
    public void shouldUseCategoryRepositoryFindAllOnly(){
        //when
        categoryService.readAll();
        //then
        verify(categoryRepositoryMock).findAll();
        verifyNoMoreInteractions(categoryRepositoryMock);
    }

    @Test
    public void shouldReadAndReturnAllCategories(){
        //given
        List<Category> expectedCategories = SAMPLE_CATEGORIES;
        when(categoryRepositoryMock.findAll()).thenReturn(expectedCategories);
        //when
        Iterable<Category> actualCategories = categoryService.readAll();
        //then
        assertThat(actualCategories).hasSize(expectedCategories.size());
        assertThat(actualCategories).containsAll(expectedCategories);
    }

    @Test
    public void shouldReturnEmptyIterableWhenFindAllFailed(){
        //given
        doThrow(new RuntimeException()).when(categoryRepositoryMock).findAll();
        //when
        Iterable<Category> actualCategories = categoryService.readAll();
        //then
        assertThat(actualCategories).isEmpty();
    }

    @Test
    public void shouldCreateAndReturnCategory(){
        //given
        Category expectedCategory = new Category(UUID.randomUUID().toString(), "Pleasure");
        when(categoryRepositoryMock.save(any(Category.class))).thenReturn(expectedCategory);
        //when
        Optional<Category> actualCategory = categoryService.create(new Category(expectedCategory.getName()));
        //then
        assertThat(actualCategory.isPresent()).isTrue();
        assertThat(actualCategory.get()).isEqualTo(expectedCategory);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenSaveFailed(){
        //given
        when(categoryRepositoryMock.save(any(Category.class))).thenThrow(new RuntimeException());
        //when
        Optional<Category> actualCategory = categoryService.create(new Category("Broken"));
        //then
        assertThat(actualCategory.isPresent()).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenDeleteSucceeded(){
        //given
        String categoryId = "a086fa80-8185-4023-96d1-848ce3386747";
        //when
        boolean actualDelete = categoryService.delete(categoryId);
        //then
        assertThat(actualDelete).isTrue();
    }

    @Test
    public void shouldReturnTrueWhenDeleteFailed(){
        //given
        String categoryId = "a086fa80-8185-4023-96d1-848ce3386747";
        doThrow(new RuntimeException()).when(categoryRepositoryMock).delete(categoryId);
        //when
        boolean actualDelete = categoryService.delete(categoryId);
        //then
        assertThat(actualDelete).isFalse();
    }

    @Test
    public void shouldReturnIterableOfFoundCategories(){
        //given
        List<Category> expectedCategories = SAMPLE_CATEGORIES;
        when(categoryRepositoryMock.findByNameIgnoreCaseContaining("oo")).thenReturn(expectedCategories);
        //when
        Iterable<Category> actualCategories = categoryService.findByName("oo");
        //then
        assertThat(actualCategories).hasSize(expectedCategories.size());
        assertThat(actualCategories).containsAll(actualCategories);
    }

    @Test
    public void shouldReturnEmptyIterableWhenFindByNameFailed(){
        //given
        doThrow(new RuntimeException()).when(categoryRepositoryMock).findByNameIgnoreCaseContaining(anyString());
        //when
        Iterable<Category> actualCategories = categoryService.findByName("Food");
        //then
        assertThat(actualCategories).isEmpty();
    }

}