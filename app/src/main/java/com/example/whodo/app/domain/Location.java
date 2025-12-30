package com.example.whodo.app.domain;

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
public class Location {
    private Double latitude;
    private Double longitude;
    private String geohash; // Ahora correctamente agrupado con la ubicación
}
