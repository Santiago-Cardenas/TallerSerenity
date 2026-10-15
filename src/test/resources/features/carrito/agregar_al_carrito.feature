# language: es
@HU-03 @carrito
Característica: Agregar productos al carrito
  Como cliente de la botica
  Quiero agregar productos al carrito respetando las cantidades permitidas y el stock
  Para preparar mi compra sin pedir más de lo que la botica puede despachar

  # Reglas: la cantidad por producto va de 1 a 5 (valores límite 0, 1, 5 y 6),
  # el acumulado en el carrito no puede superar 5 unidades ni el stock disponible.

  Antecedentes:
    Dado que Merlín inició sesión en la botica

  @Issue-5 @smoke @positivo
  Esquema del escenario: Agregar <cantidad> unidad(es) de un producto disponible
    Cuando Merlín agrega <cantidad> unidades de "<producto>" al carrito
    Entonces Merlín debería ver en el catálogo el mensaje "Se agregó <producto> al carrito"
    Y el carrito de Merlín debería tener <cantidad> unidades

    Ejemplos:
      | cantidad | producto                 |
      | 1        | Raíz de Mandrágora       |
      | 5        | Raíz de Mandrágora       |
      | 2        | Poción de Sueño Profundo |

  @Issue-6 @negativo
  Esquema del escenario: Rechazar una cantidad fuera del rango permitido: <cantidad>
    Cuando Merlín agrega <cantidad> unidades de "Polvo de Luna" al carrito
    Entonces Merlín debería ver en el catálogo el mensaje "La cantidad debe estar entre 1 y 5"
    Y el carrito de Merlín debería tener 0 unidades

    Ejemplos:
      | cantidad |
      | 0        |
      | 6        |
      | -1       |

  @Issue-7 @negativo
  Escenario: No permitir agregar más unidades que el stock disponible
    Cuando Merlín agrega 2 unidades de "Pluma de Fénix" al carrito
    Entonces Merlín debería ver en el catálogo el mensaje "No hay suficiente stock disponible"
    Y el carrito de Merlín debería tener 0 unidades
