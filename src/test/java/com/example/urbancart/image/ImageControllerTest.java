package com.example.urbancart.image;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.urbancart.auth.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ImageControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private JwtService jwtService;

  @Test
  public void testGetImageNotFound() throws Exception {
    String imageName = "nonexistent.jpg";

    mockMvc.perform(get("/images/{imageName}", imageName)).andExpect(status().isNotFound());
  }

  @Test
  public void testUploadImageServerError() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile(
            "file", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "Spring Framework".getBytes());
    mockMvc
        .perform(MockMvcRequestBuilders.multipart("/images/upload").file(file))
        .andExpect(status().isInternalServerError());
  }
}
