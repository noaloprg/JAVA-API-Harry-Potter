package lopez.noa.OrmHarryPotterApp.Servicios;

import jakarta.transaction.Transactional;
import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.PersonajeDTO.*;
import lopez.noa.OrmHarryPotterApp.Exception.AlreadyAssignedExcepction;
import lopez.noa.OrmHarryPotterApp.Exception.BrokenWandException;
import lopez.noa.OrmHarryPotterApp.Exception.ResourceNotFound;
import lopez.noa.OrmHarryPotterApp.Mappers.DataHelper;
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

    /**
     * Constantes de la entidades que pueden dar error
     */
    private final String ENTIDAD_PERSONAJE = "Personaje";
    private final String ENTIDAD_CASA = "Casa";
    private final String ENTIDAD_VARITA = "Varita";

    /**
     * Todas las dependencias necesarias
     */
    private final PersonajeRepository personajeRepo;
    private final CasaRepository casaRepo;
    private final HechizoRepository hechizoRepo;
    private final VaritaRepository varitaRepo;

    public PersonajeService(PersonajeRepository repo, CasaRepository casaRepo, HechizoRepository hechRepo, VaritaRepository varitaRepo) {
        this.personajeRepo = repo;
        this.casaRepo = casaRepo;
        this.hechizoRepo = hechRepo;
        this.varitaRepo = varitaRepo;
    }

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
        Personaje p = personajeRepo.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), ENTIDAD_PERSONAJE));
        return PersonajeMapper.toPersonajeResponse(p);
    }

    /**
     *
     * @param palabra Palabra por la cual se filtra y se busca a los personajes que la contengan en su nombre
     * @return lista de {@link  PersonajeResponseDTO}
     */
    public List<PersonajeResponseDTO> getPersonajeContaining(String palabra) {
        return personajeRepo.findByNombreContainingIgnoreCase(palabra)
                .stream().map(
                        personaje -> PersonajeMapper.toPersonajeResponse(personaje)
                ).toList();
    }


    /**
     * Obtiene los personajes de X casa
     *
     * @param nombreCasa Nombre de la {@link Casa} a la que estan vincuados los {@link  Personaje}
     * @return lista de {@link PersonajeResponseDTO}
     */
    /*
    no es transactional porque no hay lazy loading, el id de la casa de perosnaje ya esta cargado por hibernate
    cada metodo de cada repositorio es una consulta independiente, por lo que tampoco se necesita
     */
    public List<PersonajeResponseDTO> getPersonajesByCasa(String nombreCasa) {
        // Utiliza query metods

        // Busca la casa por su nombre
        Casa casa = casaRepo.findByNombreIgnoreCase(nombreCasa)
                .orElseThrow(() -> new ResourceNotFound(String.format("No se encontro la casa con el nombre %s", nombreCasa)));

        // Busca a todos los perosnajes que tengan dicha casa
        List<Personaje> personajesLista = personajeRepo.findAllByCasa(casa);

        return personajesLista.stream()
                .map(personaje -> PersonajeMapper.toPersonajeResponse(personaje)).toList();
    }

    @Override
    @Transactional
    public PersonajeResponseDTO deleteById(Integer id) {
        //manejamos existencia a traves de la busqueda de la entidad
        Personaje personaje = personajeRepo.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), ENTIDAD_PERSONAJE));
        personajeRepo.deleteById(id);
        return PersonajeMapper.toPersonajeResponse(personaje);
    }

    /**
     *
     * @param id  Id del personaje a actualizar
     * @param dto DTO de creacion para copiar todos los atributos en memoria
     * @return personaje actualizado (mismo id, atributos modificados)
     */
    @Transactional
    public Personaje update(Integer id, PersonajeCreateDTO dto) {
        //Buscamos personaje que se quiere actualizar
        Personaje personaje = personajeRepo.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), ENTIDAD_PERSONAJE));

        //buscamos la casa a la que pertenece el personaje a traves del id recibido desde el DTO
        Casa casaNueva = casaRepo.findById(dto.getIdCasa())
                .orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(dto.getIdCasa()), ENTIDAD_PERSONAJE));

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

    /**
     *
     * @param idPersonaje Id del perosnaje al que le queremos asignar la varita
     * @param idVarita    Id de la varita a asignar
     * @return DTO de respuesta que une un resumen del perosnaje y de la varita (de la relacion)
     */
    @Transactional
    public PersonajeVaritaAsignadaResponseDTO addVarita(Integer idPersonaje, Integer idVarita) {
        //obtencion de varita y personaje segun el ID
        Varita var = varitaRepo.findById(idVarita).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(idVarita), ENTIDAD_VARITA));
        Personaje per = personajeRepo.findById(idPersonaje).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(idPersonaje), ENTIDAD_PERSONAJE));

        //verificacion de que la varita no tenga ya un propietario
        if (var.getPersonaje() != null)
            throw new AlreadyAssignedExcepction(idVarita, ENTIDAD_VARITA, ENTIDAD_PERSONAJE);

        //verificar que si esta rota no se asigne
        if (var.getRota()) throw new BrokenWandException(idVarita);

        var.setPersonaje(per);
        per.getVaritas().add(var);
        return PersonajeMapper.toPersonajeVaritaAsignacionResponse(per, var);
    }

    /**
     * Funciona iguaal que el de {@link CasaService}
     *
     * @see CasaService#create(CasaCreateDTO)
     */
    @Transactional
    public PersonajeResponseDTO create(PersonajeCreateDTO dto) {
        //creamos el personaje a traves del DTO
        Personaje personaje = PersonajeMapper.crearPersonajeSimpleDesdeDTO(dto);

        //buscamos la casa a la que pertenece el personaje a traves del id recibido desde el DTO
        Casa casa = casaRepo.findById(dto.getIdCasa())
                .orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(dto.getIdCasa()), ENTIDAD_CASA));

        //asignamos la casa al personaje creado porque es NOT NULL
        personaje.setCasa(casa);
        casa.getPersonajes().add(personaje);

        personajeRepo.save(personaje);
        return PersonajeMapper.toPersonajeResponse(personaje);
    }

    /**
     * Crea un personaje junto con una varita (tambien nueva)
     *
     * @param dto DTO para crear una varita y un personaje a la vez
     * @return DTO de respuesta de esta creacion combinada
     */
    @Transactional
    public PersonajeVaritaResponseDTO createConVarita(PersonajeVaritaCreateDTO dto) {
        /*
            en el Handler ya se crea el personaje junto con su varita
            relacion + definicion de ambas entidades
         */
        Personaje personaje = PersonajeMapper.crearPersonajeJuntoVarita(dto);
        //lo obtiene del dto para no tener que ir personaje -> casa -> idCasa
        Casa casa = getCasaById(dto.getIdCasa());

        //asociacion
        personaje.setCasa(casa);
        casa.getPersonajes().add(personaje);

        personajeRepo.save(personaje);
        return PersonajeMapper.toPersonajeVaritaResponse(personaje);
    }

    /**
     * Funciona igual que {@link PersonajeService#createConVarita(PersonajeVaritaCreateDTO)}
     */
    @Transactional
    public PersonajeHechizoResponseDTO createConHechizo(PersonajeHechizoCreateDTO dto) {
        //todos los elementos necesarios
        Personaje personaje = PersonajeMapper.getPersonajeFromPersonajeHechizo(dto);
        List<Hechizo> listaHechizos = PersonajeMapper.getHechizosFromPersonajeHechizos(dto);
        Casa casa = getCasaById(dto.getIdCasa());

        // Recorre todos los hechizos para asegurar que el hechizo si existe se asocie y si no se cree
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

    /**
     * Metodo privado para obtener la casa segun ID y asi poder lanzar una excepcion
     *
     * @param idCasa Id de la casa que se busca
     * @return Entidad de {@link  Casa} porque el metodo de {@link CasaRepository#findById(Object)} devuelve un objeto de tipo {@link lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaResponseDTO}
     */
    private Casa getCasaById(Integer idCasa) {
        return casaRepo.findById(idCasa).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(idCasa), ENTIDAD_CASA));
    }
}
