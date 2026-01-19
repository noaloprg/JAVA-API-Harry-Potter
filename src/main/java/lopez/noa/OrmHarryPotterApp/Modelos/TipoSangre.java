package lopez.noa.OrmHarryPotterApp.Modelos;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoSangre {
    @JsonProperty("pura") PURA,
    @JsonProperty("mestiza") MESTIZA,
    @JsonProperty("muggle") MUGGLE
}
