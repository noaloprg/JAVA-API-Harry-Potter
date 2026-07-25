package lopez.noa.OrmHarryPotterApp.Servicios;

import jakarta.transaction.Transactional;
import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaResponseDTO;
import lopez.noa.OrmHarryPotterApp.Exception.ResourceNotFound;
import lopez.noa.OrmHarryPotterApp.Mappers.CasaMapper;
import lopez.noa.OrmHarryPotterApp.Mappers.DataHelper;
import lopez.noa.OrmHarryPotterApp.Modelos.Casa;
import lopez.noa.OrmHarryPotterApp.Repositorios.CasaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasaService implements IModeloService<CasaResponseDTO, Integer> {

    private static String NOMBRE_ENTIDAD = "Casa";
    /**
     * Repositorio JPA para la persistencia de datos.
     * <p>
     * Se utiliza {@link CasaRepository} mediante inyección por constructor.
     * Se define como {@code final} para garantizar la inmutabilidad y evitar
     * modificaciones durante la ejecución.
     * </p>
     */
    private final CasaRepository casaRepository;

    public CasaService(CasaRepository casaRepo) {
        this.casaRepository = casaRepo;
    }

    @Override
    public List<CasaResponseDTO> getAll() {
        //devuelve un DTO de respuesta, porque no se va a acceder a la entidad en si
        return casaRepository.findAll()
                .stream()
                .map(casa ->
                        CasaMapper.toCasaResponse(casa))
                .toList();
    }

    @Override
    public CasaResponseDTO getById(Integer id) {
        Casa casa = casaRepository.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), NOMBRE_ENTIDAD));
        return CasaMapper.toCasaResponse(casa);
    }

    @Override
    @Transactional
    public CasaResponseDTO deleteById(Integer id) {
        //manejamos existencia a traves de la busqueda de la entidad
        Casa casa = casaRepository.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), NOMBRE_ENTIDAD));
        casaRepository.deleteById(id);
        return CasaMapper.toCasaResponse(casa);
    }

    /**
     *
     * @param dto DTO de creacion simple
     * @return objeto completo con id generado en la BD
     */
    @Transactional
    public Casa create(CasaCreateDTO dto) {
        Casa casa = CasaMapper.crearCasaDesdeDTO(dto);
        Casa casaGuardada = casaRepository.save(casa);
        return casaGuardada;
    }

    /**
     * Actuliza una entidad de casa
     *
     * @param id  identificador de la casa
     * @param dto DTO de tipo {@link CasaCreateDTO} ya que se copian todos los atributos en memoria
     * @return devuelve le objeto completo
     */
    @Transactional
    public Casa update(Integer id, CasaCreateDTO dto) {
        Casa existente = casaRepository.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), NOMBRE_ENTIDAD));
        CasaMapper.asignarTodosCamposCasa(dto, existente);
        // en este caso no se crea una nuevo objeto porque el id es el mismo y JPA ya lo actualiza en memoria
        casaRepository.save(existente);
        return existente;
    }
}

