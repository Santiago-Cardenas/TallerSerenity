package com.icesi.botica.stepdefinitions;

import com.icesi.botica.tasks.Login;
import com.icesi.botica.tasks.NavigateTo;
import com.icesi.botica.ui.CatalogPage;
import com.icesi.botica.ui.LoginPage;
import com.icesi.botica.ui.NavigationBar;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AuthenticationStepDefinitions {

    @Dado("que {actor} está en la página de inicio de sesión")
    public void isOnTheLoginPage(Actor actor) {
        actor.attemptsTo(NavigateTo.theLoginPage());
    }

    @Dado("que {actor} inició sesión en la botica")
    public void hasLoggedIn(Actor actor) {
        actor.attemptsTo(Login.asPracticeUser());
    }

    @Cuando("{actor} inicia sesión con el usuario {string} y la contraseña {string}")
    public void logsIn(Actor actor, String user, String password) {
        actor.attemptsTo(Login.withCredentials(user, password));
    }

    @Entonces("{actor} debería ver el catálogo de productos")
    public void shouldSeeTheCatalog(Actor actor) {
        actor.attemptsTo(
                WaitUntil.the(CatalogPage.SEARCH_FIELD, isVisible()).forNoMoreThan(20).seconds(),
                Ensure.that(CatalogPage.TITLE).text().isEqualTo("Catálogo"),
                Ensure.that(CatalogPage.PRODUCT_NAMES).textValues().hasSize(9)
        );
    }

    @Entonces("{actor} debería ver el saludo {string}")
    public void shouldSeeTheGreeting(Actor actor, String greeting) {
        actor.attemptsTo(Ensure.that(NavigationBar.GREETING).text().isEqualTo(greeting));
    }

    @Entonces("{actor} debería ver el error de inicio de sesión {string}")
    public void shouldSeeTheLoginError(Actor actor, String error) {
        actor.attemptsTo(
                WaitUntil.the(LoginPage.ERROR_MESSAGE, isVisible()).forNoMoreThan(15).seconds(),
                Ensure.that(LoginPage.ERROR_MESSAGE).text().isEqualTo(error)
        );
    }

    @Entonces("{actor} debería permanecer en la página de inicio de sesión")
    public void shouldStayOnTheLoginPage(Actor actor) {
        actor.attemptsTo(
                Ensure.that(LoginPage.LOGIN_BUTTON).isDisplayed(),
                Ensure.thatTheCurrentPage().currentUrl().endsWith("/index.html")
        );
    }
}
