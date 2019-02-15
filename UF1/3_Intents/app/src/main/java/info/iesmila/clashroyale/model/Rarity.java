package info.iesmila.clashroyale.model;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Usuari
 */
public enum Rarity {
    COMMON,
    RARE,
    EPIC;    
    public static List<Rarity> getLlista(){
        return Arrays.asList(Rarity.values());
    }
}
