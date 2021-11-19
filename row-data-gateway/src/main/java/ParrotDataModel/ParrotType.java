package ParrotDataModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParrotType {

    public ParrotType(Integer parrotTypeId, String species) {
        this.parrotTypeId = parrotTypeId;
        this.species = species;
    }

    private Integer parrotTypeId;

    private String species;
}
