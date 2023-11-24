package fr.polytech.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.model.Experience;
import fr.polytech.model.ExperienceDTO;
import fr.polytech.service.ExperienceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ExperienceController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ExperienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExperienceService experienceService;

    @Test
    @WithMockUser
    public void testGetAllExperiences() throws Exception {
        given(experienceService.getAllExperiences()).willReturn(Arrays.asList(new Experience(), new Experience()));
        mockMvc.perform(get("/api/v1/experience/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetExperienceById() throws Exception {
        UUID id = UUID.randomUUID();
        given(experienceService.getExperienceById(id)).willReturn(new Experience());
        mockMvc.perform(get("/api/v1/experience/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testCreateExperience() throws Exception {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        experienceDTO.setCompanyId(UUID.randomUUID());
        experienceDTO.setStartDate(new Date());
        experienceDTO.setEndDate(new Date());
        experienceDTO.setJobTitle("jobTitle");
        experienceDTO.setJobCategoryId(UUID.randomUUID());

        Experience experience = new Experience();
        experience.setCompanyId(experienceDTO.getCompanyId());
        experience.setStartDate(experienceDTO.getStartDate());
        experience.setEndDate(experienceDTO.getEndDate());
        experience.setJobTitle(experienceDTO.getJobTitle());
        experience.setJobCategoryId(experienceDTO.getJobCategoryId());
        given(experienceService.createExperience(any(ExperienceDTO.class))).willReturn(experience);

        mockMvc.perform(post("/api/v1/experience/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(experienceDTO))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testUpdateExperience() throws Exception {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        // Set properties for experienceDTO as needed
        given(experienceService.updateExperience(any(ExperienceDTO.class))).willReturn(new Experience());

        mockMvc.perform(put("/api/v1/experience/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(experienceDTO))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeleteExperience() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/experience/" + id).with(csrf()))
                .andExpect(status().isOk());
    }
}