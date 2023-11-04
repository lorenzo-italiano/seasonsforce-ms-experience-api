package fr.polytech.restcontroller;

import fr.polytech.model.Experience;
import fr.polytech.model.ExperienceDTO;
import fr.polytech.service.ExperienceService;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/experience")
public class ExperienceController {

    /**
     * Initialize the logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ExperienceController.class);

    @Autowired
    private ExperienceService experienceService;

    /**
     * Get all experiences.
     *
     * @return List of all experiences.
     */
    @GetMapping("/")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<List<Experience>> getAllExperiences() {
        try {
            List<Experience> experiences = experienceService.getAllExperiences();
            logger.info("Got all experiences");
            return ResponseEntity.ok(experiences);
        } catch (Exception e) {
            logger.error("Error while getting all experiences: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get experience by id.
     *
     * @param id Experience id.
     * @return Experience with the specified id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable("id") UUID id) {
        try {
            Experience experience = experienceService.getExperienceById(id);
            logger.info("Got experience with id " + id);
            return ResponseEntity.ok(experience);
        } catch (NotFoundException e) {
            logger.error("Error while getting experience with id " + id + ": " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create an experience.
     *
     * @param experience Experience to create.
     * @return Created experience.
     */
    @PostMapping("/")
    @PreAuthorize("hasRole('client_candidate')")
    public ResponseEntity<Experience> createExperience(@RequestBody ExperienceDTO experience) {
        try {
            Experience createdExperience = experienceService.createExperience(experience);
            logger.info("Created experience with id " + createdExperience.getId());
            return ResponseEntity.ok(createdExperience);
        } catch (Exception e) {
            logger.error("Error while creating experience: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an experience.
     *
     * @param experience Experience to update.
     * @return Updated experience.
     */
    @PutMapping("/")
    @PreAuthorize("hasRole('client_candidate')")
    public ResponseEntity<Experience> updateExperience(@RequestBody ExperienceDTO experience) {
        try {
            Experience updatedExperience = experienceService.updateExperience(experience);
            logger.info("Updated experience with id " + experience.getId());
            return ResponseEntity.ok(updatedExperience);
        } catch (NotFoundException e) {
            logger.error("Error while updating experience with id " + experience.getId() + ": " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an experience.
     *
     * @param id Experience id.
     * @return True if the experience has been deleted, false otherwise.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('client_candidate')")
    public ResponseEntity<Boolean> deleteExperience(@PathVariable("id") UUID id) {
        try {
            experienceService.deleteExperience(id);
            logger.info("Deleted experience with id " + id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Error while deleting experience with id " + id + ": " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
