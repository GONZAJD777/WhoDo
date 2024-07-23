package com.example.whodo.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    private static final String TAG = "UTILS";

    @SuppressLint("SimpleDateFormat")
    public static Date setStringToDate (String mString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm'hs'");
        Date parsedDate = new Date();
        try {
            parsedDate = dateFormat.parse(mString);
            // Usa parsedDate como necesites
        } catch (ParseException e) {
            Log.d(TAG, "setStringToDate : " + e);
            // Maneja el error si la cadena no tiene el formato esperado
        }
        return parsedDate;
    }
    @SuppressLint("SimpleDateFormat")
    public static String setLongToDate(Long mLong) {
        // Define el formato de entrada (Long)
        SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = null;
        try {
            // Parsea la fecha Long a un objeto Date
            Date date = longDateFormat.parse(String.valueOf(mLong));
            // Define el formato deseado para la salida (puedes ajustarlo según tus necesidades)
            SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm'hs'");
            //SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH:mm'hs'", new Locale("es", "ES"));
            // Formatea la fecha a la representación deseada
            formattedDate = desiredDateFormat.format(date);
        } catch (ParseException e) {
            Log.d(TAG, "setLongToDate : " + e);
        }
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static Long setDateToLong(Date mDate) {
        // Define el formato deseado (YYYYMMDD24HHMMSS)
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // Formatea la fecha y hora
        long formattedDate = Long.parseLong(dateFormat.format(mDate));
        // Imprime el resultado
        System.out.println("Fecha y hora actual en formato YYYYMMDD24HHMMSS: " + formattedDate);
        return formattedDate;
    }

    public static Date increseDate(int pDays, Date pDate) {
        // Crea un objeto Calendar y establece la fecha actual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pDate);
        // Suma un día
        calendar.add(Calendar.DAY_OF_YEAR, pDays);
        // Obtiene la fecha resultante
        Date newDate = calendar.getTime();
        // Imprime el resultado
        System.out.println("Fecha actual: " + pDate);
        System.out.println("Fecha con un día agregado: " + newDate);
        return newDate;
    }


}
