package com.icesi.botica.stepdefinitions;

import com.icesi.botica.models.ShippingDetails;
import com.icesi.botica.tasks.AddToCart;
import com.icesi.botica.tasks.Login;
import com.icesi.botica.tasks.NavigateTo;
import com.icesi.botica.tasks.PlaceOrder;
import com.icesi.botica.ui.CheckoutPage;
import com.icesi.botica.ui.ConfirmationPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CheckoutStepDefinitions {

    @Dado("que {actor} va a pagar {int} unidad(es) de {string}")
    public void isAboutToPay(Actor actor, int quantity, String product) {
        actor.attemptsTo(
                Login.asPracticeUser(),
                AddToCart.units(quantity, product),
                NavigateTo.theCart(),
                NavigateTo.theCheckout()
        );
    }

    @Cuando("{actor} elige el envío {string}")
    public void choosesShipping(Actor actor, String method) {
        actor.attemptsTo(PlaceOrder.chooseShipping(method));
    }

    @Cuando("{actor} confirma el pedido con datos de envío válidos")
    public void placesTheOrder(Actor actor) {
        actor.attemptsTo(PlaceOrder.withDetails(ShippingDetails.ofAValidCustomer()));
    }

    @Cuando("{actor} confirma el pedido con el campo {string} en {string}")
    public void placesTheOrderWithAnInvalidField(Actor actor, String field, String value) {
        actor.attemptsTo(PlaceOrder.withDetails(ShippingDetails.ofAValidCustomer().withField(field, value)));
    }

    @Entonces("el resumen del pedido debería mostrar subtotal {string}, IVA {string}, envío {string} y total {string}")
    public void orderSummaryShouldShow(String subtotal, String vat, String shipping, String total) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(CheckoutPage.SUBTOTAL).text().isEqualTo(subtotal),
                Ensure.that(CheckoutPage.VAT).text().isEqualTo(vat),
                Ensure.that(CheckoutPage.SHIPPING_COST).text().isEqualTo(shipping),
                Ensure.that(CheckoutPage.TOTAL).text().isEqualTo(total)
        );
    }

    @Entonces("{actor} debería ver el mensaje de pago {string}")
    public void shouldSeeTheCheckoutMessage(Actor actor, String message) {
        actor.attemptsTo(
                Ensure.that(CheckoutPage.MESSAGE).isDisplayed(),
                Ensure.that(CheckoutPage.MESSAGE).text().isEqualTo(message)
        );
    }

    @Entonces("el campo {string} debería indicar el error {string}")
    public void fieldShouldShowTheError(String field, String error) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(CheckoutPage.FIELD_ERROR.of(field)).text().isEqualTo(error)
        );
    }

    @Entonces("{actor} debería ver la confirmación del pedido con un número válido")
    public void shouldSeeTheOrderConfirmation(Actor actor) {
        actor.attemptsTo(
                WaitUntil.the(ConfirmationPage.TITLE, isVisible()).forNoMoreThan(20).seconds(),
                Ensure.that(ConfirmationPage.TITLE).text().isEqualTo("¡Pedido confirmado!"),
                Ensure.that(ConfirmationPage.ORDER_NUMBER).text().matches("BOT-\\d{4}"),
                Ensure.that(ConfirmationPage.RECIPIENT).text().contains(ShippingDetails.ofAValidCustomer().name())
        );
    }

    @Entonces("el total pagado debería ser {string}")
    public void totalPaidShouldBe(String total) {
        OnStage.theActorInTheSpotlight().attemptsTo(Ensure.that(ConfirmationPage.TOTAL_PAID).text().isEqualTo(total));
    }
}
