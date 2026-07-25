package lopez.noa.OrmHarryPotterApp.Servicios;

import jakarta.transaction.Transactional;
import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoResponseDTO;
import lopez.noa.OrmHarryPotterApp.Exception.AlreadyExistsException;
import lopez.noa.OrmHarryPotterApp.Exception.ResourceNotFound;
import lopez.noa.OrmHarryPotterApp.Mappers.HechizoMapper;
import lopez.noa.OrmHarryPotterApp.Modelos.Hechizo;
import lopez.noa.OrmHarryPotterApp.Repositorios.HechizoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HechizoService implements IModeloService<HechizoResponseDTO, BigInteger> {

    private static String NOMBRE_ENTIDAD = "Hechizo";

    /**
     * Comentarios generales y que se repiten en el resto de servicios
     *
     * @see CasaService
     */
    private final HechizoRepository hechizoRepo;

    public HechizoService(HechizoRepository hechizoRepo) {
        this.hechizoRepo = hechizoRepo;
    }

    @Override
    public List<HechizoResponseDTO> getAll() {
        return hechizoRepo.findAll()
                .stream()
                .map(hechizo ->
                        new HechizoResponseDTO(
                                hechizo.getId(),
                                hechizo.getDescripcion(),
                                hechizo.getNombre(),
                                hechizo.getTipo()
                        ))
                .toList();
    }

    @Override
    public HechizoResponseDTO getById(BigInteger id) {
        Hechizo h = hechizoRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, NOMBRE_ENTIDAD));
        return HechizoMapper.toHechizoResponse(h);
    }

    /**
     *
     * @param paginacion Paginacion que se obtiene en la peticion
     * @return lista de {@link  HechizoResponseDTO}
     */
    public Page<HechizoResponseDTO> getSegunPaginado(Pageable paginacion) {
        // aplicacion de paginacion directa, atraves del metodo del repositorio
        return hechizoRepo.findAll(paginacion)
                .map(hechizo -> HechizoMapper.toHechizoResponse(hechizo));
    }

    /**
     * Obtiene todos los hechizos paginados y ordenados (otra forma de paginar)
     *
     * @param paginacion     Paginacion de la peticion
     * @param tipoOrdenacion {@code asc} se ordena de forma ascendente. {@code desc} Se ordena de forma descendente.
     *                       <p> En caso de que no se reciba nada no se ordenara</p>
     *                       <p> Es un string porque las peticiones se hacen a traves de URL (string)</p>
     * @return lista de {@link HechizoResponseDTO}
     */
    public Page<HechizoResponseDTO> getSegunPaginadoOrdenado(Pageable paginacion, String tipoOrdenacion) {
        //por defecto Sort no ordenara nada
        Sort ordenacion = Sort.unsorted();

        //define el tipo de ordenacion segun la palabra que venga
        if (tipoOrdenacion != null) {
            if (tipoOrdenacion.equalsIgnoreCase("desc")) ordenacion = Sort.by("nombre").descending();
            else if (tipoOrdenacion.equalsIgnoreCase("asc")) ordenacion = Sort.by("nombre").ascending();
        }

        //crea la paginacion junto con la ordenacion
        Pageable paginacionCompleo = PageRequest.of(
                /*
                segun los valores que vengan de la URL del usuario,
                 y sino por los valores por defecto que tiene Pageable
                 */
                paginacion.getPageNumber(),
                paginacion.getPageSize(),
                ordenacion
        );

        //lo aplica al metodo del repositorio
        return hechizoRepo.findAll(paginacionCompleo).map(h -> HechizoMapper.toHechizoResponse(h));

    }

    @Override
    @Transactional
    public HechizoResponseDTO deleteById(BigInteger id) {
        //manejamos existencia a traves de la busqueda de la entidad
        Hechizo hechizo = hechizoRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, NOMBRE_ENTIDAD));
        hechizoRepo.deleteById(id);
        return HechizoMapper.toHechizoResponse(hechizo);
    }

    /**
     * @see CasaService#update(Integer, CasaCreateDTO)
     */
    @Transactional
    public Hechizo update(BigInteger id, HechizoCreateDTO dto) {
        Hechizo existente = hechizoRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, NOMBRE_ENTIDAD));
        HechizoMapper.asignarTodosCamposHechizo(dto, existente);
        hechizoRepo.save(existente);
        return existente;
    }

    /**
     * @see CasaService#create(CasaCreateDTO)
     */
    @Transactional
    public Hechizo create(HechizoCreateDTO dto) {
        Hechizo hechizo = HechizoMapper.crearHechizoDesdeDTO(dto);
        hechizoRepo.save(hechizo);
        return hechizo;
    }

    /**
     * Atraves de una lista de DTO crea una serie de hechizos en una sola peticion
     *
     * @param listaHechizosCreate Lista de DTOs de creacion de hechizos de tipo  {@link  HechizoCreateDTO}
     * @return Lista de los hechizos creados de tipo  {@link HechizoResponseDTO}
     */
    @Transactional
    public List<HechizoResponseDTO> createMasivo(List<HechizoCreateDTO> listaHechizosCreate) {
        // lista de los hechizos creados correctamente
        List<HechizoResponseDTO> listaRespuestas = new ArrayList<>();

        // se recorren todos para realizar verificaciones
        for (HechizoCreateDTO dto : listaHechizosCreate) {
            Optional<Hechizo> existente = hechizoRepo.findByNombre(dto.getNombre());

            // verificacion de existencia, para evitar duplicados
            if (existente.isPresent()) throw new AlreadyExistsException(NOMBRE_ENTIDAD);
            else {
                // en caso de que no haya registrado un hechizo igual se crea
                Hechizo hechizoMemoria = HechizoMapper.crearHechizoDesdeDTO(dto);
                // hechizo con ID de la BD
                Hechizo hechizoNuevo = hechizoRepo.save(hechizoMemoria);
                listaRespuestas.add(HechizoMapper.toHechizoResponse(hechizoNuevo));
            }
        }
        return listaRespuestas;
    }

}
