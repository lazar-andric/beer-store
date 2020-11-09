package lazar.andric.beerstore.beer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllBears_returnsResponseEntityWithListOfBeers() throws Exception {
        List<BeerDto> beerForResponse = new ArrayList<>();
        beerForResponse.add(BeerDto.builder().id(1L).name("name1").description("desc1").meanValue(1).build());
        beerForResponse.add(BeerDto.builder().id(2L).name("name2").description("desc2").meanValue(2).build());

        given(beerService.findAllBeers()).willReturn(beerForResponse);

        MockHttpServletResponse response = mockMvc.perform(
            get( "/beers")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(beerForResponse));
    }

    @Test
    void getBeerById_returnOneBeer() throws Exception {
        BeerDto beerDto = BeerDto.builder().id(1L).name("name1").description("desc1").meanValue(1).build();

        given(beerService.findBeerById(1L)).willReturn(beerDto);

        MockHttpServletResponse response = mockMvc.perform(
            get( "/beers/1")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(beerDto));
    }

    @Test
    void getBeerById_returnOneBeerExceptionWhenIdInvalid() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
            get( "/beers/0")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deleteBeerById_success() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
            get( "/beers/1")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deleteBeerById_returnOneBeerExceptionWhenIdInvalid() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
            get( "/beers/0")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
