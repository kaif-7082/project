package com.example.firstjobapp.job.implementation;

import com.example.firstjobapp.companies.Company;
import com.example.firstjobapp.companies.CompanyRepository;
import com.example.firstjobapp.job.Job;
import com.example.firstjobapp.job.JobRepository;
import com.example.firstjobapp.job.JobService;
import com.example.firstjobapp.job.dto.createJobRequestDto;
import com.example.firstjobapp.job.dto.userResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.firstjobapp.job.dto.LocationCount;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobServiceImplementation implements JobService {

    JobRepository jobRepository;
    CompanyRepository companyRepository;

    public JobServiceImplementation(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<userResponseDTO> findAll() {
        log.info("Executing findAll jobs");
        List<userResponseDTO> userResponse=new ArrayList<userResponseDTO>();
        List<Job> jobs=jobRepository.findAll();
        for(Job job:jobs)
        {
            userResponseDTO response=new userResponseDTO();
            response.setId(job.getId());
            response.setTitle(job.getTitle());
            response.setMaxSalary(job.getMaxSalary());
            response.setLocation(job.getLocation());
            userResponse.add(response);
        }
        log.info("Found {} jobs", userResponse.size());
        return userResponse;
    }


    @Override
    public void createJob(createJobRequestDto createRequest) {
        log.info("Attempting to create job: {}", createRequest.getTitle());
        Job job = new Job();
        job.setTitle(createRequest.getTitle());
        job.setDescription(createRequest.getDescription());
        job.setMinSalary(createRequest.getMinSalary());
        job.setMaxSalary(createRequest.getMaxSalary());
        job.setLocation(createRequest.getLocation());


        Company company = companyRepository.findById(createRequest.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + createRequest.getCompanyId()));

        job.setCompany(company); // Set the relationship

        jobRepository.save(job);
        log.info("Successfully created job with id: {}", job.getId());
    }

    @Override
    public Job getJobById(Long id) {
        log.info("Finding job by id: {}", id);
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        log.info("Attempting to delete job with id: {}", id);
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            log.info("Successfully deleted job with id: {}", id);
            return true;
        }
        log.warn("Failed to delete. Job not found with id: {}", id);
        return false;
    }


    @Override
    public boolean updateJob(Long id, createJobRequestDto updatedJob) {
        log.info("Attempting to update job with id: {}", id);
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();

            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());

            Company company = companyRepository.findById(updatedJob.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found with id: " + updatedJob.getCompanyId()));

            job.setCompany(company); // Set the relationship

            jobRepository.save(job);
            log.info("Successfully updated job with id: {}", id);
            return  true;
        }
        log.warn("Failed to update. Job not found with id: {}", id);
        return false;
    }

    @Override
    public List<Job> findjobswithSorting(String field) {
        log.info("Finding jobs with sorting on field: {}", field);
        return jobRepository.findAll(Sort.by(Sort.Direction.DESC,field));
    }

    @Override
    public List<Job> findJobsByLocation(String location) {
        log.info("Finding jobs by location: {}", location);
        return jobRepository.findByLocation(location);
    }

    @Override
    public List<Job> findJobsByMinSalaryGreaterThan(Integer salary) {
        log.info("Finding jobs with min salary > {}", salary);
        return jobRepository.findByMinSalaryGreaterThan(salary);
    }

    @Override
    public List<Job> searchJobs(String query) {
        log.info("Searching jobs with query: {}", query);
        return jobRepository.searchJobs(query);
    }

    @Override
    public List<LocationCount> getLocationCounts() {
        log.info("Getting location stats");
        return jobRepository.getLocationCounts();
    }

    @Override
    public Page<Job> findJobsWithPagination(int page, int pageSize) {
        log.info("Finding jobs with pagination - page: {}, pageSize: {}", page, pageSize);
        Page<Job> jobs=jobRepository.findAll(PageRequest.of(page, pageSize));
        return jobs;
    }
}