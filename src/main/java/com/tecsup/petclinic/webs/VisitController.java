package com.tecsup.petclinic.webs;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.mapper.VisitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.petclinic.domain.VisitTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.services.VisitService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class VisitController {

    private VisitService visitService;
    private VisitMapper mapper;

    public VisitController(VisitService visitService, VisitMapper mapper){
        this.visitService = visitService;
        this.mapper = mapper;
    }

    /**
     * Get all visits
     *
     * @return
     */
    @GetMapping(value = "/visits")
    public ResponseEntity<List<VisitTO>> findAllVisits() {
        List<Visit> visits = visitService.findAll();
        log.info("visits: " + visits);
        List<VisitTO> visitsTO = this.mapper.toVisitTOList(visits);
        return ResponseEntity.ok(visitsTO);
    }

    /**
     * Create a new visit
     *
     * @param visitTO
     * @return
     */
    @PostMapping(value = "/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VisitTO> create(@RequestBody VisitTO visitTO) {

        Visit newVisit = this.mapper.toVisit(visitTO);
        Visit createdVisit = visitService.create(newVisit);
        VisitTO newVisitTO = this.mapper.toVisitTO(createdVisit);

        return ResponseEntity.status(HttpStatus.CREATED).body(newVisitTO);
    }

    /**
     * Find visit by id
     *
     * @param id
     * @return
     * @throws VisitNotFoundException
     */
    /**
     * Find visit by id
     *
     * @param id
     * @return
     * @throws VisitNotFoundException
     */
    @GetMapping(value = "/visits/{id}")
    public ResponseEntity<VisitTO> findById(@PathVariable Integer id) {

        VisitTO visitTO = null;

        try {
            Visit visit = visitService.findById(id).orElseThrow();
            visitTO = this.mapper.toVisitTO(visit);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(visitTO);
    }
    /**
     * Update visit
     *
     * @param visitTO
     * @param id
     * @return
     */
    /**
     * Update visit by id
     *
     * @param visitTO
     * @param id
     * @return
     */
    @PutMapping(value = "/visits/{id}")
    public ResponseEntity<VisitTO> update(@RequestBody VisitTO visitTO, @PathVariable Integer id) {

        VisitTO updatedVisitTO = null;

        try {
            Visit existingVisit = visitService.findById(id).orElseThrow();

            existingVisit.setVisitDate(visitTO.getVisitDate());
            existingVisit.setDescription(visitTO.getDescription());

            Pet pet = new Pet();
            pet.setId(visitTO.getPetId());
            existingVisit.setPet(pet);

            visitService.update(existingVisit);

            updatedVisitTO = this.mapper.toVisitTO(existingVisit);

        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedVisitTO);
    }
    /**
     * Delete visit by id
     *
     * @param id
     */
    @DeleteMapping(value = "/visits/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            visitService.delete(id);
            return ResponseEntity.ok("Visit deleted with ID: " + id);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find visits by petId
     *
     * @param petId
     * @return
     */
    @GetMapping(value = "/visits/pet/{petId}")
    public ResponseEntity<List<VisitTO>> findByPetId(@PathVariable Integer petId) {
        List<Visit> visits = visitService.findByPetId(petId);
        List<VisitTO> visitsTO = this.mapper.toVisitTOList(visits);
        return ResponseEntity.ok(visitsTO);
    }

    /**
     * Find visits by date
     *
     * @param date
     * @return
     */
    @GetMapping(value = "/visits/date/{date}")
    public ResponseEntity<List<VisitTO>> findByVisitDate(@PathVariable String date) {
        LocalDate visitDate = LocalDate.parse(date);
        List<Visit> visits = visitService.findByVisitDate(visitDate);
        List<VisitTO> visitsTO = this.mapper.toVisitTOList(visits);
        return ResponseEntity.ok(visitsTO);
    }
}