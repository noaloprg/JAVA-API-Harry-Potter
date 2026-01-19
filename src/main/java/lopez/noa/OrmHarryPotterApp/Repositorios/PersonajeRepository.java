package lopez.noa.OrmHarryPotterApp.Repositorios;

import lopez.noa.OrmHarryPotterApp.Modelos.Casa;
import lopez.noa.OrmHarryPotterApp.Modelos.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {
    List<Personaje> findByNombreContainingIgnoreCase(String nombre);
    List<Personaje> findByCasa(Casa casa);
}
