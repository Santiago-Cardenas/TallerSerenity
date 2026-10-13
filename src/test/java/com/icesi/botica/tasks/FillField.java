package com.icesi.botica.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;

/** Escribe un valor en un campo; si el valor está vacío, deja el campo vacío (útil en escenarios negativos). */
public class FillField {

    public static Performable with(Target field, String value) {
        return Task.where("{0} diligencia el " + field + " con \"" + value + "\"",
                actor -> {
                    if (value == null || value.isEmpty()) {
                        actor.attemptsTo(Clear.field(field));
                    } else {
                        actor.attemptsTo(Enter.theValue(value).into(field));
                    }
                });
    }
}
