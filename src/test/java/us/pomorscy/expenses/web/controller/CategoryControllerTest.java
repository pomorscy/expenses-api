package us.pomorscy.expenses.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import us.pomorscy.expenses.domain.Category;
import us.pomorscy.expenses.services.CategoryService;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static us.pomorscy.expenses.TestDataHelper.FOOD_CATEGORY;
import static us.pomorscy.expenses.TestDataHelper.SAMPLE_CATEGORIES;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
@AutoConfigureRestDocs("target/generated-snippets")
public class CategoryControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private final FieldDescriptor[] CATEGORY_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("id").description("Id of the category"),
            fieldWithPath("name").description("Name of the category")};

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp(){
        Mockito.reset(categoryService);
    }

    @Test
    public void shouldReturnListOfCategories() throws Exception{
        //given
        when(categoryService.readAll()).thenReturn(SAMPLE_CATEGORIES);
        //when
        mockMvc.perform(get("/categories").accept(MediaType.APPLICATION_JSON_UTF8))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(SAMPLE_CATEGORIES.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(SAMPLE_CATEGORIES.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(SAMPLE_CATEGORIES.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(SAMPLE_CATEGORIES.get(1).getName())))
                //document
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andDo(document("list-categories", responseFields(
                        fieldWithPath("[]").description("Array of categories"))
                        .andWithPrefix("[].", CATEGORY_DESCRIPTOR)));
    }

    @Test
    public void shouldReturnFoundCategoryById() throws Exception{
        //given
        Category expectedCategory = FOOD_CATEGORY;
        when(categoryService.findById(expectedCategory.getId())).thenReturn(Optional.of(expectedCategory));
        //when
        mockMvc.perform(get("/categories/{categoryId}", expectedCategory.getId()).accept(MediaType.APPLICATION_JSON_UTF8))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedCategory.getId())))
                .andExpect(jsonPath("$.name", is(expectedCategory.getName())))
                //document
                .andDo(document("get-category", pathParameters(
                        parameterWithName("categoryId").description("Id of requested category")),
                        responseFields(CATEGORY_DESCRIPTOR)));
    }

    @Test
    public void shouldReturnEmptyWhenCategoryByIdNotFound() throws Exception{
        //given
        when(categoryService.findById(anyString())).thenReturn(Optional.empty());
        //when
        mockMvc.perform(get("/categories/{categoryId}", "randomId").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewCategory() throws Exception{
        //given
        Category newCategory = new Category("", "New Category");
        Category expectedCategory = new Category(newCategory.getName(), UUID.randomUUID().toString());
        String newCategoryJson = objectMapper.writeValueAsString(newCategory);
        when(categoryService.create(newCategory)).thenReturn(Optional.of(expectedCategory));
        //when
        mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON_UTF8).content(newCategoryJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCategory.getId())))
                .andExpect(jsonPath("$.name", is(expectedCategory.getName())))
                //document
                .andDo(document("create-category", requestFields(CATEGORY_DESCRIPTOR), responseFields(CATEGORY_DESCRIPTOR)));
    }

    @Test
    public void shouldNotCreateNewCategoryAndReturnBadRequest() throws Exception{
        //given
        Category newCategory = new Category("New Category");
        String newCategoryJson = objectMapper.writeValueAsString(newCategory);
        when(categoryService.create(newCategory)).thenReturn(Optional.empty());
        //when
        mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON_UTF8).content(newCategoryJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateCategory() throws Exception{
        //given
        Category expectedCategory = new Category(FOOD_CATEGORY.getId(), "New name");
        String newCategoryJson = objectMapper.writeValueAsString(expectedCategory);
        when(categoryService.update(expectedCategory)).thenReturn(Optional.of(expectedCategory));
        //when
        mockMvc.perform(put("/categories").contentType(MediaType.APPLICATION_JSON_UTF8).content(newCategoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedCategory.getId())))
                .andExpect(jsonPath("$.name", is(expectedCategory.getName())))
                //document
                .andDo(document("update-category", requestFields(CATEGORY_DESCRIPTOR), responseFields(CATEGORY_DESCRIPTOR)));
    }

    @Test
    public void shouldNotUpdatedCategoryAndReturnBadRequest() throws Exception{
        //given
        Category expectedCategory = new Category("uuid", "New name");
        String newCategoryJson = objectMapper.writeValueAsString(expectedCategory);
        when(categoryService.update(any(Category.class))).thenReturn(Optional.empty());
        //when
        mockMvc.perform(put("/categories").contentType(MediaType.APPLICATION_JSON_UTF8).content(newCategoryJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteCategory() throws Exception{
        //given
        Category category = FOOD_CATEGORY;
        when(categoryService.delete(category.getId())).thenReturn(true);
        //when
        mockMvc.perform(delete("/categories/{categoryId}", category.getId()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent())
                //document
                .andDo(document("delete-category", pathParameters(
                        parameterWithName("categoryId").description("Id of category to delete"))));
    }

    @Test
    public void shouldNotDeleteCategoryAndReturnBadRequest() throws Exception{
        //given
        Category category = FOOD_CATEGORY;
        when(categoryService.delete(category.getId())).thenReturn(false);
        //when
        mockMvc.perform(delete("/categories/{categoryId}", category.getId()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }
}