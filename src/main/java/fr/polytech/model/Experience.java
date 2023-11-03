package fr.polytech.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "experience", schema = "public")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String jobTitle;
    private UUID jobCategoryId;
    private Date startDate;
    private Date endDate;
    private UUID companyId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public UUID getJobCategoryId() {
        return jobCategoryId;
    }

    public void setJobCategoryId(UUID jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobCategoryId=" + jobCategoryId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", companyId=" + companyId +
                '}';
    }
}
