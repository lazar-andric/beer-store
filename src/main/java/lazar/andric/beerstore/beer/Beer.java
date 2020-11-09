package lazar.andric.beerstore.beer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Beer {

    @Id
    private Long id;

    private String name;

    private String description;

    @Column(name = "mean_value")
    private double meanValue;

    @Transient
    @EqualsAndHashCode.Exclude
    private Method method;
}

@Getter
@Setter
class Method {
    @JsonProperty("mash_temp")
    private List<MashTemp> mashTemp;
}

@Getter
@Setter
class MashTemp {
    private Temp temp;
}

@Getter
@Setter
class Temp {
    private double value;
}
