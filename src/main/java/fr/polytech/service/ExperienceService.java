package fr.polytech.service;

import fr.polytech.model.Experience;
import fr.polytech.model.ExperienceDTO;
import fr.polytech.repository.ExperienceRepository;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExperienceService {

    /**
     * Initialize the logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ExperienceService.class);

    @Autowired
    private ExperienceRepository experienceRepository;

    /**
     * Get all experiences.
     *
     * @return List of all experiences.
     */
    public List<Experience> getAllExperiences() {
        logger.info("Getting all experiences");
        return experienceRepository.findAll();
    }

    /**
     * Get experience by id.
     *
     * @param id Experience id.
     * @return Experience with the specified id.
     * @throws NotFoundException If the experience is not found.
     */
    public Experience getExperienceById(UUID id) throws NotFoundException {
        logger.info("Getting experience with id " + id);
        Experience experience = experienceRepository.findById(id).orElse(null);

        if (experience == null) {
            logger.error("Error while getting an experience: experience not found");
            // If the experience is not found, throw an exception
            throw new NotFoundException("Experience not found");
        }

        logger.debug("Returning experience with id " + id);
        return experience;
    }

    /**
     * Create an experience.
     *
     * @param experience Experience to create.
     * @return Created experience.
     */
    public Experience createExperience(ExperienceDTO experience) {
        logger.info("Creating experience");

        Experience newExperience = new Experience();
        newExperience.setCompanyId(experience.getCompanyId());
        newExperience.setJobTitle(experience.getJobTitle());
        newExperience.setJobCategoryId(experience.getJobCategoryId());
        newExperience.setStartDate(experience.getStartDate());
        newExperience.setEndDate(experience.getEndDate());

        return experienceRepository.save(newExperience);
    }

    /**
     * Update an experience.
     *
     * @param experience Experience to update.
     * @return Updated experience.
     * @throws NotFoundException If the experience is not found.
     */
    public Experience updateExperience(ExperienceDTO experience) throws NotFoundException {
        logger.info("Updating experience with id " + experience.getId());

        Experience updatedExperience = experienceRepository.findById(experience.getId()).orElse(null);

        if (updatedExperience == null) {
            logger.error("Error while updating an experience: experience not found");
            // If the experience is not found, throw an exception
            throw new NotFoundException("Experience not found");
        }

        updatedExperience.setCompanyId(experience.getCompanyId());
        updatedExperience.setJobTitle(experience.getJobTitle());
        updatedExperience.setJobCategoryId(experience.getJobCategoryId());
        updatedExperience.setStartDate(experience.getStartDate());
        updatedExperience.setEndDate(experience.getEndDate());

        return experienceRepository.save(updatedExperience);
    }

    /**
     * Delete an experience.
     *
     * @param id Experience id.
     * @throws NotFoundException If the experience is not found.
     */
    public void deleteExperience(UUID id) throws NotFoundException {
        logger.info("Deleting experience with id " + id);

        Experience experience = getExperienceById(id);

        if (experience == null) {
            logger.error("Error while deleting an experience: experience not found");
            // If the experience is not found, throw an exception
            throw new NotFoundException("Experience not found");
        }

        logger.debug("Deleting experience with id " + id);
        experienceRepository.delete(experience);
    }
}
