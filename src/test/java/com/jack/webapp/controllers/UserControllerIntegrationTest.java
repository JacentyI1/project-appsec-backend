package com.jack.webapp.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
//
//    private AuthorService authorService;
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    public UserControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
//        this.mockMvc = mockMvc;
//        this.objectMapper = new ObjectMapper();
//        this.authorService = authorService;
//    }
//
//    @Test
//    void testThatCreateAuthorSuccesfullyReturnsHttp201Created() throws Exception {
//        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
//        testAuthor.setId(null);
//        String authorJson = objectMapper.writeValueAsString(testAuthor);
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(authorJson)
//        ).andExpect(
//                MockMvcResultMatchers.status().isCreated()
//        );
//    }
//
//    @Test
//    void testThatCreateAuthorSuccesfullyReturnsSavedAuthor() throws Exception {
//        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
//        testAuthor.setId(null);
//        String authorJson = objectMapper.writeValueAsString(testAuthor);
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(authorJson)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.id").isNumber()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.name").value("Jack Flower")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.age").value("80")
//        );
//    }
//
//    @Test
//    public void testThatListAuthorsReturnsHttpStatus200() throws Exception{
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//
//        ).andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @Test
//    public void testThatListAuthorsReturnsListOfAuthors() throws Exception{
//        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
//        authorService.save(testAuthorEntityA);
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$[0].name").value("Jack Flower")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$[0].age").value("80")
//        );
//    }
}
