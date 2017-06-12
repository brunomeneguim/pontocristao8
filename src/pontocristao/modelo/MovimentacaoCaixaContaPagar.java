package pontocristao.modelo;

import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class MovimentacaoCaixaContaPagar extends MovimentacaoCaixaSaida {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ContaPagar contaPagar;

    public ContaPagar getContaPagar() {
        return contaPagar;
    }

    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
    }
}
