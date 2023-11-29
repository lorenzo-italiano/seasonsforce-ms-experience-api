package fr.polytech.service;

import fr.polytech.model.JobCategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
public class JobCategoryService {

    private final Logger logger = LoggerFactory.getLogger(JobCategoryService.class);

    @Autowired
    private ApiService apiService;

    /**
     * Get job category by id.
     * @param id Job category id
     * @param token Access token
     * @return Job category with the specified id
     * @throws HttpClientErrorException if an error occurs while calling the API
     */
    public JobCategoryDTO getJobCategoryById(UUID id, String token) throws HttpClientErrorException {
        String uri = System.getenv("JOB_CATEGORY_API_URI") + "/" + id;
        return apiService.makeApiCall(uri, HttpMethod.GET, JobCategoryDTO.class, token);
    }

}
