package utils.converters;

import ModelFx.CategoryFx;
import database.models.Category;

public class ConverterCategory {
    @SuppressWarnings("Duplicates")
    public static Category convertToCategory(CategoryFx categoryFx)
    {
        Category category = new Category();
        category.setId(categoryFx.getId());
        category.setName(categoryFx.getName());
        return category;
    }
    @SuppressWarnings("Duplicates")
    public static CategoryFx convertToCategoryFx(Category category)
    {
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setName(category.getName());
        return categoryFx;
    }
}
