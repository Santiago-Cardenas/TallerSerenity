package com.icesi.botica.tasks;

import com.icesi.botica.ui.CartPage;
import com.icesi.botica.ui.CheckoutPage;
import com.icesi.botica.ui.NavigationBar;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.icesi.botica.ui.CatalogPage.SEARCH_FIELD;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class NavigateTo {

    public static Performable theLoginPage() {
        return Task.where("{0} abre la página de inicio de sesión de la botica",
                Open.relativeUrl("/index.html")
        );
    }

    public static Performable theCatalog() {
        return Task.where("{0} navega al catálogo",
                Click.on(NavigationBar.CATALOG_LINK),
                WaitUntil.the(SEARCH_FIELD, isVisible()).forNoMoreThan(15).seconds()
        );
    }

    public static Performable theCart() {
        return Task.where("{0} navega a su carrito",
                Click.on(NavigationBar.CART_LINK),
                WaitUntil.the(CartPage.COUPON_FIELD, isVisible()).forNoMoreThan(15).seconds()
        );
    }

    public static Performable theCheckout() {
        return Task.where("{0} decide ir a pagar",
                Click.on(CartPage.PAY_BUTTON),
                WaitUntil.the(CheckoutPage.TITLE, containsText("Datos de envío y pago")).forNoMoreThan(15).seconds()
        );
    }
}
