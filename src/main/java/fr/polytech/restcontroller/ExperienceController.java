package fr.polytech.restcontroller;

import fr.polytech.annotation.IsAdmin;
import fr.polytech.annotation.IsCandidate;
import fr.polytech.annotation.IsCandidateOrUserManager;
import fr.polytech.model.DetailedExperienceDTO;
import fr.polytech.model.Experience;
import fr.polytech.model.ExperienceDTO;
import fr.polytech.service.ExperienceService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
    @IsAdmin
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Experience>> getAllExperiences() {
        try {
            List<Experience> experiences = experienceService.getAllExperiences();
            logger.info("Got all experiences");
            return ResponseEntity.ok(experiences);
        } catch (HttpClientErrorException e) {
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
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Experience> getExperienceById(@PathVariable("id") UUID id) {
        try {
            Experience experience = experienceService.getExperienceById(id);
            logger.info("Got experience with id " + id);
            return ResponseEntity.ok(experience);
        } catch (HttpClientErrorException e) {
            logger.error("Error while getting experience with id " + id + ": " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get detailed experience by id.
     *
     * @param id Experience id.
     * @return Experience with the specified id.
     */
    @GetMapping("/detailed/{id}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailedExperienceDTO> getDetailedExperienceById(@PathVariable("id") UUID id, @RequestHeader("Authorization") String token) {
        try {
            DetailedExperienceDTO experience = experienceService.getDetailedExperienceById(id, token);
            logger.info("Got experience with id " + id);
            return ResponseEntity.ok(experience);
        } catch (HttpClientErrorException e) {
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
    @IsCandidateOrUserManager
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Experience> createExperience(@RequestBody ExperienceDTO experience) {
        try {
            Experience createdExperience = experienceService.createExperience(experience);
            logger.info("Created experience with id " + createdExperience.getId());
            return ResponseEntity.ok(createdExperience);
        } catch (HttpClientErrorException e) {
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
    @IsCandidate
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Experience> updateExperience(@RequestBody ExperienceDTO experience) {
        try {
            Experience updatedExperience = experienceService.updateExperience(experience);
            logger.info("Updated experience with id " + experience.getId());
            return ResponseEntity.ok(updatedExperience);
        } catch (HttpClientErrorException e) {
            logger.error("Error while updating experience with id " + experience.getId() + ": " + e.getMessage());
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }

    /**
     * Delete an experience.
     *
     * @param id Experience id.
     * @return True if the experience has been deleted, false otherwise.
     */
    @DeleteMapping("/{id}")
    @IsCandidate
    @Produces(MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Boolean> deleteExperience(@PathVariable("id") UUID id) {
        try {
            experienceService.deleteExperience(id);
            logger.info("Deleted experience with id " + id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            logger.error("Error while deleting experience with id " + id + ": " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
