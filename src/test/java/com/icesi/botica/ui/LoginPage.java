package com.icesi.botica.ui;

import net.serenitybdd.screenplay.targets.Target;

public class LoginPage {
    public static final Target USERNAME = Target.the("campo usuario").locatedBy("#usuario");
    public static final Target PASSWORD = Target.the("campo contraseña").locatedBy("[name='clave']");
    public static final Target LOGIN_BUTTON = Target.the("botón ingresar").locatedBy("#form-login button[type='submit']");
    public static final Target ERROR_MESSAGE = Target.the("mensaje de error de inicio de sesión").locatedBy("[data-testid='error-login']");
}
