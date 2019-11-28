package utils.converters;

import ModelFx.BuiltItemFx;
import database.models.BuiltItems;
import javafx.beans.property.ObjectProperty;

public class ConverterBuiltItems {

    @SuppressWarnings("Duplicates")
    public static BuiltItems convertToBuiltItems(BuiltItemFx builtItemFx)
    {
        BuiltItems builtItems = new BuiltItems();
        builtItems.setId(builtItemFx.getId());
        builtItems.setName(builtItemFx.getName());
        builtItems.setPrice(Double.parseDouble(builtItemFx.getPrice()));
        builtItems.setStock(Integer.parseInt(builtItemFx.getStock()));
        builtItems.setCategory(ConverterCategory.convertToCategory(builtItemFx.getCategoryFx()));

        return builtItems;
    }
    @SuppressWarnings("Duplicates")
    public static BuiltItemFx convertToBuiltItemsFx(BuiltItems builtItems)
    {
        BuiltItemFx builtItemFx = new BuiltItemFx();
        builtItemFx.setId(builtItems.getId());
        builtItemFx.setName(builtItems.getName());
        builtItemFx.setPrice(String.valueOf(builtItems.getPrice()));
        builtItemFx.setStock(String.valueOf(builtItems.getStock()));
        builtItemFx.setCategoryFx(ConverterCategory.convertToCategoryFx(builtItems.getCategory()));

        return builtItemFx;
    }


}
