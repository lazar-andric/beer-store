package lazar.andric.beerstore.beer;

import lazar.andric.beerstore.util.constants.ApplicationProperties;
import lazar.andric.beerstore.util.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerService {

    private final Environment environment;
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private final RestTemplate restTemplate;

    public List<BeerDto> findAllBeers() {
        return beerRepository.findAll()
                             .stream()
                             .map(beerMapper::toBeerDto)
                             .collect(Collectors.toList());
    }

    public BeerDto findBeerById(long id) {
        Optional<Beer> foundBeer = beerRepository.findById(id);
        if (foundBeer.isPresent()) {
            return beerMapper.toBeerDto(foundBeer.get());
        }
        throw new EntityNotFoundException("Beer", String.valueOf(id));
    }

    public void deleteBeerById(long id) {
        boolean existsById = beerRepository.existsById(id);
        if (existsById)
            beerRepository.deleteById(id);
        else {
            throw new EntityNotFoundException("Beer", String.valueOf(id));
        }
    }

    public List<BeerDto> fillBeers() {
        Set<Beer> beersFromApi = getBeersFromApi();
        return beerRepository.saveAll(beersFromApi)
                             .stream()
                             .map(beerMapper::toBeerDto)
                             .collect(Collectors.toList());
    }

    private Set<Beer> getBeersFromApi() {
        int maxBeerAllowed = environment.getProperty(ApplicationProperties.MAX_BEER_ALLOWED,  int.class, 10);
        String punkapiResourceUrl = environment.getProperty(ApplicationProperties.PUNKAPI_RANDOM_BEER_URL,  String.class,
                                                            "https://api.punkapi.com/v2/beers/random");
        Set<Beer> beersFromDb = new HashSet<>(beerRepository.findAll());
        Set<Beer> beersFromApi = new HashSet<>();

        while (beersFromDb.size() + beersFromApi.size() < maxBeerAllowed) {
            ResponseEntity<Beer[]> responseEntity = restTemplate.getForEntity(punkapiResourceUrl, Beer[].class);
            Beer[] randomBeers = responseEntity.getBody();
            if(randomBeers != null && randomBeers.length > 0) {
                Beer beer = randomBeers[0];
                if (!beersFromDb.contains(beer)) {
                    setBearMeanValue(beer);
                    beersFromApi.add(beer);
                }
            }
        }
        return beersFromApi;
    }

    private void setBearMeanValue(Beer beer) {
        OptionalDouble meanValue = beer.getMethod().getMashTemp()
                                       .stream()
                                       .mapToDouble(b -> b.getTemp().getValue())
                                       .average();
        if (meanValue.isPresent()) {
            beer.setMeanValue(meanValue.getAsDouble());
        }
    }
}
