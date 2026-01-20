package lopez.noa.OrmHarryPotterApp.Servicios;

import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaSummaryDTO;
import lopez.noa.OrmHarryPotterApp.Exception.ResourceNotFound;
import lopez.noa.OrmHarryPotterApp.Mappers.VaritaMapper;
import lopez.noa.OrmHarryPotterApp.Modelos.Varita;
import lopez.noa.OrmHarryPotterApp.Repositorios.PersonajeRepository;
import lopez.noa.OrmHarryPotterApp.Repositorios.VaritaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VaritaService implements IModeloService<VaritaResponseDTO, Integer> {

    private static String NOMBRE_ENTIDAD = "Varita";

    /**
     * Comentarios generales y que se repiten en el resto de servicios
     *
     * @see CasaService
     */
    private final VaritaRepository varitaRepo;
    private final PersonajeRepository personajeRepo;

    public VaritaService(VaritaRepository varitaRepo, PersonajeRepository personajeRepo) {
        this.varitaRepo = varitaRepo;
        this.personajeRepo = personajeRepo;
    }

    //GET
    @Override
    @Transactional(readOnly = true)
    public List<VaritaResponseDTO> getAll() {
        return varitaRepo.findAll()
                .stream()
                .map(varita ->
                        VaritaMapper.toVaritaResponse(varita))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VaritaResponseDTO getById(Integer id) {
        Varita varita = varitaRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, NOMBRE_ENTIDAD));
        return VaritaMapper.toVaritaResponse(varita);
    }


    @Transactional(readOnly = true)
    public List<VaritaResponseDTO> getAllRotas(boolean rota) {
        List<Varita> listaVaritas = varitaRepo.findByRota(rota);
        return listaVaritas.stream().map(
                v ->
                        VaritaMapper.toVaritaResponse(v)
        ).toList();
    }

    public List<VaritaResponseDTO> getByNucleo(String nucleo) {
        return varitaRepo.findByNucleoContainingIgnoreCase(nucleo)
                .stream()
                .map(varita ->
                        VaritaMapper.toVaritaResponse(varita))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VaritaSummaryDTO> getResumen() {
        return varitaRepo.findAll()
                .stream()
                .map(v -> VaritaMapper.toVaritaSummary(v)).toList();
    }

    public List<VaritaSummaryDTO> getOrdenadasUsadas(Boolean descendente, Boolean usadas) {
        //crea el modo de ordenacion segun el booleano pasado por parametros
        Sort ordenacion = descendente ? Sort.by("longitud").descending() : Sort.by("longitud").ascending();

        //si solo quiere las que estan usadas
        List<Varita> listaRespuesta = usadas
                ? varitaRepo.findByPersonajeIsNotNull(ordenacion)
                : varitaRepo.findByPersonajeIsNull(ordenacion);

        return listaRespuesta.stream().map(var -> VaritaMapper.toVaritaSummary(var)).toList();
    }


    //ELIMINAR Y ACTUALIZAR
    @Override
    @Transactional
    public VaritaResponseDTO deleteById(Integer id) {
        //manejamos existencia a traves de la busqueda de la entidad
        Varita varita = varitaRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, NOMBRE_ENTIDAD));
        varitaRepo.deleteById(id);
        return VaritaMapper.toVaritaResponse(varita);
    }

    @Transactional
    public VaritaSummaryDTO updateVarita(Integer id, VaritaCreateDTO dto) {
        Varita existente = varitaRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, NOMBRE_ENTIDAD));
        VaritaMapper.asignarTodosCamposVarita(dto, existente);
        varitaRepo.save(existente);
        return VaritaMapper.toVaritaSummary(existente);
    }

    @Transactional
    /*
    usuario solo indica el id y a traves del servicio, se modifica
    el servicio añade el resto de atributos (sino se perderia)
     */
    public VaritaResponseDTO setRota(int id) {
        Varita var = varitaRepo.findById(id).orElseThrow(() -> new ResourceNotFound(id, "varita"));
        var.setRota(true);
        return VaritaMapper.toVaritaResponse(var);
    }

    //CREAR
    @Transactional
    public VaritaSummaryDTO createVarita(VaritaCreateDTO dto) {
        //no tiene porque estar vinculada a un personaje
        Varita varita = VaritaMapper.crearVaritaDesdeDTO(dto);
        varitaRepo.save(varita);
        return VaritaMapper.toVaritaSummary(varita);
    }


}