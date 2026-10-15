package com.cathy.recycle_bid_backend.function.material;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cathy.recycle_bid_backend.exception.BusinessException;
import com.cathy.recycle_bid_backend.exception.GlobalExceptionHandler;
import com.cathy.recycle_bid_backend.function.material.dto.MaterialReq;
import com.cathy.recycle_bid_backend.function.material.dto.MaterialRes;

// 只驗證 HTTP 契約與驗證規則，不需要啟動資料庫。
class MaterialControllerTests {
    private MaterialService service;
    private MockMvc mvc;
    private MaterialRes material;

    private static final String VALID_REQUEST = """
            {"name":"廢紅銅","category":"金屬類","unit":"KG","enabled":true}
            """;

    @BeforeEach
    void setUp() {
        service = mock(MaterialService.class);
        mvc = MockMvcBuilders.standaloneSetup(new MaterialController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        material = new MaterialRes();
        material.setId(1L);
        material.setCode("MET001");
        material.setName("廢紅銅");
        material.setCategory("金屬類");
        material.setUnit("KG");
        material.setEnabled(true);
        material.setCreatedAt(LocalDateTime.of(2026, 10, 14, 16, 30));
    }

    @Test
    void listsMaterials() throws Exception {
        when(service.findAll()).thenReturn(List.of(material));
        mvc.perform(get("/api/materials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("MET001"));
    }

    @Test
    void findsMaterialById() throws Exception {
        when(service.findById(1L)).thenReturn(material);
        mvc.perform(get("/api/materials/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createsMaterialWithLocation() throws Exception {
        when(service.create(any(MaterialReq.class))).thenReturn(material);
        mvc.perform(post("/api/materials").contentType(MediaType.APPLICATION_JSON).content(VALID_REQUEST))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/materials/1"))
                .andExpect(jsonPath("$.enabled").value(true))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void updatesMaterial() throws Exception {
        when(service.update(eq(1L), any(MaterialReq.class))).thenReturn(material);
        mvc.perform(put("/api/materials/1").contentType(MediaType.APPLICATION_JSON).content(VALID_REQUEST))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("廢紅銅"));
    }

    @Test
    void deletesMaterialWithNoContent() throws Exception {
        mvc.perform(delete("/api/materials/1")).andExpect(status().isNoContent());
        verify(service).delete(1L);
    }

    @Test
    void rejectsBlankNameBeforeCallingService() throws Exception {
        String invalid = """
                {"name":"  ","category":"金屬類","unit":"KG"}
                """;
        mvc.perform(post("/api/materials").contentType(MediaType.APPLICATION_JSON).content(invalid))
                .andExpect(status().isBadRequest());
        mvc.perform(put("/api/materials/1").contentType(MediaType.APPLICATION_JSON).content(invalid))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void preservesBusinessErrorResponse() throws Exception {
        when(service.findById(999L)).thenThrow(new BusinessException("找不到此回收品項"));
        mvc.perform(get("/api/materials/999")).andExpect(status().isUnprocessableContent());
    }
}
