package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.VisitTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTests {

    private static final ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindVisitOK() throws Exception {
        int VISIT_ID = 1;
        LocalDate VISIT_DATE = LocalDate.parse("2010-03-04");
        String DESCRIPTION = "rabies shot";

        mockMvc.perform(get("/visits/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(VISIT_ID)))
                .andExpect(jsonPath("$.visitDate", is(VISIT_DATE.toString()))) // Comparar con el formato String de LocalDate
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindVisitKO() throws Exception {

        mockMvc.perform(get("/visits/{id}", 666))  // ID inexistente (666)
                .andExpect(status().isNotFound());  // Espera que la respuesta sea un 404
    }

    /**
     * @throws Exception
     */

    @Test
    public void testCreateVisit() throws Exception {
        LocalDate VISIT_DATE = LocalDate.parse("2024-11-07");
        String DESCRIPTION = "Routine check";
        int PET_ID = 1;

        VisitTO newVisitTO = new VisitTO();
        newVisitTO.setVisitDate(VISIT_DATE);
        newVisitTO.setDescription(DESCRIPTION);
        newVisitTO.setPetId(PET_ID);

        mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.visitDate", is(VISIT_DATE.toString())))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.petId", is(PET_ID)));
    }

    /**
     * @throws Exception
     */

    @Test
    public void testDeleteVisit() throws Exception {

        LocalDate VISIT_DATE = LocalDate.parse("2024-11-07");
        String DESCRIPTION = "Routine check";
        int PET_ID = 1;

        VisitTO newVisitTO = new VisitTO();
        newVisitTO.setVisitDate(VISIT_DATE);
        newVisitTO.setDescription(DESCRIPTION);
        newVisitTO.setPetId(PET_ID);
        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");
        mockMvc.perform(delete("/visits/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVisitKO() throws Exception {
        mockMvc.perform(delete("/visits/" + "1000"))
                .andExpect(status().isNotFound());
    }
    /**
     * @throws Exception
     */

}
