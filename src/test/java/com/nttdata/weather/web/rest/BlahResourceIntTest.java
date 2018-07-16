package com.nttdata.weather.web.rest;

import com.nttdata.weather.WeatherJHipApp;

import com.nttdata.weather.domain.Blah;
import com.nttdata.weather.repository.BlahRepository;
import com.nttdata.weather.service.BlahService;
import com.nttdata.weather.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.nttdata.weather.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BlahResource REST controller.
 *
 * @see BlahResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherJHipApp.class)
public class BlahResourceIntTest {

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTINENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTINENT = "BBBBBBBBBB";

    @Autowired
    private BlahRepository blahRepository;

    

    @Autowired
    private BlahService blahService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBlahMockMvc;

    private Blah blah;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlahResource blahResource = new BlahResource(blahService);
        this.restBlahMockMvc = MockMvcBuilders.standaloneSetup(blahResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Blah createEntity(EntityManager em) {
        Blah blah = new Blah()
            .location(DEFAULT_LOCATION)
            .continent(DEFAULT_CONTINENT);
        return blah;
    }

    @Before
    public void initTest() {
        blah = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlah() throws Exception {
        int databaseSizeBeforeCreate = blahRepository.findAll().size();

        // Create the Blah
        restBlahMockMvc.perform(post("/api/blahs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blah)))
            .andExpect(status().isCreated());

        // Validate the Blah in the database
        List<Blah> blahList = blahRepository.findAll();
        assertThat(blahList).hasSize(databaseSizeBeforeCreate + 1);
        Blah testBlah = blahList.get(blahList.size() - 1);
        assertThat(testBlah.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testBlah.getContinent()).isEqualTo(DEFAULT_CONTINENT);
    }

    @Test
    @Transactional
    public void createBlahWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blahRepository.findAll().size();

        // Create the Blah with an existing ID
        blah.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlahMockMvc.perform(post("/api/blahs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blah)))
            .andExpect(status().isBadRequest());

        // Validate the Blah in the database
        List<Blah> blahList = blahRepository.findAll();
        assertThat(blahList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBlahs() throws Exception {
        // Initialize the database
        blahRepository.saveAndFlush(blah);

        // Get all the blahList
        restBlahMockMvc.perform(get("/api/blahs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blah.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT.toString())));
    }
    

    @Test
    @Transactional
    public void getBlah() throws Exception {
        // Initialize the database
        blahRepository.saveAndFlush(blah);

        // Get the blah
        restBlahMockMvc.perform(get("/api/blahs/{id}", blah.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blah.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBlah() throws Exception {
        // Get the blah
        restBlahMockMvc.perform(get("/api/blahs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlah() throws Exception {
        // Initialize the database
        blahService.save(blah);

        int databaseSizeBeforeUpdate = blahRepository.findAll().size();

        // Update the blah
        Blah updatedBlah = blahRepository.findById(blah.getId()).get();
        // Disconnect from session so that the updates on updatedBlah are not directly saved in db
        em.detach(updatedBlah);
        updatedBlah
            .location(UPDATED_LOCATION)
            .continent(UPDATED_CONTINENT);

        restBlahMockMvc.perform(put("/api/blahs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlah)))
            .andExpect(status().isOk());

        // Validate the Blah in the database
        List<Blah> blahList = blahRepository.findAll();
        assertThat(blahList).hasSize(databaseSizeBeforeUpdate);
        Blah testBlah = blahList.get(blahList.size() - 1);
        assertThat(testBlah.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testBlah.getContinent()).isEqualTo(UPDATED_CONTINENT);
    }

    @Test
    @Transactional
    public void updateNonExistingBlah() throws Exception {
        int databaseSizeBeforeUpdate = blahRepository.findAll().size();

        // Create the Blah

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBlahMockMvc.perform(put("/api/blahs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blah)))
            .andExpect(status().isBadRequest());

        // Validate the Blah in the database
        List<Blah> blahList = blahRepository.findAll();
        assertThat(blahList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlah() throws Exception {
        // Initialize the database
        blahService.save(blah);

        int databaseSizeBeforeDelete = blahRepository.findAll().size();

        // Get the blah
        restBlahMockMvc.perform(delete("/api/blahs/{id}", blah.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Blah> blahList = blahRepository.findAll();
        assertThat(blahList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Blah.class);
        Blah blah1 = new Blah();
        blah1.setId(1L);
        Blah blah2 = new Blah();
        blah2.setId(blah1.getId());
        assertThat(blah1).isEqualTo(blah2);
        blah2.setId(2L);
        assertThat(blah1).isNotEqualTo(blah2);
        blah1.setId(null);
        assertThat(blah1).isNotEqualTo(blah2);
    }
}
