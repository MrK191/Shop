package com.shop.controllers;

import com.google.gson.Gson;
import com.shop.model.Book;
import com.shop.model.BookCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminPanelTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    private Book book;

    @Before
    public void setup() {

        book = Book.builder().bookName("Tower421").bookPrice("1000").bookCategory(BookCategory.SCIENCEFICTION).build();

    }


    @Test
    @WithMockUser(roles = "USER")
    public void accesWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/admin-panel/books")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void accesWithAuthorization() throws Exception {
        mockMvc.perform(get("/admin-panel/users"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "ADMIN")
    public void acces2() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(book);

        mockMvc.perform(post("/admin-panel/books").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }


}
