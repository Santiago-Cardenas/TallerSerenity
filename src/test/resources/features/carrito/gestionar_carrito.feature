# language: es
@HU-04 @carrito
Característica: Gestionar el carrito de compras
  Como cliente de la botica
  Quiero modificar las cantidades y eliminar productos de mi carrito
  Para ajustar mi compra antes de pagar

  @Issue-11 @positivo
  Escenario: Actualizar la cantidad de un producto en el carrito
    Dado que Merlín tiene 2 unidades de "Elixir de Vitalidad" en el carrito
    Cuando Merlín actualiza la cantidad de "Elixir de Vitalidad" a 3
    Entonces Merlín debería ver en el carrito el mensaje "Cantidad actualizada"
    Y el subtotal de "Elixir de Vitalidad" debería ser "$126.000"
    Y el subtotal del carrito debería ser "$126.000"
    Y el carrito de Merlín debería tener 3 unidades

  @Issue-12 @positivo
  Escenario: Eliminar el único producto deja el carrito vacío
    Dado que Merlín tiene 1 unidad de "Anillo de la Suerte" en el carrito
    Cuando Merlín elimina "Anillo de la Suerte" del carrito
    Entonces Merlín debería ver en el carrito el mensaje "Se eliminó Anillo de la Suerte del carrito"
    Y Merlín debería ver el carrito vacío
    Y el carrito de Merlín debería tener 0 unidades

  @Issue-13 @negativo
  Escenario: Rechazar una cantidad inválida al actualizar el carrito
    Dado que Merlín tiene 2 unidades de "Polvo de Luna" en el carrito
    Cuando Merlín actualiza la cantidad de "Polvo de Luna" a 7
    Entonces Merlín debería ver en el carrito el mensaje "La cantidad debe estar entre 1 y 5"
    Y el subtotal del carrito debería ser "$17.000"
    Y el carrito de Merlín debería tener 2 unidades
