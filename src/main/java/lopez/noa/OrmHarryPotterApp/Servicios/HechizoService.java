package lopez.noa.OrmHarryPotterApp.Servicios;

import jakarta.transaction.Transactional;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.HechizoDTO.HechizoResponseDTO;
import lopez.noa.OrmHarryPotterApp.Exception.AlreadyExistsException;
import lopez.noa.OrmHarryPotterApp.Exception.ResourceNotFound;
import lopez.noa.OrmHarryPotterApp.Mappers.HechizoMapper;
import lopez.noa.OrmHarryPotterApp.Modelos.Hechizo;
import lopez.noa.OrmHarryPotterApp.Repositorios.HechizoRepository;
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

    @Override
    @Transactional
    public HechizoResponseDTO deleteById(BigInteger id) {
        //manejamos existencia a traves de la busqueda de la entidad
        Hechizo hechizo = hechizoRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, NOMBRE_ENTIDAD));
        hechizoRepo.deleteById(id);
        return HechizoMapper.toHechizoResponse(hechizo);
    }

    @Transactional
    public Hechizo createHechizo(HechizoCreateDTO dto) {
        Hechizo hechizo = HechizoMapper.crearHechizoDesdeDTO(dto);
        hechizoRepo.save(hechizo);
        return hechizo;
    }

    @Transactional
    public List<HechizoResponseDTO> createMuchosHechizos(List<HechizoCreateDTO> listaHechizosCreate) {
        List<HechizoResponseDTO> listaRespuestas = new ArrayList<>();

        for (HechizoCreateDTO dto : listaHechizosCreate) {
            Optional<Hechizo> existente = hechizoRepo.findByNombre(dto.nombre());

            if (existente.isPresent()) throw new AlreadyExistsException(NOMBRE_ENTIDAD);
            else {
                Hechizo nuevo = HechizoMapper.crearHechizoDesdeDTO(dto);
                hechizoRepo.save(nuevo);
                listaRespuestas.add(HechizoMapper.toHechizoResponse(nuevo));
            }
        }
        return listaRespuestas;
    }

    @Transactional
    public Hechizo update(BigInteger id, HechizoCreateDTO dto) {
        Hechizo existente = hechizoRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, NOMBRE_ENTIDAD));
        HechizoMapper.asignarTodosCamposHechizo(dto, existente);
        hechizoRepo.save(existente);
        return existente;
    }
}
