package com.tecsup.petclinic.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.repositories.VisitRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para gestionar visitas.
 *
 * @author jgomezm
 *
 */
@Service
@Slf4j
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    /**
     * Crear una nueva visita.
     *
     * @param visit
     * @return
     */
    @Override
    public Visit create(Visit visit) {
        return visitRepository.save(visit);
    }

    /**
     * Actualizar una visita existente.
     *
     * @param visit
     * @return
     * @throws VisitNotFoundException
     */
    @Override
    public Visit update(Visit visit) throws VisitNotFoundException {
        if (!visitRepository.existsById(visit.getId())) {
            throw new VisitNotFoundException("Visit not found with id: " + visit.getId());
        }
        return visitRepository.save(visit);
    }

    /**
     * Eliminar una visita por ID.
     *
     * @param id
     * @throws VisitNotFoundException
     */
    @Override
    public void delete(int id) throws VisitNotFoundException {
        Visit visit = findById(id).orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));
        visitRepository.delete(visit);
    }

    /**
     * Buscar visita por ID.
     *
     * @param id
     * @return
     * @throws VisitNotFoundException
     */
    @Override
    public Optional<Visit> findById(int id) throws VisitNotFoundException {
        return Optional.ofNullable(visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id)));
    }

    /**
     * Buscar visitas por petId.
     *
     * @param petId
     * @return
     */
    @Override
    public List<Visit> findByPetId(int petId) {
        List<Visit> visits = visitRepository.findByPetId(petId);
        visits.forEach(visit -> log.info(visit.toString()));
        return visits;
    }

    /**
     * Buscar visitas por fecha.
     *
     * @param visitDate
     * @return
     */
    @Override
    public List<Visit> findByVisitDate(LocalDate visitDate) {
        List<Visit> visits = visitRepository.findByVisitDate(visitDate);
        visits.forEach(visit -> log.info(visit.toString()));
        return visits;
    }

    /**
     * Obtener todas las visitas.
     *
     * @return
     */
    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }
}