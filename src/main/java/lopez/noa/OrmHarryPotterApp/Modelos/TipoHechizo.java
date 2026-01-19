package lopez.noa.OrmHarryPotterApp.Modelos;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoHechizo {
    @JsonProperty("encantamiento")
    ENCANTAMIENTO,

    @JsonProperty("transformacion")
    TRANSFORMACION,

    @JsonProperty("defensa")
    DEFENSA,

    @JsonProperty("ofensivo")
    OFENSIVO,

    @JsonProperty("curacion")
    CURACION,

    @JsonProperty("maldicion")
    MALDICION
}
