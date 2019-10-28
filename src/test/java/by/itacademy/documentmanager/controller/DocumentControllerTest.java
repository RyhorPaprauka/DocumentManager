package by.itacademy.documentmanager.controller;

import by.itacademy.documentmanager.model.DocumentType;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DbxClientV2 dropBoxClient;

    @Test
    void uploadTest() throws Exception {
        Metadata metadata = Metadata.newBuilder("meta")
                .withPathLower("/documents/test.txt")
                .build();

        String filename = "test.txt";
        MockMultipartFile file = new MockMultipartFile("file.txt",
                filename,
                "text/plain",
                "test data".getBytes());


        when(dropBoxClient.files().uploadBuilder("/documents/test.txt")
                .uploadAndFinish(file.getInputStream()))
                .thenReturn((FileMetadata) metadata);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/document/upload")
                .file("file", file.getBytes())
                .param("title", "test")
                .param("type", DocumentType.FICTION.name()))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void downloadTest() {
    }

    @Test
    void searchTest() {
    }
}