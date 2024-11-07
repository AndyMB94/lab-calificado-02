package com.tecsup.petclinic.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Cardenas
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VisitTO {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;

    private String description;

    private Integer petId;

}