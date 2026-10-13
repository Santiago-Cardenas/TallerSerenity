package com.icesi.botica.questions;

import com.icesi.botica.ui.NavigationBar;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class TheShoppingCart {

    /** Unidades que indica el contador del carrito en la barra de navegación. */
    public static Question<Integer> unitCount() {
        return Question.about("la cantidad de unidades en el carrito")
                .answeredBy(actor -> Integer.parseInt(Text.of(NavigationBar.CART_COUNTER).answeredBy(actor).trim()));
    }
}
