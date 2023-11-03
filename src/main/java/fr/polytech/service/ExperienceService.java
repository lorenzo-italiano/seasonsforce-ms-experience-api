package fr.polytech.service;

import fr.polytech.repository.ExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService {

    /**
     * Initialize the logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ExperienceService.class);

    @Autowired
    private ExperienceRepository experienceRepository;
}
