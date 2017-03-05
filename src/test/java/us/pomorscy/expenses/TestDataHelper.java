package us.pomorscy.expenses;

import com.google.common.collect.ImmutableList;
import us.pomorscy.expenses.domain.Category;

import java.util.List;
import java.util.UUID;

public class TestDataHelper{

    private static final Category FOOD_CATEGORY = new Category(UUID.randomUUID().toString(), "Food");
    private static final Category COOL_CATEGORY = new Category(UUID.randomUUID().toString(), "Cool");
    public static final List<Category> SAMPLE_CATEGORIES = ImmutableList.of(FOOD_CATEGORY, COOL_CATEGORY);

}