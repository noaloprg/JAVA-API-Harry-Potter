package lopez.noa.OrmHarryPotterApp.Mappers;

import lopez.noa.OrmHarryPotterApp.Modelos.Varita;

public class VaritaMapperHelper {
    public static String obtenerNombrePerosnaje(Varita v){
        return (v.getPersonaje() == null) ? "Sin propietario" : v.getPersonaje().getNombre();
    }
}
