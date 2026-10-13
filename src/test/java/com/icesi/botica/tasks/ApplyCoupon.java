package com.icesi.botica.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.icesi.botica.ui.CartPage.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ApplyCoupon {

    /** La validación del cupón es asíncrona (muestra un indicador de carga): se espera a que aparezca la respuesta. */
    public static Performable withCode(String code) {
        return Task.where("{0} aplica el cupón \"" + code + "\"",
                FillField.with(COUPON_FIELD, code),
                Click.on(APPLY_COUPON_BUTTON),
                WaitUntil.the(COUPON_MESSAGE, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
