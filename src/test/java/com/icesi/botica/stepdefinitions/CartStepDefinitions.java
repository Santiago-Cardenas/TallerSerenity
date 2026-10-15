package com.icesi.botica.stepdefinitions;

import com.icesi.botica.tasks.AddToCart;
import com.icesi.botica.tasks.ApplyCoupon;
import com.icesi.botica.tasks.Login;
import com.icesi.botica.tasks.ManageCart;
import com.icesi.botica.tasks.NavigateTo;
import com.icesi.botica.ui.CartPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class CartStepDefinitions {

    @Dado("que {actor} tiene {int} unidad(es) de {string} en el carrito")
    public void hasProductsInTheCart(Actor actor, int quantity, String product) {
        actor.attemptsTo(
                Login.asPracticeUser(),
                AddToCart.units(quantity, product),
                NavigateTo.theCart()
        );
    }

    @Cuando("{actor} actualiza la cantidad de {string} a {int}")
    public void updatesTheQuantity(Actor actor, String product, int quantity) {
        actor.attemptsTo(ManageCart.updateQuantity(product, quantity));
    }

    @Dado("{actor} aplicó el cupón {string}")
    @Cuando("{actor} aplica el cupón {string}")
    public void appliesTheCoupon(Actor actor, String code) {
        actor.attemptsTo(ApplyCoupon.withCode(code));
    }

    @Entonces("{actor} debería ver en el carrito el mensaje {string}")
    public void shouldSeeTheCartMessage(Actor actor, String message) {
        actor.attemptsTo(
                Ensure.that(CartPage.MESSAGE).isDisplayed(),
                Ensure.that(CartPage.MESSAGE).text().isEqualTo(message)
        );
    }

    @Entonces("el subtotal de {string} debería ser {string}")
    public void lineSubtotalShouldBe(String product, String subtotal) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(CartPage.LINE_SUBTOTAL_OF.of(product)).text().isEqualTo(subtotal)
        );
    }

    @Entonces("el subtotal del carrito debería ser {string}")
    public void cartSubtotalShouldBe(String subtotal) {
        OnStage.theActorInTheSpotlight().attemptsTo(Ensure.that(CartPage.SUBTOTAL).text().isEqualTo(subtotal));
    }

    @Entonces("{actor} debería ver el mensaje del cupón {string}")
    public void shouldSeeTheCouponMessage(Actor actor, String message) {
        actor.attemptsTo(Ensure.that(CartPage.COUPON_MESSAGE).text().isEqualTo(message));
    }

    @Entonces("el cupón aplicado debería ser {string}")
    public void appliedCouponShouldBe(String code) {
        OnStage.theActorInTheSpotlight().attemptsTo(Ensure.that(CartPage.APPLIED_COUPON).text().isEqualTo(code));
    }

    @Entonces("el descuento debería ser {string}")
    public void discountShouldBe(String discount) {
        OnStage.theActorInTheSpotlight().attemptsTo(Ensure.that(CartPage.DISCOUNT).text().isEqualTo(discount));
    }

    @Entonces("el total parcial debería ser {string}")
    public void partialTotalShouldBe(String total) {
        OnStage.theActorInTheSpotlight().attemptsTo(Ensure.that(CartPage.PARTIAL_TOTAL).text().isEqualTo(total));
    }
}
