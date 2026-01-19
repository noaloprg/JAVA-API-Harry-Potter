package lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO;

import lopez.noa.OrmHarryPotterApp.Modelos.TipoHechizo;

public record HechizoResponseDTO (
  Long id,
  String descripcion,
  String nombre,
  TipoHechizo tipo
  //no muestra personajes porque muchos personajes van a usar el mismo hechizo
){}
