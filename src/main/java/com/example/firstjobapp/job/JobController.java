package com.example.firstjobapp.job;

import com.example.firstjobapp.job.dto.LocationCount;
import com.example.firstjobapp.job.dto.createJobRequestDto;
import com.example.firstjobapp.job.dto.userResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping
    public ResponseEntity<List<userResponseDTO>> findAll() {
        log.info("GET /jobs - Request to get all jobs");
        return ResponseEntity.ok(jobService.findAll());
    }


    @GetMapping("/sorted/{field}")
    public ResponseEntity<List<Job>> findSortedJobs(@PathVariable String field) {
        log.info("GET /jobs/sorted/{} - Request to get sorted jobs", field);
        List<Job> sortedJobs = jobService.findjobswithSorting(field);
        return ResponseEntity.ok(sortedJobs);
    }


    @PostMapping
    public ResponseEntity<String> createJob(@Valid @RequestBody createJobRequestDto createRequest) {
        log.info("POST /jobs - Request to create new job: {}", createRequest.getTitle());
        jobService.createJob(createRequest);
        log.info("POST /jobs - Job created successfully: {}", createRequest.getTitle());
        return new ResponseEntity<>("Job created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id) {
        log.info("GET /jobs/{} - Request to get job by id", id);
        Job job = jobService.getJobById(id);
        if (job != null)
            return new ResponseEntity<>(job, HttpStatus.OK);

        log.warn("GET /jobs/{} - Job not found", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
        log.info("DELETE /jobs/{} - Request to delete job", id);
        boolean deleted = jobService.deleteJobById(id);
        if (deleted) {
            log.info("DELETE /jobs/{} - Job deleted successfully", id);
            return new ResponseEntity<>("Job deleted", HttpStatus.OK);
        }
        log.warn("DELETE /jobs/{} - Job not found", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @Valid @RequestBody createJobRequestDto updatedJob) {
        log.info("PUT /jobs/{} - Request to update job", id);
        boolean updated = jobService.updateJob(id, updatedJob);
        if (updated) {
            log.info("PUT /jobs/{} - Job updated successfully", id);
            return new ResponseEntity<>("Job updated", HttpStatus.OK);
        }
        log.warn("PUT /jobs/{} - Job not found", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Job>> findJobsByLocation(@PathVariable String location) {
        log.info("GET /jobs/location/{} - Request to find jobs by location", location);
        List<Job> jobs = jobService.findJobsByLocation(location);
        return ResponseEntity.ok(jobs);
    }
    @GetMapping("/salary")
    public ResponseEntity<List<Job>> findJobsByMinSalaryGreaterThan(@RequestParam Integer min) {
        log.info("GET /jobs/salary - Request to find jobs by min salary > {}", min);
        List<Job> jobs = jobService.findJobsByMinSalaryGreaterThan(min);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(@RequestParam String query) {
        log.info("GET /jobs/search - Request to search jobs with query: {}", query);
        List<Job> jobs = jobService.searchJobs(query);
        return ResponseEntity.ok(jobs);
    }
    @GetMapping("/stats/location-counts")
    public ResponseEntity<List<LocationCount>> getLocationCounts() {
        log.info("GET /jobs/stats/location-counts - Request to get location stats");
        List<LocationCount> stats = jobService.getLocationCounts();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/pagination/{page}/{pageSize}")
    public ResponseEntity<Page<Job>> getJobsWithPagination(@PathVariable int page, @PathVariable int pageSize) {
        log.info("GET /jobs/pagination/{}/{} - Request to get paginated jobs", page, pageSize);
        Page<Job> jobs = jobService.findJobsWithPagination(page, pageSize);
        return ResponseEntity.ok(jobs);
    }


}