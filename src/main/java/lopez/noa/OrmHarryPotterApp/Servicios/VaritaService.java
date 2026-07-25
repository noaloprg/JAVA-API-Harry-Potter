package lopez.noa.OrmHarryPotterApp.Servicios;

import lopez.noa.OrmHarryPotterApp.DTO.CasaDTO.CasaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaCreateDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaResponseDTO;
import lopez.noa.OrmHarryPotterApp.DTO.VaritaDTO.VaritaSummaryDTO;
import lopez.noa.OrmHarryPotterApp.Exception.ResourceNotFound;
import lopez.noa.OrmHarryPotterApp.Mappers.DataHelper;
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
        Varita varita = varitaRepo.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), NOMBRE_ENTIDAD));
        return VaritaMapper.toVaritaResponse(varita);
    }


    /**
     * Obtiene todas las varitas dependiendo de si estan rotas o no
     *
     * @param rota {@code true} devuelve todas las varitas rotas. {@code false} devuelve todas las varitas que no estan rotas
     * @return Lista de {@link VaritaResponseDTO}
     */
    @Transactional(readOnly = true)
    public List<VaritaResponseDTO> getAllRotas(boolean rota) {
        List<Varita> listaVaritas = varitaRepo.findByRota(rota);
        return listaVaritas.stream().map(
                v ->
                        VaritaMapper.toVaritaResponse(v)
        ).toList();
    }

    /**
     * Obtiene la varita segun el nombre del nucleo
     *
     * @param nucleo Cadena de texto que se usa para buscar coincidentes
     * @return Lista de {@link  VaritaResponseDTO}
     */
    public List<VaritaResponseDTO> getByNucleo(String nucleo) {
        return varitaRepo.findByNucleoContainingIgnoreCase(nucleo)
                .stream()
                .map(varita ->
                        VaritaMapper.toVaritaResponse(varita))
                .toList();
    }

    /**
     * Obtiene un resumen de cada varita
     *
     * @return Lista de un DTO resumen de cada varita
     */
    @Transactional(readOnly = true)
    public List<VaritaSummaryDTO> getResumen() {
        return varitaRepo.findAll()
                .stream()
                .map(v -> VaritaMapper.toVaritaSummary(v)).toList();
    }

    /**
     * Obtiene todas las baritas ordenadas por longitud y usadas
     *
     * @param descendente {@code true} se ordena descendente. {@code false} Se ordena ascendente
     * @param usadas      {@code true} Las que si esten relacionadas con {@link lopez.noa.OrmHarryPotterApp.Modelos.Personaje}. {@code false} Las que no esten relacionadas con nadie
     * @return lista DTO de resumen de la varita
     */
    public List<VaritaSummaryDTO> getOrdenadasUsadas(Boolean descendente, Boolean usadas) {
        //crea el modo de ordenacion segun el booleano pasado por parametros
        Sort ordenacion = descendente ? Sort.by("longitud").descending() : Sort.by("longitud").ascending();

        //si solo quiere las que estan usadas
        List<Varita> listaRespuesta = usadas
                ? varitaRepo.findByPersonajeIsNotNull(ordenacion)
                : varitaRepo.findByPersonajeIsNull(ordenacion);

        return listaRespuesta.stream().map(var -> VaritaMapper.toVaritaSummary(var)).toList();
    }

    @Override
    @Transactional
    public VaritaResponseDTO deleteById(Integer id) {
        //manejamos existencia a traves de la busqueda de la entidad
        Varita varita = varitaRepo.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), NOMBRE_ENTIDAD));
        varitaRepo.deleteById(id);
        return VaritaMapper.toVaritaResponse(varita);
    }

    /**
     * Funciona iguaal que el de {@link CasaService}
     *
     * @see CasaService#update(Integer, CasaCreateDTO)
     */
    @Transactional
    public VaritaSummaryDTO update(Integer id, VaritaCreateDTO dto) {
        Varita existente = varitaRepo.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), NOMBRE_ENTIDAD));
        VaritaMapper.asignarTodosCamposVarita(dto, existente);
        varitaRepo.save(existente);
        return VaritaMapper.toVaritaSummary(existente);
    }

    /**
     *
     * @param id Id de la varita que se desea romper, en caso de que ya este rota da igual
     * @return DTO de respuesta de tipo {@link VaritaResponseDTO}
     */
    @Transactional
    /*
    usuario solo indica el id y a traves del servicio, se modifica
    el servicio añade el resto de atributos (sino se perderia)
     */
    public VaritaResponseDTO setRota(int id) {
        Varita var = varitaRepo.findById(id).orElseThrow(() -> new ResourceNotFound(DataHelper.fromIntegerToBigInt(id), "varita"));
        var.setRota(true);
        return VaritaMapper.toVaritaResponse(var);
    }

    /**
     * Funciona iguaal que el de {@link CasaService}
     *
     * @see CasaService#create(CasaCreateDTO)
     */
    @Transactional
    public VaritaSummaryDTO create(VaritaCreateDTO dto) {
        //no tiene porque estar vinculada a un personaje
        Varita varita = VaritaMapper.crearVaritaDesdeDTO(dto);
        varitaRepo.save(varita);
        return VaritaMapper.toVaritaSummary(varita);
    }


}