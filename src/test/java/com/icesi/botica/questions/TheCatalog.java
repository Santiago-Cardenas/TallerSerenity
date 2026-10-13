package com.icesi.botica.questions;

import com.icesi.botica.ui.CatalogPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class TheCatalog {

    /** Unidades disponibles (stock) que muestra la tarjeta del producto en el catálogo. */
    public static Question<Integer> availableUnitsOf(String product) {
        return Question.about("las unidades disponibles de " + product)
                .answeredBy(actor -> Integer.parseInt(Text.of(CatalogPage.AVAILABLE_UNITS_OF.of(product)).answeredBy(actor).trim()));
    }
}
