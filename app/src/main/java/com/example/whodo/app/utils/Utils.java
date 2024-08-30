package com.example.whodo.app.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
    //***********************************************************
    public static String getISOLocalDateFromString(String pDate, String pTime){
        // Parsear las cadenas a LocalDate y LocalTime
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(pDate, formatterEntrada);

        // Formatear la fecha a YYYY-MM-DD
        DateTimeFormatter formatterSalida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fecha.format(formatterSalida);

        LocalDate mFecha = LocalDate.parse(fechaFormateada);
        LocalTime mHora = LocalTime.parse(pTime);

        // Combinar LocalDate y LocalTime en LocalDateTime
        LocalDateTime fechaHora = LocalDateTime.of(mFecha, mHora);

        // Convertir LocalDateTime a ZonedDateTime en la zona horaria local
        ZonedDateTime fechaHoraZoned = fechaHora.atZone(ZoneId.systemDefault());

        // Formatear la fecha y hora en formato ISO 8601
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String fechaHoraFormateada = fechaHoraZoned.format(formatter);
        return fechaHoraFormateada;
    }

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

    public static Boolean isAfter(String mDate1,String mDate2){
        OffsetDateTime offsetDateTime1 = OffsetDateTime.parse(mDate1, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime offsetDateTime2 = OffsetDateTime.parse(mDate2, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        if (offsetDateTime1.isAfter(offsetDateTime2)||offsetDateTime1.isEqual(offsetDateTime2)) {
            return true;
        } else {
            return false;
        }
    }
}
