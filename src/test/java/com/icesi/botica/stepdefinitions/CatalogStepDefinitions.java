package com.icesi.botica.stepdefinitions;

import com.icesi.botica.questions.TheCatalog;
import com.icesi.botica.questions.TheShoppingCart;
import com.icesi.botica.tasks.AddToCart;
import com.icesi.botica.tasks.NavigateTo;
import com.icesi.botica.tasks.SearchTheCatalog;
import com.icesi.botica.ui.CatalogPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.util.Arrays;
import java.util.List;

public class CatalogStepDefinitions {

    @Cuando("{actor} filtra el catálogo por la categoría {string}")
    public void filtersByCategory(Actor actor, String category) {
        actor.attemptsTo(SearchTheCatalog.byCategory(category));
    }

    @Cuando("{actor} busca productos con el texto {string}")
    public void searchesProducts(Actor actor, String text) {
        actor.attemptsTo(SearchTheCatalog.byName(text));
    }

    @Cuando("{actor} agrega {int} unidad(es) de {string} al carrito")
    public void addsToCart(Actor actor, int quantity, String product) {
        actor.attemptsTo(AddToCart.units(quantity, product));
    }

    @Entonces("{actor} debería ver los productos {string}")
    public void shouldSeeTheProducts(Actor actor, String products) {
        List<String> expected = Arrays.stream(products.split(",")).map(String::trim).toList();
        actor.attemptsTo(Ensure.that(CatalogPage.PRODUCT_NAMES).textValues().isEqualTo(expected));
    }

    @Entonces("{actor} debería ver el aviso {string}")
    public void shouldSeeTheNotice(Actor actor, String notice) {
        actor.attemptsTo(
                Ensure.that(CatalogPage.NO_RESULTS).isDisplayed(),
                Ensure.that(CatalogPage.NO_RESULTS).text().isEqualTo(notice)
        );
    }

    @Entonces("{actor} no debería ver productos en el catálogo")
    public void shouldSeeNoProducts(Actor actor) {
        actor.attemptsTo(Ensure.that(CatalogPage.PRODUCT_NAMES).isNotDisplayed());
    }

    @Entonces("{actor} debería ver en el catálogo el mensaje {string}")
    public void shouldSeeTheCatalogMessage(Actor actor, String message) {
        actor.attemptsTo(
                Ensure.that(CatalogPage.MESSAGE).isDisplayed(),
                Ensure.that(CatalogPage.MESSAGE).text().isEqualTo(message)
        );
    }

    @Entonces("el carrito de {actor} debería tener {int} unidad(es)")
    public void cartShouldHaveUnits(Actor actor, int units) {
        actor.attemptsTo(Ensure.that(TheShoppingCart.unitCount()).isEqualTo(units));
    }

    @Entonces("{actor} debería ver {int} unidades disponibles de {string} en el catálogo")
    public void shouldSeeAvailableUnits(Actor actor, int units, String product) {
        actor.attemptsTo(
                NavigateTo.theCatalog(),
                Ensure.that(TheCatalog.availableUnitsOf(product)).isEqualTo(units)
        );
    }
}
