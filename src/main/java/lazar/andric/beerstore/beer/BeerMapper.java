package lazar.andric.beerstore.beer;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BeerMapper {
    BeerDto toBeerDto(Beer beer);
    Beer toBeer(BeerDto beerDto);
}
