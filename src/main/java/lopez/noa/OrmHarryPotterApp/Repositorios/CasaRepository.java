package lopez.noa.OrmHarryPotterApp.Repositorios;

import lopez.noa.OrmHarryPotterApp.Modelos.Casa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CasaRepository extends JpaRepository<Casa, Integer>{
    Optional<Casa> findByNombreIgnoreCase(String nombre);
}