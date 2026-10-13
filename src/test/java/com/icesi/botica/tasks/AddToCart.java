package com.icesi.botica.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.icesi.botica.ui.CatalogPage.ADD_BUTTON_OF;
import static com.icesi.botica.ui.CatalogPage.QUANTITY_OF;

public class AddToCart {

    public static Performable units(int quantity, String product) {
        return Task.where("{0} agrega " + quantity + " unidad(es) de " + product + " al carrito",
                Enter.theValue(String.valueOf(quantity)).into(QUANTITY_OF.of(product)),
                Click.on(ADD_BUTTON_OF.of(product))
        );
    }
}
