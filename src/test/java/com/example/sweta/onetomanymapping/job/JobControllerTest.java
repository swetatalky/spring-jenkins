package com.example.sweta.onetomanymapping.job;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sweta.onetomanymapping.exception.JobNotFoundException;

@ExtendWith(SpringExtension.class)
@WebMvcTest(JobController.class)
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    private Job job;

    @BeforeEach
    void setUp() {
        job = new Job(1L, "Software Engineer", "Develop software", "50000", "80000", "NYC");
    }

    /**
     * Test case for GET /jobs (Find all jobs)
     */
    @Test
    public void testFindAllJobs() throws Exception {
        List<Job> jobs = Arrays.asList(job);
        when(jobService.findAll()).thenReturn(jobs);

        mockMvc.perform(get("/jobs"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(1))
               .andExpect(jsonPath("$[0].title").value("Software Engineer"));
    }

    /**
     * Test case for POST /jobs (Create a job)
     */
    @Test
    public void testCreateJob() throws Exception {
        doNothing().when(jobService).createJob(Mockito.any(Job.class));

        mockMvc.perform(post("/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"title\": \"Software Engineer\", \"description\": \"Develop software\", \"minSalary\": \"50000\", \"maxSalary\": \"80000\", \"location\": \"NYC\"}"))
               .andExpect(status().isCreated())
               .andExpect(content().string("Job added successfully"));
    }

    /**
     * Test case for GET /jobs/{id} (Find job by ID)
     */
    @Test
    public void testGetJobById() throws Exception {
      /*  when(jobService.getJobById(1L)).thenReturn(job);

        mockMvc.perform(get("/jobs/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("Software Engineer"))
               .andExpect(jsonPath("$.location").value("NYC"));*/
    	   when(jobService.getJobById(1L)).thenThrow(new JobNotFoundException("Job not found with ID: 1"));

    	    mockMvc.perform(get("/jobs/1"))
    	           .andExpect(status().isNotFound())  // ✅ Expect 404 NOT FOUND
    	           .andExpect(jsonPath("$.message").value("Job not found with ID: 1"))  // ✅ Verify error message
    	           .andExpect(jsonPath("$.status").value(404))  // ✅ Verify status code in response body
    	           .andExpect(jsonPath("$.timestamp").exists());  // ✅ Ensure timestamp is included
    	    }

    /**
     * Test case for GET /jobs/{id} when job is not found
     */
    //@Test
    public void testGetJobById_NotFound() throws Exception {
        when(jobService.getJobById(1L)).thenThrow(new RuntimeException("Job not found"));

        mockMvc.perform(get("/jobs/1"))
               .andExpect(status().isNotFound());
    }
    
    /**
     * Test case for PUT /jobs/{id} (Update job by ID)
     */
    @Test
    public void testUpdateJob() throws Exception {
      //when return type is void 
    	//doNothing().when(jobService).updateJob(eq(1L), Mockito.any(Job.class));

    	when(jobService.updateJob(eq(1L), Mockito.any(Job.class))).thenReturn(true); // Mock the return value

        mockMvc.perform(put("/jobs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Senior Software Engineer\", \"description\": \"Develop complex software\", \"minSalary\": \"70000\", \"maxSalary\": \"100000\", \"location\": \"SF\"}"))
               .andExpect(status().isOk())
               .andExpect(content().string("Job updated successfully"));
    	
    }

    /**
     * Test case for DELETE /jobs/{id} (Delete job by ID)
     */
    @Test
    public void testDeleteJob() throws Exception {
       // doNothing().when(jobService).deleteJobById(1L);
    /*	  when(jobService.deleteJobById(1L)).thenReturn(true); // Mock the return value

        mockMvc.perform(delete("/jobs/1"))
               .andExpect(status().isOk())
               .andExpect(content().string("Job deleted successfully"));*/
    	
    	
    	    // Simulate that the job does not exist by throwing JobNotFoundException
    	    doThrow(new JobNotFoundException("Job not found with ID: 1"))
    	        .when(jobService)
    	        .updateJob(eq(1L), Mockito.any(Job.class));

    	    mockMvc.perform(put("/jobs/1")
    	            .contentType(MediaType.APPLICATION_JSON)
    	            .content("{\"title\": \"Senior Software Engineer\", \"description\": \"Develop complex software\", \"minSalary\": \"70000\", \"maxSalary\": \"100000\", \"location\": \"SF\"}"))
    	           .andExpect(status().isNotFound())  // ✅ Expect 404 NOT FOUND
    	           .andExpect(jsonPath("$.message").value("Job not found with ID: 1"))  // ✅ Verify error message
    	           .andExpect(jsonPath("$.status").value(404))  // ✅ Verify status code in response body
    	           .andExpect(jsonPath("$.timestamp").exists());  // ✅ Ensure timestamp is included
    	

    }
    
   


    /**
     * Test case for DELETE /jobs/{id} when job is not found
     */
    @Test
    public void testDeleteJob_NotFound() throws Exception {
    	
      //  doThrow(new RuntimeException("Job not found")).when(jobService).deleteJobById(1L);
   //this code is when controller would be handling the true or false case but in our case global handler is handling so code is like after below one
    	
    	/*	  when(jobService.deleteJobById(1L)).thenReturn(false);
        mockMvc.perform(delete("/jobs/1"))
               .andExpect(status().isNotFound());*/
    	
    	doThrow(new JobNotFoundException("Job not found with ID: 1"))
        .when(jobService)
        .deleteJobById(1L);
    	
    	 mockMvc.perform(delete("/jobs/1"))
         .andExpect(status().isNotFound())  // ✅ Expect 404 NOT FOUND
         .andExpect(jsonPath("$.message").value("Job not found with ID: 1"))  // ✅ Verify error message
         .andExpect(jsonPath("$.status").value(404))  // ✅ Verify status code in response body
         .andExpect(jsonPath("$.timestamp").exists());  // ✅ Ensure timestamp is included
}
    

   
    /**
     * Test case for PUT /jobs/{id} when job is not found
     */
  //  @Test
    public void testUpdateJob_NotFound() throws Exception {
       // doThrow(new RuntimeException("Job not found")).when(jobService).updateJob(eq(1L), Mockito.any(Job.class));

    	when(jobService.updateJob(eq(1L), Mockito.any(Job.class))).thenReturn(false); // Simulate failure

        mockMvc.perform(put("/jobs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Senior Software Engineer\", \"description\": \"Develop complex software\", \"minSalary\": \"70000\", \"maxSalary\": \"100000\", \"location\": \"SF\"}"))
               .andExpect(status().isNotFound());
    }
}
