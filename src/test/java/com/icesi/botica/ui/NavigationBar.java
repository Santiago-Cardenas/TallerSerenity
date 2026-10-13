package com.icesi.botica.ui;

import net.serenitybdd.screenplay.targets.Target;

public class NavigationBar {
    public static final Target GREETING = Target.the("saludo al usuario").locatedBy("header.barra .saludo");
    public static final Target CATALOG_LINK = Target.the("enlace al catálogo").locatedBy("nav.menu a[href='catalogo.html']");
    public static final Target CART_LINK = Target.the("enlace al carrito").locatedBy("#enlace-carrito");
    public static final Target CART_COUNTER = Target.the("contador del carrito").locatedBy("[data-testid='contador-carrito']");
}
