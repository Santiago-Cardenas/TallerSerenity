package com.icesi.botica.tasks;

import com.icesi.botica.models.ShippingDetails;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static com.icesi.botica.ui.CheckoutPage.*;

public class PlaceOrder {

    public static Performable chooseShipping(String method) {
        return Task.where("{0} elige el envío " + method,
                Click.on(SHIPPING_OPTION.of(method))
        );
    }

    public static Performable withDetails(ShippingDetails details) {
        return Task.where("{0} confirma el pedido con los datos de envío de " + details.name(),
                FillField.with(FULL_NAME, details.name()),
                FillField.with(EMAIL, details.email()),
                FillField.with(ADDRESS, details.address()),
                FillField.with(CITY, details.city()),
                FillField.with(PHONE, details.phone()),
                Click.on(CONFIRM_BUTTON)
        );
    }
}
