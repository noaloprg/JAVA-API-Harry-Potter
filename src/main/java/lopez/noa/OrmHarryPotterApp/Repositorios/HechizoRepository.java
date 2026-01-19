package lopez.noa.OrmHarryPotterApp.Repositorios;

import lopez.noa.OrmHarryPotterApp.Modelos.Hechizo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface HechizoRepository extends JpaRepository<Hechizo, BigInteger> {
    Optional<Hechizo> findByNombre(String nombre);
}
