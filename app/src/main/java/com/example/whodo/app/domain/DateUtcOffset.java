package com.example.whodo.app.domain;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateUtcOffset {
    private String dateUtc; // Fecha almacenada en UTC
    private String timeZoneOffset; // Desplazamiento horario en formato ±hh:mm

    public void mapStringToDateUtcOffset(String isoDate) {
        try {
            System.out.println("Date To Parse send --> " + isoDate);

            // Parseamos usando java.time
            OffsetDateTime odt = OffsetDateTime.parse(isoDate);
            OffsetDateTime utcDate = odt.withOffsetSameInstant(ZoneOffset.UTC);

            // Formateamos la fecha en formato UTC con milisegundos y zona horaria
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            this.dateUtc = utcDate.format(formatter);

            // Offset original, por ejemplo "+00:00"
            this.timeZoneOffset = odt.getOffset().toString();

            System.out.println("Formatted Date UTC --> " + this.dateUtc);
            System.out.println("Offset --> " + this.timeZoneOffset);

        } catch (Exception e) {
            throw new RuntimeException("Error al convertir la fecha: " + e.getMessage());
        }
    }
}
