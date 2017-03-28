package com.almundo.browser.automation.utils;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by leandro.efron on 22/11/2016.
 */
public class Constants {

    //URLS
    public static final String STAGING_URL = "http://staging.almundo.com.ar/";
    public static final String PROD_URL = "http://almundo.com/";
    public static final String SUC_URL = "https://sucursales.almundo.com.mx/";
    public static final String CCR_URL = "https://ccr.almundo.com.ar/";
    public static final String ICBC_URL = "https://icbcstore.almundo.com.ar/";

    //PATHS
    public static final String RESOURCES_PATH = "src/test/resources/";
    public static final String DATA_PATH = "src/test/java/com/almundo/browser/automation/data/";

    public enum FlightType {
        ONE_WAY("Solo ida"),
        ROUND_TRIP("Ida y vuelta"),
        MULTIDESTINATION("Varias ciudades");

        public final String flightType;

        FlightType(String flightType) {
            this.flightType = flightType;
        }

        @Override
        public String toString() {
            return flightType;
        }
    }

    public enum Messages {
        VOLVE_A_INTENTARLO_MSG("No pudimos procesar la consulta. Volvé a intentarlo en unos minutos."),
        NO_DISPONIBILIDAD_MSG("Lo sentimos. No encontramos disponibilidad para tu búsqueda\nPor favor intentá una nueva busqueda"),
        FELICITACIONES_MSG("¡Felicitaciones, tu solicitud de compra fué exitosa!"),
        MANDATORY_FLD_MSG("Este campo es obligatorio."),
        LISTADO_DE_SUCURSALES_LNK("Ver listado de sucursales");



        public final String message;

        Messages(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    //LISTS
    public static final List<String> FLIGHT_TYPE_LIST = asList("Ida y vuelta", "Solo ida", "Varias ciudades");
    public static final List<String> FLIGHT_CLASS_LIST = asList("Todas", "Primera", "Business", "Premium", "Turista");
    public static final List<String> CHILD_RANGE_LIST = asList("Menos de 2 años en brazos", "Menos de 2 años en asiento", "Entre 2 y 11 años", "Más de 11 años");
    public static final List<String> USER_MENU_LIST_AR = asList("Perfil", "Medios de Pago", "Reservas", "Mis gustos", "Mis puntos", "Cerrar sesión");
    public static final List<String> USER_MENU_LIST_CO = asList("Perfil", "Formas de pago", "Reservas", "Cerrar sesión");
    public static final List<String> USER_MENU_LIST_MX = asList("Perfil", "Medios de Pago", "Reservas", "Cerrar sesión");

}
