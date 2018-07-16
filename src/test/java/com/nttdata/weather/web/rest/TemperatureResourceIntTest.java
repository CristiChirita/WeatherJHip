package com.nttdata.weather.web.rest;

import com.nttdata.weather.WeatherJHipApp;

import com.nttdata.weather.domain.Temperature;
import com.nttdata.weather.repository.TemperatureRepository;
import com.nttdata.weather.service.TemperatureService;
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
 * Test class for the TemperatureResource REST controller.
 *
 * @see TemperatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherJHipApp.class)
public class TemperatureResourceIntTest {

    private static final String DEFAULT_CONTINENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTINENT = "BBBBBBBBBB";

    private static final Double DEFAULT_MIN_TEMPERATURE = 1D;
    private static final Double UPDATED_MIN_TEMPERATURE = 2D;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Double DEFAULT_MAX_TEMPERATURE = 1D;
    private static final Double UPDATED_MAX_TEMPERATURE = 2D;

    private static final String DEFAULT_CITY_MAX = "AAAAAAAAAA";
    private static final String UPDATED_CITY_MAX = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_MAX = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_MAX = "BBBBBBBBBB";

    @Autowired
    private TemperatureRepository temperatureRepository;

    

    @Autowired
    private TemperatureService temperatureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemperatureMockMvc;

    private Temperature temperature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemperatureResource temperatureResource = new TemperatureResource(temperatureService);
        this.restTemperatureMockMvc = MockMvcBuilders.standaloneSetup(temperatureResource)
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
    public static Temperature createEntity(EntityManager em) {
        Temperature temperature = new Temperature()
            .continent(DEFAULT_CONTINENT)
            .minTemperature(DEFAULT_MIN_TEMPERATURE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .maxTemperature(DEFAULT_MAX_TEMPERATURE)
            .cityMax(DEFAULT_CITY_MAX)
            .countryMax(DEFAULT_COUNTRY_MAX);
        return temperature;
    }

    @Before
    public void initTest() {
        temperature = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemperature() throws Exception {
        int databaseSizeBeforeCreate = temperatureRepository.findAll().size();

        // Create the Temperature
        restTemperatureMockMvc.perform(post("/api/temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temperature)))
            .andExpect(status().isCreated());

        // Validate the Temperature in the database
        List<Temperature> temperatureList = temperatureRepository.findAll();
        assertThat(temperatureList).hasSize(databaseSizeBeforeCreate + 1);
        Temperature testTemperature = temperatureList.get(temperatureList.size() - 1);
        assertThat(testTemperature.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testTemperature.getMinTemperature()).isEqualTo(DEFAULT_MIN_TEMPERATURE);
        assertThat(testTemperature.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testTemperature.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testTemperature.getMaxTemperature()).isEqualTo(DEFAULT_MAX_TEMPERATURE);
        assertThat(testTemperature.getCityMax()).isEqualTo(DEFAULT_CITY_MAX);
        assertThat(testTemperature.getCountryMax()).isEqualTo(DEFAULT_COUNTRY_MAX);
    }

    @Test
    @Transactional
    public void createTemperatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = temperatureRepository.findAll().size();

        // Create the Temperature with an existing ID
        temperature.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemperatureMockMvc.perform(post("/api/temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temperature)))
            .andExpect(status().isBadRequest());

        // Validate the Temperature in the database
        List<Temperature> temperatureList = temperatureRepository.findAll();
        assertThat(temperatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTemperatures() throws Exception {
        // Initialize the database
        temperatureRepository.saveAndFlush(temperature);

        // Get all the temperatureList
        restTemperatureMockMvc.perform(get("/api/temperatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(temperature.getId().intValue())))
            .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT.toString())))
            .andExpect(jsonPath("$.[*].minTemperature").value(hasItem(DEFAULT_MIN_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].maxTemperature").value(hasItem(DEFAULT_MAX_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].cityMax").value(hasItem(DEFAULT_CITY_MAX.toString())))
            .andExpect(jsonPath("$.[*].countryMax").value(hasItem(DEFAULT_COUNTRY_MAX.toString())));
    }
    

    @Test
    @Transactional
    public void getTemperature() throws Exception {
        // Initialize the database
        temperatureRepository.saveAndFlush(temperature);

        // Get the temperature
        restTemperatureMockMvc.perform(get("/api/temperatures/{id}", temperature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(temperature.getId().intValue()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT.toString()))
            .andExpect(jsonPath("$.minTemperature").value(DEFAULT_MIN_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.maxTemperature").value(DEFAULT_MAX_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.cityMax").value(DEFAULT_CITY_MAX.toString()))
            .andExpect(jsonPath("$.countryMax").value(DEFAULT_COUNTRY_MAX.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTemperature() throws Exception {
        // Get the temperature
        restTemperatureMockMvc.perform(get("/api/temperatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemperature() throws Exception {
        // Initialize the database
        temperatureService.save(temperature);

        int databaseSizeBeforeUpdate = temperatureRepository.findAll().size();

        // Update the temperature
        Temperature updatedTemperature = temperatureRepository.findById(temperature.getId()).get();
        // Disconnect from session so that the updates on updatedTemperature are not directly saved in db
        em.detach(updatedTemperature);
        updatedTemperature
            .continent(UPDATED_CONTINENT)
            .minTemperature(UPDATED_MIN_TEMPERATURE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .maxTemperature(UPDATED_MAX_TEMPERATURE)
            .cityMax(UPDATED_CITY_MAX)
            .countryMax(UPDATED_COUNTRY_MAX);

        restTemperatureMockMvc.perform(put("/api/temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemperature)))
            .andExpect(status().isOk());

        // Validate the Temperature in the database
        List<Temperature> temperatureList = temperatureRepository.findAll();
        assertThat(temperatureList).hasSize(databaseSizeBeforeUpdate);
        Temperature testTemperature = temperatureList.get(temperatureList.size() - 1);
        assertThat(testTemperature.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testTemperature.getMinTemperature()).isEqualTo(UPDATED_MIN_TEMPERATURE);
        assertThat(testTemperature.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testTemperature.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testTemperature.getMaxTemperature()).isEqualTo(UPDATED_MAX_TEMPERATURE);
        assertThat(testTemperature.getCityMax()).isEqualTo(UPDATED_CITY_MAX);
        assertThat(testTemperature.getCountryMax()).isEqualTo(UPDATED_COUNTRY_MAX);
    }

    @Test
    @Transactional
    public void updateNonExistingTemperature() throws Exception {
        int databaseSizeBeforeUpdate = temperatureRepository.findAll().size();

        // Create the Temperature

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemperatureMockMvc.perform(put("/api/temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temperature)))
            .andExpect(status().isBadRequest());

        // Validate the Temperature in the database
        List<Temperature> temperatureList = temperatureRepository.findAll();
        assertThat(temperatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemperature() throws Exception {
        // Initialize the database
        temperatureService.save(temperature);

        int databaseSizeBeforeDelete = temperatureRepository.findAll().size();

        // Get the temperature
        restTemperatureMockMvc.perform(delete("/api/temperatures/{id}", temperature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Temperature> temperatureList = temperatureRepository.findAll();
        assertThat(temperatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Temperature.class);
        Temperature temperature1 = new Temperature();
        temperature1.setId(1L);
        Temperature temperature2 = new Temperature();
        temperature2.setId(temperature1.getId());
        assertThat(temperature1).isEqualTo(temperature2);
        temperature2.setId(2L);
        assertThat(temperature1).isNotEqualTo(temperature2);
        temperature1.setId(null);
        assertThat(temperature1).isNotEqualTo(temperature2);
    }
}
