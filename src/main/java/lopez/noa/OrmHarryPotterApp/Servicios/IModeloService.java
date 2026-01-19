package lopez.noa.OrmHarryPotterApp.Servicios;

import java.util.List;

public interface IModeloService<D, ID> {
    //SOLO GET Y DELETE PORQUE CREATE Y UPDATE PUEDEN TENER VARIOS DTO

    //devuelve un DTO porque solo es para mostrar
    List<D> getAll();

    //trabaja con la entidad porque sirve para varias operaciones
    D getById(ID id);

    //preferible que no devuelva nada
    D deleteById(ID id);

}
