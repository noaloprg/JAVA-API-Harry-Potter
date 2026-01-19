package lopez.noa.OrmHarryPotterApp.Repositorios;

import lopez.noa.OrmHarryPotterApp.Modelos.Varita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaritaRepository extends JpaRepository<Varita, Integer> {
    List<Varita> findByRota(Boolean rota);

    List<Varita> findByNucleoContainingIgnoreCase(String nucleo);
}
