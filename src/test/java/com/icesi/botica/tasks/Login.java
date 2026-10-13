package com.icesi.botica.tasks;

import com.icesi.botica.ui.NavigationBar;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.icesi.botica.ui.LoginPage.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Login {

    public static final String PRACTICE_USER = "alquimista";
    public static final String PRACTICE_PASSWORD = "pocion123";

    public static Performable withCredentials(String user, String password) {
        return Task.where("{0} inicia sesión como \"" + user + "\"",
                FillField.with(USERNAME, user),
                FillField.with(PASSWORD, password),
                Click.on(LOGIN_BUTTON)
        );
    }

    /** Inicia sesión con el usuario de práctica y espera a que cargue el catálogo (la verificación de credenciales es asíncrona). */
    public static Performable asPracticeUser() {
        return Task.where("{0} inicia sesión con el usuario de práctica",
                NavigateTo.theLoginPage(),
                withCredentials(PRACTICE_USER, PRACTICE_PASSWORD),
                WaitUntil.the(NavigationBar.GREETING, isVisible()).forNoMoreThan(20).seconds()
        );
    }
}
