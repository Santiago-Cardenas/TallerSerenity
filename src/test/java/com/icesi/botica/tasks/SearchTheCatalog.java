package com.icesi.botica.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

import static com.icesi.botica.ui.CatalogPage.CATEGORY_FILTER;
import static com.icesi.botica.ui.CatalogPage.SEARCH_FIELD;

public class SearchTheCatalog {

    public static Performable byName(String text) {
        return Task.where("{0} busca productos con el texto \"" + text + "\"",
                Enter.theValue(text).into(SEARCH_FIELD)
        );
    }

    public static Performable byCategory(String category) {
        return Task.where("{0} filtra el catálogo por la categoría " + category,
                SelectFromOptions.byVisibleText(category).from(CATEGORY_FILTER)
        );
    }
}
