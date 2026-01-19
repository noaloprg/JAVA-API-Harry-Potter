package lopez.noa.OrmHarryPotterApp.Mappers;

import jakarta.transaction.Transactional;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoResponseDTO;
import lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO.*;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;
import lopez.noa.OrmHarryPotterApp.Modelos.Hechizo;
import lopez.noa.OrmHarryPotterApp.Modelos.Personaje;
import lopez.noa.OrmHarryPotterApp.Modelos.Varita;

import java.util.ArrayList;
import java.util.List;

public class PersonajeMapper {
    //asigna campos de un DTO  de creacion a un personaje ya existente (abstraccion y para PUT)
    public static void asignarTodosCamposSimplesPersonaje(PersonajeCreateDTO dto, Personaje personaje) {
        personaje.setNombre(dto.getNombre());
        personaje.setFechaNacimiento(dto.getFechaNacimiento());
        personaje.setSangre(dto.getSangre());
        //la asignacion de la casa se hace en el servicio ya que se necesita acceder a la entidad casa
    }

    //CREACION
    //crea un personaje desde un DTO de creacion
    public static Personaje crearPersonajeSimpleDesdeDTO(PersonajeCreateDTO dto) {
        Personaje personaje = new Personaje();
        //asigna todos los campos a un personaje que ya existe (creado antes)
        asignarTodosCamposSimplesPersonaje(dto, personaje);
        return personaje;
    }

    //creacion personaje + varita
    @Transactional
    public static Personaje crearPersonajeJuntoVarita(PersonajeVaritaCreateDTO dto) {
        //se usa lista porque no se puede hacer .add() a un elemento que no se ha creado
        List<Varita> listaVaritas = new ArrayList<>();
        Varita varitaCreada;

        /*
        creamos el personaje
        no da error al usar otro DTO porque el que se esta usando esta heredando del que pide
         */
        Personaje personaje = PersonajeMapper.crearPersonajeSimpleDesdeDTO(dto);

        //creamos las varitas y las guardamos en una lista para asignarlas al personaje
        if (dto.getCrearVarita() != null && !dto.getCrearVarita().isEmpty()) {
            for (VaritaCreateDTO crearVarita : dto.getCrearVarita()) {

                varitaCreada = VaritaMapper.crearVaritaDesdeDTO(crearVarita);

                //RELACIONAR SIEMPRE AMBOS LADOS
                varitaCreada.setPersonaje(personaje);

                listaVaritas.add(varitaCreada);
            }
        }
        //relacionar
        personaje.setVaritas(listaVaritas);

        return personaje;
    }

     /* creacion personaje + hechizo
        las relaciones se hacen despues de verificar que el hechizo no existe
     */

    //OBTENCION
    //obtiene solo el personaje del DTO combinado
    public static Personaje getPersonajeFromPersonajeHechizo(PersonajeHechizoCreateDTO dto) {
        return PersonajeMapper.crearPersonajeSimpleDesdeDTO(dto);
    }

    //devulve solo la lista introducida por el usuario para asi poder verificar despues con el repositorio su existencia
    public static List<Hechizo> getHechizosFromPersonajeHechizos(PersonajeHechizoCreateDTO dto) {
        List<Hechizo> listaHechizos = new ArrayList<>();

        if (dto.getListahechizos() != null && !dto.getListahechizos().isEmpty())

            listaHechizos = dto.getListahechizos()
                    .stream().map(
                            //aqui ya es tipo hechizo
                            h -> HechizoMapper.crearHechizoDesdeDTO(h)
                    ).toList();

        return listaHechizos;
    }

    //CONVERSORES
    public static PersonajeResponseDTO toPersonajeResponse(Personaje p) {
        return new PersonajeResponseDTO(p.getId(), p.getNombre(), p.getSangre(), p.getCasa().getId());
    }

    public static PersonajeVaritaAsignadaResponseDTO toPersonajeVaritaAsignacionResponse(Personaje p, Varita v) {
        return new PersonajeVaritaAsignadaResponseDTO(p.getId(), p.getNombre(), VaritaMapper.toVaritaResponse(v));
    }

    //crea un DTO resumen de Personaje y Hechizo
    public static PersonajeHechizoResponseDTO toPersonajeHechizoResponse(Personaje p) {
        List<HechizoResponseDTO> listaResponseHechizo = new ArrayList<>();
        HechizoResponseDTO hechizoResponse;

        //crea el Response de perosnaje
        PersonajeResponseDTO dtoPersonaje = toPersonajeResponse(p);

        if (p.getHechizos() != null && !p.getHechizos().isEmpty()) {
            //crea el response de cada hechizo
            listaResponseHechizo = p.getHechizos()
                    .stream().map(
                            hechizo -> HechizoMapper.toHechizoResponse(hechizo)
                    ).toList();
        }
        //combina Response del personaje y de la lista de hechizos
        return new PersonajeHechizoResponseDTO(dtoPersonaje, listaResponseHechizo);
    }

    //obtiene el DTO combinado de personaje y varita a traves de un perosnaje
    public static PersonajeVaritaResponseDTO toPersonajeVaritaResponse(Personaje p) {

        //inicializar para poder devolver
        List<VaritaResponseDTO> listaVaritasResponse = new ArrayList<>();

        if (p.getVaritas() != null && !p.getVaritas().isEmpty()) {
            //obtener las varitas individuales de personaje para crear el DTO de Varita
            listaVaritasResponse =
                    p.getVaritas().stream().map(
                            v ->
                                    VaritaMapper.toVaritaResponse(v)
                    ).toList();

        }
        //devuelve combinado
        return new PersonajeVaritaResponseDTO(
                toPersonajeResponse(p), listaVaritasResponse
        );
    }
}
