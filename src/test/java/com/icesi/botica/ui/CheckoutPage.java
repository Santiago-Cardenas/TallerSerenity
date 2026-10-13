package com.icesi.botica.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CheckoutPage {
    public static final Target TITLE = Target.the("título de datos de envío y pago").locatedBy("main.contenido h1");
    public static final Target FULL_NAME = Target.the("campo nombre completo").locatedBy("#nombreCompleto");
    public static final Target EMAIL = Target.the("campo correo electrónico").locatedBy("[name='correo']");
    public static final Target ADDRESS = Target.the("campo dirección").locatedBy("[data-testid='direccion']");
    public static final Target CITY = Target.the("campo ciudad").locatedBy("[name='ciudad']");
    public static final Target PHONE = Target.the("campo teléfono").locatedBy("[name='telefono']");
    public static final Target SHIPPING_OPTION = Target.the("opción de envío {0}").locatedBy("css:input[name='envio'][value='{0}']");
    public static final Target CONFIRM_BUTTON = Target.the("botón confirmar pedido").locatedBy("#btn-confirmar");
    public static final Target MESSAGE = Target.the("mensaje del checkout").locatedBy("[data-testid='mensaje-checkout']");
    public static final Target FIELD_ERROR = Target.the("error del campo {0}").locatedBy(".error-campo[data-campo='{0}']");

    public static final Target SUBTOTAL = Target.the("subtotal del pedido").locatedBy("#resumen-subtotal");
    public static final Target VAT = Target.the("IVA del pedido").locatedBy(".valor-iva");
    public static final Target SHIPPING_COST = Target.the("costo de envío").locatedBy("[data-testid='resumen-envio']");
    public static final Target TOTAL = Target.the("total a pagar").locatedBy("#total-pagar");
}
