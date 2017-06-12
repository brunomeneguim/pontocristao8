package pontocristao.modelo;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Marcondes
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MovimentacaoCaixaSaida extends MovimentacaoCaixa {

}
