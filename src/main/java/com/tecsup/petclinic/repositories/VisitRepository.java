package com.tecsup.petclinic.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Visit;

/**
 * Repositorio para la entidad Visit.
 *
 * @author jgomezm
 *
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {

    // Obtener visitas por petId
    List<Visit> findByPetId(int petId);

    // Obtener visitas por fecha
    List<Visit> findByVisitDate(LocalDate visitDate);

    @Override
    List<Visit> findAll();
}