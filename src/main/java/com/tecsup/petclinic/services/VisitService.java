package com.tecsup.petclinic.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;

public interface VisitService {

    // Crear una nueva visita
    Visit create(Visit visit);

    // Actualizar una visita existente
    Visit update(Visit visit) throws VisitNotFoundException;

    // Buscar visita por ID
    Optional<Visit> findById(int id) throws VisitNotFoundException;

    // Buscar visitas por petId
    List<Visit> findByPetId(int petId);

    // Buscar visitas por fecha
    List<Visit> findByVisitDate(LocalDate visitDate);

    // Eliminar visita por ID
    void delete(int id) throws VisitNotFoundException;

    // Obtener todas las visitas
    List<Visit> findAll();
}