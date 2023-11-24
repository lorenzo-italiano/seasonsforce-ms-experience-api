package fr.polytech.service;

import fr.polytech.model.*;
import fr.polytech.repository.ExperienceRepository;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobCategoryService jobCategoryService;

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

        checkAttributes(experience);

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

        checkAttributes(experience);

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
     * Check if the experience has all the required attributes.
     *
     * @param experience Experience to check.
     * @throws HttpClientErrorException If the experience does not have all the required attributes.
     */
    private void checkAttributes(ExperienceDTO experience) throws HttpClientErrorException {
        if (experience.getJobTitle() == null || experience.getCompanyId() == null || experience.getJobCategoryId() == null || experience.getStartDate() == null || experience.getEndDate() == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing attributes");
        }
        if (experience.getStartDate().after(experience.getEndDate())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Start date must be before end date");
        }
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

    public DetailedExperienceDTO getDetailedExperienceById(UUID id, String token) {
        Experience experience = getExperienceById(id);

        if (experience == null) {
            logger.error("Error while getting an experience: experience not found");
            // If the experience is not found, throw an exception
            throw new NotFoundException("Experience not found");
        }

        DetailedExperienceDTO detailedExperience = new DetailedExperienceDTO();

        detailedExperience.setId(experience.getId());
        detailedExperience.setJobTitle(experience.getJobTitle());
        detailedExperience.setStartDate(experience.getStartDate());
        detailedExperience.setEndDate(experience.getEndDate());

        CompanyDTO companyById = companyService.getCompanyById(experience.getCompanyId(), token);

        if (companyById == null) {
            logger.error("Error while getting a company: company not found");
            // If the company is not found, throw an exception
            throw new NotFoundException("Company not found");
        }

        detailedExperience.setCompany(companyById);

        JobCategoryDTO jobCategoryById = jobCategoryService.getJobCategoryById(experience.getJobCategoryId(), token);

        if (jobCategoryById == null) {
            logger.error("Error while getting a job category: job category not found");
            // If the job category is not found, throw an exception
            throw new NotFoundException("Job category not found");
        }

        detailedExperience.setJobCategory(jobCategoryById);

        return detailedExperience;
    }
}
