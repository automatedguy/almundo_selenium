package com.almundo.browser.automation.utils;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by leandro.efron on 22/11/2016.
 */
public class Constants {

    //BROWSERS
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String CHROME_HEADLESS = "chrome-headless";

    //VERSIONS
    public static final String LATEST = "latest";

    //BOOLEAN
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    //URLS
    public static final String STG_URL = "https://st.almundo.com";
    public static final String PROD_URL = "http://almundo.com/";
    public static final String RET_URL = "https://sucursales.almundo.com";
    public static final String RET_STG_URL = "https://sucursales.st.almundo.com";
    public static final String CCR_URL = "https://ccr.almundo.com";
    public static final String CCR_STG_URL = "https://ccr.st.almundo.com";
    public static final String CHECKOUT_HOST = "http://10.20.1.242:8080/";
    public static final String ICBC_URL = "https://icbcstore.almundo.com.ar/";
    public static final String API_PROD_URL = "http://apipr.almundo.it:8080/";
    public static final String API_STG_URL = "http://apist.almundo.it:8080/";
    public static final String APIKEY_URL = "http://abs.almundo.it:8080/abs/security/apikeys";
    public static final String TRIPS_URL = "http://apist.almundo.it:8080/api/trips";

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
        FELICITACIONES_V3_MSG("¡Felicitaciones, tu reserva fue exitosa!"),
        MANDATORY_FLD_MSG("Este campo es obligatorio."),
        LISTADO_DE_SUCURSALES_LNK("Ver listado de sucursales"),
        NO_PUDIMOS_PROCESAR("No pudimos procesar la consulta. Intentá más tarde."),
        PRUEBA_CON_OTRAS_FECHAS("Uy, no encontramos resultados. Puedes probar con otras fechas.");

        public final String message;

        Messages(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    public enum Results {
        PASSED("passed"),
        FAILED("failed");

        public final String result;

        Results(String result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return result;
        }
    }

    public enum Products {
        VUELOS("Vuelos"),
        HOTELES("Hoteles"),
        AUTOS("Autos");

        public final String product;

        Products(String product) {
            this.product = product;
        }

        @Override
        public String toString() {
            return product;
        }
    }

    //PAYMENTS

    public static final String RANDOM = "random";

    public static final String DEBIT = "debit";
    public static final String TWO_CARDS = "two_cards";
    public static final String DESTINATION = "destination";
    public static final String TODOPAGO = "todopago";
    public static final String PAGO_DIVIDIDO = "PAGO DIVIDIDO";
    public static final String LINK_DE_PAGO = "LINK DE PAGO";
    public static final String REWARDS = "rewards";
    public static final String CUSTOMER_EMAIL = "almundoturismo@gmail.com";

    public static final String VISA_1 = "1_visa_visa";
    public static final String VISA_3 = "3_visa_visa";
    public static final String VISA_3_TODOPAGO = "3_visa_todopago";
    public static final String CABAL_3_TODOPAGO = "3_cabal_todopago";
    public static final String MASTER_1 = "1_master_master";
    public static final String DESTINATION_MASTER_1 = "destination$1_master_master";
    public static final String LINK_VISA_1 = "link_de_pago$1_visa_visa";
    public static final String LINK_TWO_CARDS_VISA_1_MASTER_1 = "link_de_pago$two_cards$1_visa_visa$1_master_master$";
    public static final String LINK_REWARDS_VISA_1 = "link_de_pago$rewards$1_visa_visa$";
    public static final String AMEX_1 = "1_amex_amex";

    public static final String REWARDS_VISA_1 = "rewards$1_visa_visa";

    public static final String DEPOSIT = "deposit";
    public static final String TRANSFER = "transfer";

    public static final String VISA_MASTER = "pago_dividido$1_visa_visa$1_master_master$";
    public static final String MASTER_VISA = "pago_dividido$1_master_master$1_visa_visa$";

    public static final String TWOCARDS_VISA_MASTER = "two_cards$1_visa_visa$1_master_master$";

    public static final String VISA_AMEX = "pago_dividido$1_visa_visa$1_amex_amex$";
    public static final String AMEX_VISA = "pago_dividido$1_amex_amex$1_visa_visa$";

    public static final String DEPOSIT_VISA = "pago_dividido$deposit$1_visa_visa$";
    public static final String VISA_DEPOSIT = "pago_dividido$1_visa_visa$deposit$";

    public static final String CASH_DEPOSIT = "pago_dividido$cash$deposit$";
    public static final String CASH_TRANSFER = "pago_dividido$cash$transfer$";

    public static final String DEPOSIT_MASTER = "pago_dividido$deposit$1_master_master$";
    public static final String MASTER_DEPOSIT = "pago_dividido$1_master_master$deposit$";

    public static final String DEPOSIT_AMEX = "pago_dividido$deposit$1_amex_amex$";
    public static final String AMEX_DEPOSIT = "pago_dividido$1_amex_amex$deposit$";

    public static final String TRANSFER_VISA = "pago_dividido$transfer$1_visa_visa$";
    public static final String VISA_TRANSFER = "pago_dividido$1_visa_visa$transfer$";

    public static final String TRANSFER_MASTER = "pago_dividido$transfer$1_master_master$";
    public static final String MASTER_TRANSFER = "pago_dividido$1_master_master$transfer$";

    public static final String TRANSFER_AMEX = "pago_dividido$transfer$1_amex_amex$";
    public static final String AMEX_TRANSFER = "pago_dividido$1_amex_amex$transfer$";

    public static final String VISA_MASTER_AMEX = "pago_dividido$1_visa_visa$1_master_master$1_amex_amex$";
    public static final String AMEX_VISA_MASTER = "pago_dividido$1_amex_amex$1_visa_visa$1_master_master$";

    public static final String VISATP_VISA_AMEX = "pago_dividido$1_visa_todopago$1_visa_visa$1_amex_amex";

    public static final String VISA_DEBIT = "visa_debit";

    //PERCEPTIONS

    public static final String INCLUDE_AMOUNT = "Incluirlos en el importe";
    public static final String ADD_AMOUNT = "Sumarlos al importe";

    //BILLINGS
    public static final String LOCAL_BILLING = "local_Billing";
    public static final String LOCAL_BILLING_SUCURSALES = "local_Billing_sucursales";

    //CONTACTS
    public static final String CONTACT_CELL_PHONE = "contact_cell_phone";
    public static final String CONTACT_PHONE = "contact_phone";

    //CHECKOUT PAGES
    public static final String CARS_CHECKOUT = "CarsCheckOutPage";
    public static final String CARS_CHECKOUT_RET = "CarsCheckOutPageSucursal";

    public static final String FLIGHTS_CHECKOUT_INT = "FlightsCheckOutPageInternational";
    public static final String FLIGHTS_CHECKOUT_DOM = "FlightsCheckOutPageDomestic";

    public static final String FLIGHTS_CHECKOUT_DOM_RET = "FlightsCheckOutPageDomesticSucursal";
    public static final String FLIGHTS_CHECKOUT_INT_RET = "FlightsCheckOutPageInternationalSucursal";

    public static final String TRIPS_CHECKOUT_DOM_RET = "TripsCheckOutPageDomesticSucursal";
    public static final String TRIPS_CHECKOUT_INTV3 = "TripsCheckOutPageInternationalV3";
    public static final String TRIPS_CHECKOUT_DOMV3 = "TripsCheckOutPageDomesticV3";

    public static final String HOTELS_CHECKOUT_DOM = "HotelsCheckOutPageDomesticV3";
    public static final String HOTELS_CHECKOUT_INT = "HotelsCheckOutPageInternationalV3";

    public static final String HOTELS_CHECKOUT_INT_RET = "HotelsCheckOutPageInternationalSucursal";
    public static final String HOTELS_CHECKOUT_DOM_RET = "HotelsCheckOutPageDomesticSucursal";

    //PASSENGERS
    public static final String ADULT_MALE_NATIVE = "adult_male_native";
    public static final String ADULT_FEMALE_NATIVE = "adult_female_native";
    public static final String ADULT_MALE_FOREIGN = "adult_male_foreign";
    public static final String ADULT_FEMALE_FOREIGN = "adult_female_foreign";
    public static final String CHILD_MALE_NATIVE = "child_male_native";
    public static final String CHILD_FEMALE_NATIVE = "child_female_native";

    public static final String CHILD_FEMALE_NAT_TRIPS = "child_female_native_trips";
    public static final String CHILD_FEM_NAT_TRIPS_DOM = "child_female_native_trips_domestic";

    public static final String DOM01_15D_2A_1R = "domestic01_15days_2adults_1room";

    //COUNTRIES

    public static final String ARGENTINA = "ARGENTINA";
    public static final String COLOMBIA = "COLOMBIA";
    public static final String MEXICO = "MEXICO";

    //MESSAGES
    public static final String NO_DOMESTIC_CARS_COLOMBIA = "Apparently, in Colombia they don't rent cars Domestic test is not running and we just set it passed";
    public static final String NO_DOMESTIC_CARS_COLOMBIA_TICKET = "This isssue was reported ticket is: " + "https://almundo.atlassian.net/browse/CARS-444";
    public static final String NOT_RUNNING_MEXICO = "We are not running this for MEXICO!";
    public static final String NOT_RUNNING_MEXICO_COLOMBIA = "We are not running this for MEXICO and COLOMBIA!";
    public static final String NOT_RUNNING_MEXICO_ARGENTINA = "We are not running this for MEXICO and ARGENTINA!";

    //ITINERARIES
    //CARS
    public static final String CAP_10D_21_24 = "capital_10days_entre_21_24";
    public static final String MIA_10D_21_24 = "miami_10days_entre_21_24";

    //FLIGHTS

    public static final String DOMESTIC_30D_2A_ALL = "domestic_30days_2adults_todas";
    public static final String DOMESTIC_20D_2A_ALL = "domestic_20days_2adults_todas";
    public static final String MIAMI_10D_2A_TOURIST = "miami_10days_2adults_turista";
    public static final String MIAMI_10D_2A_2C_TOURIST = "miami_10days_2adults_2childs_turista";
    public static final String MULTI_3LEGS_2A_ALL = "multiDest_3Flights_2adults_todas";

    //HOTELS

    public static final String MIA_10D_2A_1R = "miami_10days_2adults_1room";
    public static final String DOM02_20D_2A_1R = "domestic02_20days_2adults_1room";
    public static final String DOM03_20D_2A_1R = "domestic03_20days_2adults_1room";

    //TRIPS
    public static final String INT02_20D_2A_1R = "int02_20days_2adults_1room";
    public static final String DOM02_20D_2A_1C_1R = "domestic02_20days_2adults_1childs_1room";
    public static final String MIA_10D_2A_2C_1R = "miami_10days_2adults_2childs_1room";
    public static final String DOM01_15D_2A_1C_1R = "domestic01_15days_2adults_1childs_1room";

    //PACKAGES
    public static final String PKG_10D_2A = "10days_2adults";


    //LISTS
    public static final List<String> FLIGHT_TYPE_LIST = asList("Ida y vuelta", "Solo ida", "Varias ciudades");
    public static final List<String> FLIGHT_CLASS_LIST = asList("Todas", "Primera", "Business", "Premium", "Turista");
    public static final List<String> CHILD_RANGE_LIST = asList("Menos de 2 años en brazos", "Entre 2 y 11 años", "Más de 11 años");
    public static final List<String> USER_MENU_LIST_AR = asList("Perfil", "Medios de pago", "Reservas", "Mis gustos", "Mis puntos", "Cerrar sesión");
    public static final List<String> USER_MENU_LIST_CO = asList("Perfil", "Medios de pago", "Reservas", "Cerrar sesión");
    public static final List<String> USER_MENU_LIST_MX = asList("Perfil", "Medios de pago", "Reservas", "Cerrar sesión");

    public final static int FIRST_OPTION = 0;

    public final static int FIRST_OPTION_O1 = 1;

    //INCONS
    public static final String TRIPS_ICO = "Vuelo+Hotel icon";

    //MISC

    public static final String AGENT_EMAIL = "gabriel.cespedes@almundo.com";

    //CSS ATTRIBUTES
    public static final String VALUE = "value";

}