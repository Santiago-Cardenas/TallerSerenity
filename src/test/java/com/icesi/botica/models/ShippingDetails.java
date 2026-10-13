package com.icesi.botica.models;

/**
 * Datos de envío del formulario de checkout.
 * Los nombres de campo usados en el Gherkin coinciden con el atributo data-campo de la página.
 */
public record ShippingDetails(String name, String email, String address, String city, String phone) {

    public static ShippingDetails ofAValidCustomer() {
        return new ShippingDetails("Nicolás Flamel", "nicolas.flamel@botica.com", "Calle de los Escribanos 7", "Cali", "3001234567");
    }

    /** Devuelve una copia con un único campo modificado: nombre, correo, direccion, ciudad o telefono. */
    public ShippingDetails withField(String field, String value) {
        return switch (field) {
            case "nombre" -> new ShippingDetails(value, email, address, city, phone);
            case "correo" -> new ShippingDetails(name, value, address, city, phone);
            case "direccion" -> new ShippingDetails(name, email, value, city, phone);
            case "ciudad" -> new ShippingDetails(name, email, address, value, phone);
            case "telefono" -> new ShippingDetails(name, email, address, city, value);
            default -> throw new IllegalArgumentException("Campo de envío desconocido: " + field);
        };
    }
}
