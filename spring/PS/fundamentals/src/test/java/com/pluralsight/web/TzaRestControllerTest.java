package com.pluralsight.web;

import com.pluralsight.service.ApplicationService;
import com.pluralsight.service.ReleaseService;
import com.pluralsight.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TzaRestControllerTest.class)
public class TzaRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ApplicationService applicationService;
    @MockBean
    TicketService ticketService;
    @MockBean
    ReleaseService releaseService;

    @Test
    public void getAllApplications() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get("/tza/applications"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        /* [{"id":1,"name":"Trackzilla","owner":"Kesha Williams","description":"A bug tracking application"},
        {"id":2,"name":"Expenses","owner":"Jane Doe","description":"An application used to submit expenses"},
        {"id":3,"name":"Bookings","owner":"John Doe","description":"An application used to book tickets"},
        {"id":4,"name":"Invoice Search","owner":"Mary Richards","description":"An application used to search invoices "},
        {"id":5,"name":"Audits","owner":"Tiffany Stewart","description":"An application used for auditing purposes."}] */
        verify(applicationService, times(1)).listApplications();
    }

}