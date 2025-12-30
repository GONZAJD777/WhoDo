package com.example.whodo.app.resources.parameters;

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
public class Parameter {
    private String id;
    private String value;
    private String description;
    private String type;
    private Integer status;
}

