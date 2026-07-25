package lopez.noa.OrmHarryPotterApp.Servicios;

import jakarta.transaction.Transactional;
import lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO.*;
import lopez.noa.OrmHarryPotterApp.Exception.AlreadyAssignedExcepction;
import lopez.noa.OrmHarryPotterApp.Exception.BrokenWandException;
import lopez.noa.OrmHarryPotterApp.Exception.ResourceNotFound;
import lopez.noa.OrmHarryPotterApp.Mappers.PersonajeMapper;
import lopez.noa.OrmHarryPotterApp.Modelos.Casa;
import lopez.noa.OrmHarryPotterApp.Modelos.Hechizo;
import lopez.noa.OrmHarryPotterApp.Modelos.Personaje;
import lopez.noa.OrmHarryPotterApp.Modelos.Varita;
import lopez.noa.OrmHarryPotterApp.Repositorios.CasaRepository;
import lopez.noa.OrmHarryPotterApp.Repositorios.HechizoRepository;
import lopez.noa.OrmHarryPotterApp.Repositorios.PersonajeRepository;
import lopez.noa.OrmHarryPotterApp.Repositorios.VaritaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonajeService implements IModeloService<PersonajeResponseDTO, Integer> {

    private static String ENTIDAD_PERSONAJE = "Personaje";
    private static String ENTIDAD_CASA = "Casa";
    private static String ENTIDAD_HECHIZO = "Hechizo";
    private static String ENTIDAD_VARITA = "Varita";


    private final PersonajeRepository personajeRepo;
    private final CasaRepository casaRepo;
    private final HechizoRepository hechizoRepo;
    private final VaritaRepository varitaRepo;

    //inyeccion de dependencias
    public PersonajeService(PersonajeRepository repo, CasaRepository casaRepo, HechizoRepository hechRepo, VaritaRepository varitaRepo) {
        this.personajeRepo = repo;
        this.casaRepo = casaRepo;
        this.hechizoRepo = hechRepo;
        this.varitaRepo = varitaRepo;
    }

    //METODOS DE OBTENCION
    @Override
    public List<PersonajeResponseDTO> getAll() {
        return personajeRepo.findAll()
                .stream()
                .map(p ->
                        new PersonajeResponseDTO(
                                p.getId(),
                                p.getNombre(),
                                p.getSangre(),
                                p.getCasa().getId()
                        ))
                .toList();
    }

    @Override
    public PersonajeResponseDTO getById(Integer id) {
        Personaje p = personajeRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, ENTIDAD_PERSONAJE));
        return PersonajeMapper.toPersonajeResponse(p);
    }

    public List<PersonajeResponseDTO> getPersonajeContaining(String palabra) {
        return personajeRepo.findByNombreContainingIgnoreCase(palabra)
                .stream().map(
                        personaje -> PersonajeMapper.toPersonajeResponse(personaje)
                ).toList();
    }

    /*
    no es transactional porque no hay lazy loading, el id de la casa de perosnaje ya esta cargado por hibernate
    cada metodo de cada repositorio es una consulta independiente, por lo que tampoco se necesita
     */
    public List<PersonajeResponseDTO> getPersonajesByCasa(String nombreCasa) {
        Casa casa = casaRepo.findByNombreIgnoreCase(nombreCasa)
                .orElseThrow(() -> new ResourceNotFound(String.format("No se encontro la casa con el nombre %s", nombreCasa)));

        List<Personaje> personajesLista = personajeRepo.findByCasa(casa);

        return personajesLista.stream()
                .map(personaje -> PersonajeMapper.toPersonajeResponse(personaje)).toList();
    }

    //METODOS DE ELIMINACION
    @Override
    @Transactional
    public PersonajeResponseDTO deleteById(Integer id) {
        //manejamos existencia a traves de la busqueda de la entidad
        Personaje personaje = personajeRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, ENTIDAD_PERSONAJE));
        personajeRepo.deleteById(id);
        return PersonajeMapper.toPersonajeResponse(personaje);
    }

    //METODOS DE ACTUALIZACION
    @Transactional
    public Personaje actualizarPersonajeSimple(Integer id, PersonajeCreateDTO dto) {
        //Buscamos personaje que se quiere actualizar
        Personaje personaje = personajeRepo.findById(id).orElseThrow(() -> new ResourceNotFound((id), ENTIDAD_PERSONAJE));

        //buscamos la casa a la que pertenece el personaje a traves del id recibido desde el DTO
        Casa casaNueva = casaRepo.findById(dto.getIdCasa())
                .orElseThrow(() -> new ResourceNotFound(dto.getIdCasa(), ENTIDAD_PERSONAJE));

        Casa casaVieja = personaje.getCasa();

        PersonajeMapper.asignarTodosCamposSimplesPersonaje(dto, personaje);

        //si no es la misma casa se tienen que actualizar las listas de las casas y tambien las casa del personaje
        if (casaVieja.getId() != casaNueva.getId()) {
            //elimina el personaje de la casa antigua
            casaVieja.getPersonajes().removeIf(p -> p.getId() == personaje.getId());
            //añade el nuevo personaje a la casa
            casaNueva.getPersonajes().add(personaje);

            personaje.setCasa(casaNueva);
        }
        //si es la misma casa solo se actualiza el personaje de la lista
        else {
            //elimina y vueve a añadir para asegurar que los datos sean correctos
            casaNueva.getPersonajes().removeIf(p -> p.getId() == personaje.getId());
            casaNueva.getPersonajes().add(personaje);
        }
        personajeRepo.save(personaje);
        return personaje;
    }

    @Transactional
    public PersonajeVaritaAsignadaResponseDTO asignarVaritaPersonaje(Integer idPersonaje, Integer idVarita) {
        //obtencion de varita y personaje segun el ID
        Varita var = varitaRepo.findById(idVarita).orElseThrow(() -> new ResourceNotFound(idVarita, ENTIDAD_VARITA));
        Personaje per = personajeRepo.findById(idPersonaje).orElseThrow(() -> new ResourceNotFound(idPersonaje, ENTIDAD_PERSONAJE));

        //verificacion de que la varita no tenga ya un propietario
        if (var.getPersonaje() != null)
            throw new AlreadyAssignedExcepction(idVarita, ENTIDAD_VARITA, ENTIDAD_PERSONAJE);

        //verificar que si esta rota no se asigne
        if (var.getRota()) throw new BrokenWandException(idVarita);

        var.setPersonaje(per);
        per.getVaritas().add(var);
        return PersonajeMapper.toPersonajeVaritaAsignacionResponse(per, var);
    }

    //METODOS DE CREACION
    @Transactional
    public PersonajeResponseDTO crearPersonajeSimple(PersonajeCreateDTO dto) {
        //creamos el personaje a traves del DTO
        Personaje personaje = PersonajeMapper.crearPersonajeSimpleDesdeDTO(dto);

        //buscamos la casa a la que pertenece el personaje a traves del id recibido desde el DTO
        Casa casa = casaRepo.findById(dto.getIdCasa())
                .orElseThrow(() -> new ResourceNotFound(dto.getIdCasa(), ENTIDAD_CASA));

        //asignamos la casa al personaje creado porque es NOT NULL
        personaje.setCasa(casa);
        casa.getPersonajes().add(personaje);

        personajeRepo.save(personaje);
        return PersonajeMapper.toPersonajeResponse(personaje);
    }

    //MIRAR LO DE TRANSACTIONAL EN ESTOS CASOS
    @Transactional
    public PersonajeVaritaResponseDTO crearPersonajeConVarita(PersonajeVaritaCreateDTO dto) {
        /*
            en el Handler ya se crea el personaje junto con su varita
            relacion + definicion de ambas entidades
         */
        Personaje personaje = PersonajeMapper.crearPersonajeJuntoVarita(dto);
        //lo obtiene del dto para no tener que ir personaje -> casa -> idCasa
        Casa casa = obtenerCasaSegunID(dto.getIdCasa());

        //asociacion
        personaje.setCasa(casa);
        casa.getPersonajes().add(personaje);

        personajeRepo.save(personaje);
        return PersonajeMapper.toPersonajeVaritaResponse(personaje);
    }

    @Transactional
    public PersonajeHechizoResponseDTO crearPersonajeConHechizo(PersonajeHechizoCreateDTO dto) {
        //todos los elementos necesarios
        Personaje personaje = PersonajeMapper.getPersonajeFromPersonajeHechizo(dto);
        List<Hechizo> listaHechizos = PersonajeMapper.getHechizosFromPersonajeHechizos(dto);
        Casa casa = obtenerCasaSegunID(dto.getIdCasa());

        for (Hechizo h : listaHechizos) {
            Hechizo hechizoFinal = hechizoRepo.findByNombre(h.getNombre()).orElseGet(
                    //devuevle le hechizo ya creado con su ID
                    () -> hechizoRepo.save(h)
            );
            //relacion
            hechizoFinal.getPersonajes().add(personaje);
        }
        //relaciones
        casa.getPersonajes().add(personaje);
        personaje.setHechizos(listaHechizos);

        return PersonajeMapper.toPersonajeHechizoResponse(personaje);
    }


    //METODOS DE LA CLASE (SERVICIO)
    private Casa obtenerCasaSegunID(Integer idCasa) {
        return casaRepo.findById(idCasa).orElseThrow(() -> new ResourceNotFound(idCasa, ENTIDAD_CASA));
    }
}
