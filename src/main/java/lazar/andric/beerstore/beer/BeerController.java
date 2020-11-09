package lazar.andric.beerstore.beer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RequestMapping("beers")
@RequiredArgsConstructor
@Validated
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<List<BeerDto>> getAllBears() {
        return ResponseEntity.ok(beerService.findAllBeers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable @Min(1) long id ) {
        return ResponseEntity.ok(beerService.findBeerById(id));
    }

    @PostMapping
    public ResponseEntity<List<BeerDto>> fillBeers() {
        return ResponseEntity.ok(beerService.fillBeers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBeerById(@PathVariable @Min(1) long id) {
        beerService.deleteBeerById(id);
        return ResponseEntity.ok("Beer successfully deleted.");
    }
}
