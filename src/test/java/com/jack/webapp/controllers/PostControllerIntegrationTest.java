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
public class PostControllerIntegrationTest {
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    private BookService bookService;
//
//    @Autowired
//    public PostControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
//        this.mockMvc = mockMvc;
//        this.objectMapper = new ObjectMapper();
//        this.bookService = bookService;
//    }
//
//    @Test
//    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception{
//
//        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
//        String createBookJson = objectMapper.writeValueAsString(bookDto);
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createBookJson)
//        ).andExpect(
//                MockMvcResultMatchers.status().isCreated()
//        );
//
//    }
//    @Test
//    public void testThatCreateBookReturnsCreatedBook() throws Exception{
//
//        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
//        String createBookJson = objectMapper.writeValueAsString(bookDto);
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createBookJson)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
//        );
//
//    }
//
//    @Test
//    public void testThatListBooksReturnsHttpStatus200Ok() throws Exception{
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(
//                MockMvcResultMatchers.status().isOk()
//        );
//
//    }
//    @Test
//    public void testThatListBooksReturnsBook() throws Exception {
//        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
//        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-1")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$[0].title").value("The Shadow in the Attic")
//        );
//    }

}
