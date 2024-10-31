package com.tecsup.petclinic.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="visits")
@Data
public class Visits {
    @Id
}

