# Automatización de pruebas Front-End con Serenity BDD

Taller de Técnicas de Prueba: automatización de **La Botica del Alquimista** (https://boticaalquimista.onrender.com)
con **Serenity BDD 5.3.9 + Cucumber (Gherkin en español) + patrón Screenplay + JUnit 5 Platform Suite + Gradle**.
Incluye trazabilidad **escenario ↔ Issue ↔ historia de usuario** y ejecución en **GitHub Actions**.

## 1. Aplicación e historias de usuario

Se eligió **La Botica del Alquimista** (tienda en línea de pociones, ingredientes y amuletos).
Toda la lógica corre en el navegador (`localStorage`), por lo que cada escenario parte de un navegador limpio
(`serenity.restart.browser.for.each = scenario`) y las pruebas no dependen del orden ni de otros usuarios.

| HU | Historia de usuario | Reglas verificadas |
|---|---|---|
| HU-01 | Inicio de sesión | Usuario `alquimista` / `pocion123`. Con credenciales inválidas se muestra "Usuario o contraseña incorrectos". |
| HU-02 | Consulta del catálogo | Filtro por categoría y búsqueda por nombre sin distinguir mayúsculas. Si no hay coincidencias, se muestra "No se encontraron productos." |
| HU-03 | Agregar al carrito | Cantidad de 1 a 5, máximo 5 unidades acumuladas por producto, sin superar el stock. Los productos agotados no se pueden agregar. |
| HU-04 | Gestionar el carrito | Actualizar cantidades (de 1 a 5) y eliminar productos. |
| HU-05 | Cupones | LUNA10: 10 % sin mínimo. ELIXIR20: 20 % con compra mínima de $100.000. Un cupón por compra; se retira si la compra baja del mínimo. |
| HU-06 | Checkout | IVA del 19 % sobre el subtotal. Envío normal $8.000, gratis desde $150.000. Envío express $15.000. Campos obligatorios, correo válido y teléfono de 10 dígitos. |
| HU-07 | Confirmación | Número de pedido `BOT-####`. El carrito queda vacío y se descuenta el stock vendido. |

> Las historias se redactaron a partir del comportamiento de la aplicación. Antes de la entrega, contrástelas con el documento de historias de usuario del docente.

## 2. Matriz de trazabilidad

| Issue | Tag | HU | Escenario | Tipo | Ejecuciones |
|---|---|---|---|---|---|
| #1 | `@Issue-1` | HU-01 | Ingresar con credenciales válidas | smoke | 1 |
| #2 | `@Issue-2` | HU-01 | Rechazar credenciales inválidas | negativo | 4 |
| #3 | `@Issue-3` | HU-02 | Filtrar por categoría | positivo | 3 |
| #5 | `@Issue-5` | HU-02 | Búsqueda sin resultados | negativo | 1 |
| #6 | `@Issue-6` | HU-03 | Agregar unidades (valores límite 1 y 5) | smoke | 3 |
| #7 | `@Issue-7` | HU-03 | Cantidad fuera de rango (0, 6, -1) | negativo | 3 |
| #9 | `@Issue-9` | HU-03 | Supera el stock disponible | negativo | 1 |
| #11 | `@Issue-11` | HU-04 | Actualizar cantidad | positivo | 1 |
| #14 | `@Issue-14` | HU-05 | Aplicar LUNA10 / ELIXIR20 | positivo | 2 |
| #15 | `@Issue-15` | HU-05 | Cupón inexistente, vacío o sin mínimo | negativo | 3 |
| #18 | `@Issue-18` | HU-06 | IVA, envío y total (normal, gratis, express) | positivo | 3 |
| #19 | `@Issue-19` | HU-06 | Datos de envío inválidos | negativo | 4 |
| #20 | `@Issue-20` | HU-07 | Confirmar pedido y descontar stock | smoke | 1 |
| #21 | `@Issue-21` | HU-04 | **[BUG]** Actualizar la cantidad del carrito no valida el stock | negativo | 1 |
| #22 | `@Issue-22` | HU-06 | **[BUG]** El IVA se calcula antes del descuento | positivo | 1 |

**Total:** 15 escenarios con su Issue: 13 en la suite normal (30 ejecuciones) y 2 `@bug` que se ejecutan aparte. Los números de Issue conservan el de su tag, por eso hay saltos (no existen #4, #8, #10, #12, #13, #16 ni #17).
La fuente de verdad de los Issues es `traceability/issues.json`.

**Escenarios `@bug` (#21 y #22):** documentan dos defectos de la aplicación frente a la historia de usuario oficial
(`HistoriaUsuario_Botica.md`) y **fallan a propósito**. Por eso quedan **excluidos de la ejecución normal**
(`build.gradle` aplica `not @bug` cuando no se indica ningún tag) y la suite y el pipeline siguen en verde (aparecen como *skipped*).
Se ejecutan solo cuando se piden por su tag:

```powershell
.\gradlew.bat clean test "-Dcucumber.filter.tags=@Issue-21"   # bug del stock en el carrito
.\gradlew.bat clean test "-Dcucumber.filter.tags=@Issue-22"   # bug del IVA antes del descuento
.\gradlew.bat clean test "-Dcucumber.filter.tags=@bug"        # los dos
```

- **#21:** con 1 Elixir de Vitalidad (stock 3) se pide actualizar a 5. Se esperaba "No hay suficiente stock disponible" y la página responde "Cantidad actualizada".
- **#22:** con 2 Pociones de Sueño Profundo y `LUNA10`, el IVA esperado es $6.327 (19 % de $33.300) y la página muestra $7.030 (19 % de $37.000).

Tags disponibles:
- Por Issue: `@Issue-N`
- Por historia de usuario: `@HU-0X`
- Por módulo: `@autenticacion`, `@catalogo`, `@carrito`, `@cupones`, `@pago`
- Por tipo: `@smoke`, `@positivo`, `@negativo`

## 3. Diseño Screenplay

```text
Feature (Gherkin) → Step Definition → Actor (Merlín) → Task / Question / Ensure → Target (ui/)
```

```text
src/test/java/com/icesi/botica/
├── runners/CucumberTestSuite.java     → Suite JUnit 5 + SerenityReporterParallel + JSON de Cucumber
├── hooks/ScreenplayHooks.java          → OnlineCast (un navegador por actor) y @ParameterType {actor}
├── stepdefinitions/                    → Solo traducen Gherkin a llamadas del actor
├── tasks/                              → Metas de negocio (Task.where)
├── questions/                          → Consultas propias con Question.about
├── ui/                                 → Targets agrupados por página
└── models/ShippingDetails.java         → Datos de envío (record)
src/test/resources/
├── features/<módulo>/*.feature         → Gherkin en español (# language: es)
└── serenity.conf                       → Chrome headless, timeouts y URL base
```

### Clases y métodos

| # | Paquete | Clase | Métodos / elementos | Para qué sirve |
|---|---|---|---|---|
| 1 | ui | LoginPage | USERNAME, PASSWORD, LOGIN_BUTTON, ERROR_MESSAGE | Localizadores del inicio de sesión |
| 2 | ui | NavigationBar | GREETING, CATALOG_LINK, CART_LINK, CART_COUNTER | Barra superior común a todas las páginas |
| 3 | ui | CatalogPage | SEARCH_FIELD, CATEGORY_FILTER, PRODUCT_NAMES, QUANTITY_OF, ADD_BUTTON_OF, AVAILABLE_UNITS_OF… | Catálogo. Los targets `…_OF` son dinámicos por nombre de producto (`.of("Polvo de Luna")`) |
| 4 | ui | CartPage | QUANTITY_OF, UPDATE_BUTTON_OF, REMOVE_BUTTON_OF, COUPON_FIELD, SUBTOTAL, DISCOUNT, PARTIAL_TOTAL… | Carrito y cupones |
| 5 | ui | CheckoutPage | FULL_NAME, EMAIL, ADDRESS, CITY, PHONE, SHIPPING_OPTION, FIELD_ERROR, TOTAL… | Formulario de envío y resumen del pedido |
| 6 | ui | ConfirmationPage | TITLE, ORDER_NUMBER, RECIPIENT, TOTAL_PAID | Confirmación del pedido |
| 7 | tasks | Login | withCredentials(user, password), asPracticeUser() | Iniciar sesión (la segunda espera a que cargue el catálogo) |
| 8 | tasks | NavigateTo | theLoginPage(), theCatalog(), theCart(), theCheckout() | Navegación entre páginas |
| 9 | tasks | SearchTheCatalog | byName(text), byCategory(category) | Buscar y filtrar |
| 10 | tasks | AddToCart | units(quantity, product) | Agregar productos desde el catálogo |
| 11 | tasks | ManageCart | updateQuantity(product, qty) | Modificar la cantidad en el carrito |
| 12 | tasks | ApplyCoupon | withCode(code) | Aplicar un cupón (espera la respuesta asíncrona) |
| 13 | tasks | PlaceOrder | chooseShipping(method), withDetails(details) | Elegir envío y confirmar el pedido |
| 14 | tasks | FillField | with(target, value) | Escribir un valor o dejar el campo vacío (escenarios negativos) |
| 15 | questions | TheShoppingCart | unitCount() | Unidades del contador del carrito |
| 16 | questions | TheCatalog | availableUnitsOf(product) | Stock que muestra el catálogo |
| 17 | models | ShippingDetails | ofAValidCustomer(), withField(campo, valor) | Cliente válido y variantes con un campo inválido |

### Del Gherkin al código (ejemplos)

| Paso del .feature | Step Definition | Task / Question / Ensure |
|---|---|---|
| `Dado que Merlín inició sesión en la botica` | `AuthenticationStepDefinitions.hasLoggedIn` | `Login.asPracticeUser()` |
| `Cuando Merlín agrega 2 unidades de "X" al carrito` | `CatalogStepDefinitions.addsToCart` | `AddToCart.units(2, "X")` |
| `Y el carrito de Merlín debería tener 2 unidades` | `CatalogStepDefinitions.cartShouldHaveUnits` | `Ensure.that(TheShoppingCart.unitCount()).isEqualTo(2)` |
| `Cuando Merlín aplica el cupón "LUNA10"` | `CartStepDefinitions.appliesTheCoupon` | `ApplyCoupon.withCode("LUNA10")` |
| `Entonces el resumen del pedido debería mostrar subtotal…` | `CheckoutStepDefinitions.orderSummaryShouldShow` | `Ensure.that(CheckoutPage.TOTAL).text().isEqualTo(…)` |

### Decisiones y riesgos

- **Esperas:** la aplicación simula latencia en el login (0,3–0,7 s), en los cupones (0,4–1 s) y al confirmar el pedido (0,8–1,6 s).
  Solo en esos puntos se usa `WaitUntil`; en el resto bastan las esperas implícitas del `Target`. No se usa `Thread.sleep`.
- **Localizadores:** se priorizan `id`, `name` y `data-testid`. Los dinámicos usan XPath relativo por el texto visible del producto.
  Los selectores CSS parametrizados que empiezan por una etiqueta llevan el prefijo `css:`, porque sin él Serenity los interpreta como XPath.
- **Localizadores frágiles a propósito:** la página de checkout tiene ids generados (`inp-x92`, `f-dir-01`, `t-771`).
  Por eso se usan `name` y `data-testid` en lugar de esos ids.

## 4. Ejecución local (Windows)

Requiere Java 17 y Google Chrome (Selenium Manager descarga el driver).

```powershell
.\gradlew.bat clean test                                         # toda la suite + reporte
.\gradlew.bat clean test "-Dcucumber.filter.tags=@Issue-14"      # un issue
.\gradlew.bat clean test "-Dcucumber.filter.tags=@HU-05"         # una historia de usuario
.\gradlew.bat clean test "-Dcucumber.filter.tags=@smoke or @negativo"
.\gradlew.bat clean test "-Dheadless.mode=false"                 # ver el navegador
```

Reporte (Documentación Viva): `target/site/serenity/index.html`.

## 5. Configuración en GitHub (repositorio, TestCases e Issues)

1. **Repositorio:** crear un repositorio vacío y subir esta carpeta como raíz:
   ```powershell
   git init; git add .; git commit -m "feat: automatización serenity botica del alquimista"
   git branch -M main
   git remote add origin https://github.com/<usuario>/<repositorio>.git
   git push -u origin main
   ```
2. **Issues y labels:** en *Actions*, abrir **Setup trazabilidad (labels + issues)** y hacer clic en *Run workflow*.
   El workflow crea los labels (`status:passed`, `status:failed`, `bug`, `HU-0X`…) y los Issues pendientes de `traceability/issues.json`.
   Cada Issue incluye el tag, la historia de usuario, el archivo feature y el Gherkin del escenario.
3. **Proyecto (TestCases):** en *Projects*, crear un proyecto y vincularlo al repositorio.
   Luego agregar los Issues con *Add item from repository*, o activar el workflow *Auto-add to project* con el filtro `is:issue`.
4. **Ejecución desde el TestCase:** en *Actions*, abrir **CI / CD Pipeline**, hacer clic en *Run workflow* y escribir el tag (por ejemplo `@Issue-14`).
   Al terminar, el pipeline:
   - publica el reporte de Serenity como artefacto `serenity-report`;
   - comenta en cada Issue ejecutado el resultado y los escenarios;
   - actualiza los labels `status:passed` / `status:failed`.

## 6. Preguntas para la sustentación

- Si mañana cambia el `name` del campo contraseña, ¿qué archivo se modifica y cuántos escenarios se ven afectados?
  Solo `LoginPage.PASSWORD`; afecta a todos, porque todos inician sesión.
- ¿Por qué `ApplyCoupon` necesita un `WaitUntil` y `AddToCart` no?
- ¿Cómo generaría a mano el localizador del botón "Agregar al carrito" del "Anillo de la Suerte"?
  `//article[contains(@class,'tarjeta-producto')][.//h3[normalize-space()='Anillo de la Suerte']]//button[contains(@class,'btn-agregar')]`
