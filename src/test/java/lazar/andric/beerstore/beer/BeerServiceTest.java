package lazar.andric.beerstore.beer;

import lazar.andric.beerstore.util.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @InjectMocks
    BeerService beerService;

    @Spy
    BeerMapper beerMapper = Mappers.getMapper(BeerMapper.class);


    @Mock
    BeerRepository beerRepository;

    @Test
    void findAllBeers_returnListOfBeers() {
        List<Beer> beersFromDb = new ArrayList<>();
        beersFromDb.add(Beer.builder().id(1L).name("name1").description("desc1").meanValue(1).build());
        beersFromDb.add(Beer.builder().id(2L).name("name2").description("desc2").meanValue(2).build());
        List<BeerDto> beerForResponse = beersFromDb.stream().map(beerMapper::toBeerDto).collect(Collectors.toList());

        given(beerRepository.findAll()).willReturn(beersFromDb);

        assertThat(beerService.findAllBeers()).isEqualTo(beerForResponse);
    }

    @Test
    void findBeerById_returnOneBeer() {
        Beer beer = Beer.builder().id(1L).name("name1").description("desc1").meanValue(1).build();
        Optional<Beer> beerFromDb = Optional.of(beer);
        BeerDto beerForResponse = beerMapper.toBeerDto(beer);

        given(beerRepository.findById(1L)).willReturn(beerFromDb);

        assertThat(beerService.findBeerById(1L)).isEqualTo(beerForResponse);
    }

    @Test
    void findBeerById_throwException() {

        given(beerRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy( () -> {
            beerService.findBeerById(1L);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteBeerById_throwException() {

        given(beerRepository.existsById(1L)).willReturn(false);

        assertThatThrownBy( () -> {
            beerService.deleteBeerById(1L);
        }).isInstanceOf(EntityNotFoundException.class);
    }
}
