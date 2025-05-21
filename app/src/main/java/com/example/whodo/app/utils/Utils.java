package com.example.whodo.app.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    private static final String TAG = "UTILS";

    public static String getISOLocalDate(){
        TimeZone timeZone = TimeZone.getDefault();
        ZoneId zonaHoraria = timeZone.toZoneId();

        // Obtener la fecha y hora actual en la zona horaria especificada
        ZonedDateTime fechaLocal = ZonedDateTime.now(zonaHoraria);

        // Formatear la fecha en formato ISO 8601 con offset de zona horaria
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String fechaFormateada = fechaLocal.format(formatter);

        // Mostrar la fecha formateada
        System.out.println("Fecha local en formato ISO 8601 (UTC± zona horaria): " + fechaFormateada);

        return fechaFormateada;
    }

    public static String creationDateParse(long timestamp) {
        ZonedDateTime zonedDateTime = Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault()); // Usa la zona horaria del sistema
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")); // 🛠️ Usa XXX en lugar de XX
    }



    public static String convertToUTC(String localDateTime) {
        // Parsear la fecha local proporcionada en formato ISO 8601 con zona horaria
        ZonedDateTime fechaLocal = ZonedDateTime.parse(localDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // Convertir la fecha local a UTC
        ZonedDateTime fechaUTC = fechaLocal.withZoneSameInstant(ZoneOffset.UTC);

        // Formatear la fecha en formato ISO 8601 para UTC
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String fechaFormateadaUTC = fechaUTC.format(formatter);

        // Mostrar la fecha UTC formateada
        System.out.println("Fecha en formato ISO 8601 (UTC): " + fechaFormateadaUTC);

        return fechaFormateadaUTC;
    }


    public static String getISOLocalDatePlus(int pDays,String pDate) {

        ZonedDateTime fechaHoraZoned = ZonedDateTime.parse(pDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        // Obtener la fecha y hora actual en la zona horaria local
        //ZonedDateTime fechaLocal = ZonedDateTime.now(ZoneId.systemDefault());

        // Incrementar la fecha en 1 día
        ZonedDateTime fechaIncrementada = fechaHoraZoned.plusDays(pDays);

        // Formatear la fecha en formato ISO 8601 (UTC± zona horaria)
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String fechaFormateada = fechaIncrementada.format(formatter);

        return fechaFormateada;
    }

    public static Date parseISODate(String isoDateString) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return Date.from(zonedDateTime.toInstant()); // Convertir a Date
    }

    //Conviernte una fecha en formatos simples con hora separada a formato ISO
    //***********************************************************
    public static String getISOLocalDateFromString(String pDate, String pTime) {
        // Detectar el formato de la fecha ingresada
        DateTimeFormatter formatterEntrada;
        if (pDate.contains("-")) {
            formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else if (pDate.contains("/")) {
            formatterEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        } else if (pDate.length()==8) {
            formatterEntrada = DateTimeFormatter.ofPattern("yyyyMMdd");
        }
        else {
            throw new IllegalArgumentException("Formato de fecha no reconocido: " + pDate);
        }

        // Parsear la cadena a LocalDate usando el formato detectado
        LocalDate fecha = LocalDate.parse(pDate, formatterEntrada);

        // Combinar LocalDate y LocalTime en LocalDateTime
        LocalTime mHora = LocalTime.parse(pTime);
        LocalDateTime fechaHora = LocalDateTime.of(fecha, mHora);

        // Convertir LocalDateTime a ZonedDateTime en la zona horaria local
        ZonedDateTime fechaHoraZoned = fechaHora.atZone(ZoneId.systemDefault());

        // Formatear la fecha y hora en formato ISO 8601
        DateTimeFormatter formatterSalida = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return fechaHoraZoned.format(formatterSalida);
    }
    //Entada 2023-08-26T02:15:47-03:00 --> Salida 26/08/2023
    public static String getDateFromISO(String pISODate) {
        return OffsetDateTime.parse(pISODate, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    // Tranforma una ISODATE 2023-08-26T02:15:47-03:00  a un formato amigable
    public static String getISOtoDate(String pISODate) {
        // Cadena en formato ISO_OFFSET_DATE_TIME
        String isoDateTime = "2023-08-26T02:15:47-03:00";

        // Parsear la cadena
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(pISODate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // Formatear la fecha y hora en un estilo más amigable con formato de 24 horas
        DateTimeFormatter formatterAmigable = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm'Hs'");
        String fechaAmigable = offsetDateTime.format(formatterAmigable);

        // Imprimir la fecha formateada
        System.out.println(fechaAmigable);

        return fechaAmigable;
    }

    //Compara 2 fechas y devuelve la mas grande
    public static String getBiggerISODate(String mDate1, String mDate2){
            // Parsear las cadenas
            OffsetDateTime offsetDateTime1 = OffsetDateTime.parse(mDate1, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            OffsetDateTime offsetDateTime2 = OffsetDateTime.parse(mDate2, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            // Comparar las fechas
            if (offsetDateTime1.isAfter(offsetDateTime2)) {
                System.out.println("La primera fecha es mayor.");
                return mDate1;
            } else if (offsetDateTime1.isBefore(offsetDateTime2)) {
                System.out.println("La segunda fecha es mayor.");
                return mDate2;
            } else {
                System.out.println("Ambas fechas son iguales.");
                return mDate1;
            }
    }
    //Compara 2 fechas isThisDate con AfterThisDate y devuelve true si es mayor
    public static Boolean isAfter(String isThisDate,String AfterThisDate){
        OffsetDateTime offsetDateTime1 = OffsetDateTime.parse(isThisDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime offsetDateTime2 = OffsetDateTime.parse(AfterThisDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        if (offsetDateTime1.isAfter(offsetDateTime2)) {
            return true;
        } else {
            return false;
        }
    }
}
