package com.icesi.botica.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.icesi.botica.ui.CartPage.*;

public class ManageCart {

    public static Performable updateQuantity(String product, int quantity) {
        return Task.where("{0} actualiza la cantidad de " + product + " a " + quantity,
                Enter.theValue(String.valueOf(quantity)).into(QUANTITY_OF.of(product)),
                Click.on(UPDATE_BUTTON_OF.of(product))
        );
    }

    public static Performable remove(String product) {
        return Task.where("{0} elimina " + product + " del carrito",
                Click.on(REMOVE_BUTTON_OF.of(product))
        );
    }
}
