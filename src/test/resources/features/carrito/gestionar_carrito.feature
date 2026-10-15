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

  # HU Funcionalidad 1, criterio 6: la cantidad nunca puede superar el stock, tampoco al modificarla en el carrito
  @Issue-21 @bug @negativo
  Escenario: No permitir actualizar la cantidad del carrito por encima del stock disponible
    Dado que Merlín tiene 1 unidad de "Elixir de Vitalidad" en el carrito
    Cuando Merlín actualiza la cantidad de "Elixir de Vitalidad" a 5
    Entonces Merlín debería ver en el carrito el mensaje "No hay suficiente stock disponible"
    Y el subtotal del carrito debería ser "$42.000"
    Y el carrito de Merlín debería tener 1 unidades
