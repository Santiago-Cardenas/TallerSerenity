package com.icesi.botica.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CartPage {
    public static final Target MESSAGE = Target.the("mensaje del carrito").locatedBy("[data-testid='mensaje-carrito']");

    // Fila del carrito identificada por el nombre del producto
    private static final String CART_LINE = "//tr[contains(@class,'linea-carrito')][td[contains(@class,'nombre-linea')][contains(normalize-space(),'{0}')]]";

    public static final Target QUANTITY_OF = Target.the("cantidad en el carrito de {0}").locatedBy(CART_LINE + "//input[contains(@class,'cantidad-carrito')]");
    public static final Target UPDATE_BUTTON_OF = Target.the("botón actualizar {0}").locatedBy(CART_LINE + "//button[contains(@class,'btn-actualizar')]");
    public static final Target LINE_SUBTOTAL_OF = Target.the("subtotal de {0}").locatedBy(CART_LINE + "/td[contains(@class,'subtotal-linea')]");

    public static final Target COUPON_FIELD = Target.the("campo cupón de descuento").locatedBy("[name='cupon']");
    public static final Target APPLY_COUPON_BUTTON = Target.the("botón aplicar cupón").locatedBy("#btn-aplicar-cupon");
    public static final Target COUPON_MESSAGE = Target.the("mensaje del cupón").locatedBy("#mensaje-cupon");
    public static final Target APPLIED_COUPON = Target.the("cupón aplicado").locatedBy("[data-testid='cupon-aplicado']");

    public static final Target SUBTOTAL = Target.the("subtotal del carrito").locatedBy("#subtotal");
    public static final Target DISCOUNT = Target.the("descuento del carrito").locatedBy("[data-testid='descuento']");
    public static final Target PARTIAL_TOTAL = Target.the("total parcial").locatedBy(".total-parcial");
    public static final Target PAY_BUTTON = Target.the("botón ir a pagar").locatedBy(".btn-pagar");
}
