package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j

@SpringBootTest
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Autowired
    private PetRepository petRepository;

    @Test
    public void testCreateVisit() {
        // Crear una nueva visita asociada a un Pet existente
        Pet pet = petRepository.findById(1).orElse(null); // Asegúrate de que existe un Pet con ID 1 en la base de datos
        assertNotNull(pet, "Pet con ID 1 no existe para asociar la visita");

        Visit visit = new Visit(LocalDate.now(), "Consulta general", pet);
        Visit createdVisit = visitService.create(visit);

        assertNotNull(createdVisit.getId());
        assertEquals("Consulta general", createdVisit.getDescription());
        assertEquals(pet.getId(), createdVisit.getPet().getId());
    }

    @Test
    public void testFindVisitById() throws VisitNotFoundException {
        // Crear una nueva visita para luego buscarla
        Pet pet = petRepository.findById(1).orElse(null);
        Visit visit = new Visit(LocalDate.now(), "Revisión anual", pet);
        Visit createdVisit = visitService.create(visit);

        Optional<Visit> foundVisit = visitService.findById(createdVisit.getId());
        assertTrue(foundVisit.isPresent());
        assertEquals("Revisión anual", foundVisit.get().getDescription());
    }

    @Test
    public void testUpdateVisit() throws VisitNotFoundException {
        // Crear y actualizar una visita
        Pet pet = petRepository.findById(1).orElse(null);
        Visit visit = new Visit(LocalDate.now(), "Vacunación", pet);
        Visit createdVisit = visitService.create(visit);

        createdVisit.setDescription("Vacunación actualizada");
        Visit updatedVisit = visitService.update(createdVisit);

        assertEquals("Vacunación actualizada", updatedVisit.getDescription());
    }
    @Test
    public void testDeleteVisit() throws VisitNotFoundException {
        // Obtener la mascota con ID 28
        Pet pet = petRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // ------------ Create Visit ---------------

        Visit visit = new Visit(LocalDate.now(), "Desparasitacion", pet);
        Visit createdVisit = visitService.create(visit);
        log.info("Created Visit: " + createdVisit);

        // ------------ Delete Visit ---------------

        try {
            visitService.delete(createdVisit.getId());
            log.info("Visit deleted successfully.");
        } catch (VisitNotFoundException e) {
            fail("Failed to delete visit: " + e.getMessage());
        }

        // ------------ Validation ---------------

        assertThrows(VisitNotFoundException.class, () -> {
            visitService.findById(createdVisit.getId());
        }, "Visit should not be found after deletion.");
    }

}
