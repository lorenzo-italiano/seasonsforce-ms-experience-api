package fr.polytech.service;

import fr.polytech.model.Experience;
import fr.polytech.model.ExperienceDTO;
import fr.polytech.repository.ExperienceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ExperienceServiceTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceService experienceService;

    /**
     * Test that the method returns a list of experiences.
     */
    @Test
    public void testGetAllExperiences() {
        experienceRepository.save(new Experience()); // Save some dummy data
        experienceRepository.save(new Experience());

        List<Experience> result = experienceService.getAllExperiences();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    /**
     * Test that the method returns an experience.
     */
    @Test
    public void testGetExperienceById() {
        Experience savedExperience = experienceRepository.save(new Experience());

        Experience result = experienceService.getExperienceById(savedExperience.getId());
        assertNotNull(result);
        assertEquals(savedExperience.getId(), result.getId());
    }

    /**
     * Test that the method throws an exception when the experience is not found.
     */
    @Test
    public void testGetExperienceByIdWithInvalidId() {
        // Check that an exception is thrown with status code 404
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> experienceService.getExperienceById(UUID.randomUUID()));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * Test that the method creates an experience.
     */
    @Test
    public void testCreateExperience() {
        ExperienceDTO experience = new ExperienceDTO();
        experience.setCompanyId(UUID.randomUUID());
        experience.setStartDate(new Date());
        experience.setJobCategoryId(UUID.randomUUID());
        // Set the end date to one second after the start date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(experience.getStartDate());
        calendar.add(Calendar.SECOND, 1);
        experience.setEndDate(calendar.getTime());
        experience.setJobTitle("jobTitle");

        Experience result = experienceService.createExperience(experience);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(experience.getCompanyId(), result.getCompanyId());
    }

    /**
     * Test that the method throws an exception when the end date is before the start date.
     */
    @Test
    public void testCreateExperienceWithEndDateBeforeStartDate() {
        ExperienceDTO experience = new ExperienceDTO();
        experience.setCompanyId(UUID.randomUUID());
        experience.setStartDate(new Date());
        experience.setJobCategoryId(UUID.randomUUID());
        // Set the end date to one second before the start date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(experience.getStartDate());
        calendar.add(Calendar.SECOND, -1);
        experience.setEndDate(calendar.getTime());
        experience.setJobTitle("jobTitle");

        // Check that an exception is thrown with status code 400
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> experienceService.createExperience(experience));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * Test that the method throws an exception when the experience does not have all the required attributes.
     */
    @Test
    public void testCreateExperienceWithMissingAttributes() {
        ExperienceDTO experience = new ExperienceDTO();
        experience.setCompanyId(UUID.randomUUID());
        experience.setStartDate(new Date());
        experience.setJobCategoryId(UUID.randomUUID());
        experience.setJobTitle("jobTitle");

        // Check that an exception is thrown with status code 400
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> experienceService.createExperience(experience));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * Test that the method updates an experience.
     */
    @Test
    public void testUpdateExperience() {
        Experience savedExperience = experienceRepository.save(new Experience());

        ExperienceDTO experience = new ExperienceDTO();
        experience.setId(savedExperience.getId());
        experience.setCompanyId(UUID.randomUUID());
        experience.setStartDate(new Date());
        experience.setJobCategoryId(UUID.randomUUID());
        // Set the end date to one second after the start date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(experience.getStartDate());
        calendar.add(Calendar.SECOND, 1);
        experience.setEndDate(calendar.getTime());
        experience.setJobTitle("jobTitle");

        Experience result = experienceService.updateExperience(experience);
        assertNotNull(result);
        assertEquals(experience.getCompanyId(), result.getCompanyId());
    }

    /**
     * Test that the method throws an exception when the end date is before the start date.
     */
    @Test
    public void testUpdateExperienceWithEndDateBeforeStartDate() {
        Experience savedExperience = experienceRepository.save(new Experience());

        ExperienceDTO experience = new ExperienceDTO();
        experience.setId(savedExperience.getId());
        experience.setCompanyId(UUID.randomUUID());
        experience.setStartDate(new Date());
        experience.setJobCategoryId(UUID.randomUUID());
        // Set the end date to one second before the start date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(experience.getStartDate());
        calendar.add(Calendar.SECOND, -1);
        experience.setEndDate(calendar.getTime());
        experience.setJobTitle("jobTitle");

        // Check that an exception is thrown with status code 400
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> experienceService.updateExperience(experience));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * Test that the method throws an exception when the experience does not have all the required attributes.
     */
    @Test
    public void testUpdateExperienceWithMissingAttributes() {
        Experience savedExperience = experienceRepository.save(new Experience());

        ExperienceDTO experience = new ExperienceDTO();
        experience.setId(savedExperience.getId());
        experience.setCompanyId(UUID.randomUUID());
        experience.setStartDate(new Date());
        experience.setJobCategoryId(UUID.randomUUID());
        experience.setJobTitle("jobTitle");

        // Check that an exception is thrown with status code 400
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> experienceService.updateExperience(experience));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    /**
     * Test that the method throws an exception when the experience is not found.
     */
    @Test
    public void testUpdateExperienceWithInvalidId() {
        ExperienceDTO experience = new ExperienceDTO();
        experience.setId(UUID.randomUUID());
        experience.setCompanyId(UUID.randomUUID());
        experience.setStartDate(new Date());
        experience.setJobCategoryId(UUID.randomUUID());
        // Set the end date to one second after the start date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(experience.getStartDate());
        calendar.add(Calendar.SECOND, 1);
        experience.setEndDate(calendar.getTime());
        experience.setJobTitle("jobTitle");

        // Check that an exception is thrown with status code 404
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> experienceService.updateExperience(experience));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * Test that the method deletes an experience.
     */
    @Test
    public void testDeleteExperience() {
        Experience savedExperience = experienceRepository.save(new Experience());

        experienceService.deleteExperience(savedExperience.getId());

        assertFalse(experienceRepository.existsById(savedExperience.getId()));
    }

}
