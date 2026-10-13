package com.icesi.botica.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CatalogPage {
    public static final Target TITLE = Target.the("título del catálogo").locatedBy("main.contenido h1");
    public static final Target SEARCH_FIELD = Target.the("campo buscar por nombre").locatedBy("#buscar");
    public static final Target CATEGORY_FILTER = Target.the("filtro de categoría").locatedBy("[name='categoria']");
    public static final Target MESSAGE = Target.the("mensaje del catálogo").locatedBy("[data-testid='mensaje-catalogo']");
    public static final Target PRODUCT_NAMES = Target.the("nombres de los productos").locatedBy(".tarjeta-producto .nombre-producto");
    public static final Target NO_RESULTS = Target.the("aviso sin resultados").locatedBy(".sin-resultados");

    // Localizadores dinámicos: la tarjeta del producto se identifica por su nombre visible
    private static final String PRODUCT_CARD = "//article[contains(@class,'tarjeta-producto')][.//h3[normalize-space()='{0}']]";

    public static final Target QUANTITY_OF = Target.the("cantidad de {0}").locatedBy(PRODUCT_CARD + "//input[contains(@class,'cantidad')]");
    public static final Target ADD_BUTTON_OF = Target.the("botón agregar {0}").locatedBy(PRODUCT_CARD + "//button[contains(@class,'btn-agregar')]");
    public static final Target AVAILABLE_UNITS_OF = Target.the("unidades disponibles de {0}").locatedBy(PRODUCT_CARD + "//p[contains(@class,'stock')]/span");
}
