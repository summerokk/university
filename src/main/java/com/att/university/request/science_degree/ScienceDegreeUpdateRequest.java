package com.att.university.request.science_degree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScienceDegreeUpdateRequest {
    private Integer id;
    private String name;
}
