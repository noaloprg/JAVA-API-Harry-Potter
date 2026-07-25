package lopez.noa.OrmHarryPotterApp.Mappers;

import java.math.BigInteger;

/**
 * Clase para conversiones de tipos de datos
 */
public class DataHelper {

    /**
     *
     * @param num numero de tipo integer
     * @return numero de tipo Integer convertido a BigInteger
     */
    public static BigInteger fromIntegerToBigInt(Integer num){
        return BigInteger.valueOf(num);
    }
}
