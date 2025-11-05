package com.example.firstjobapp.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

}
//we dont have to write any code for the repository.spring data jpa will automatically will generate implementation at runtime and we can use it in our application(service class) to interact with database