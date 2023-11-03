package fr.polytech.restcontroller;

import fr.polytech.service.ExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/experience")
public class ExperienceController {

    /**
     * Initialize the logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ExperienceController.class);

    @Autowired
    private ExperienceService experienceService;
}
