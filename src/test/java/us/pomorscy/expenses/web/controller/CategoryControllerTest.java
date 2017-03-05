package us.pomorscy.expenses.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import us.pomorscy.expenses.services.CategoryService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static us.pomorscy.expenses.TestDataHelper.SAMPLE_CATEGORIES;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
@AutoConfigureRestDocs("target/generated-snippets")
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private FieldDescriptor[] CATEGORY_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("id").description("Id of the category"),
            fieldWithPath("name").description("Name of the category")};

    @Test
    public void shouldReturnListOfCategories() throws Exception{
        //given
        when(categoryService.readAll()).thenReturn(SAMPLE_CATEGORIES);
        //when
        mockMvc.perform(get("/categories").accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(SAMPLE_CATEGORIES.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(SAMPLE_CATEGORIES.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(SAMPLE_CATEGORIES.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(SAMPLE_CATEGORIES.get(1).getName())))
                //document
                .andDo(document("list-categories", responseFields(
                        fieldWithPath("[]").description("Array of categories"))
                        .andWithPrefix("[].", CATEGORY_DESCRIPTOR)));
    }

}