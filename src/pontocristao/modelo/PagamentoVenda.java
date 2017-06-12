package pontocristao.modelo;

import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class PagamentoVenda extends Pagamento {

    @ManyToOne(optional = false)
    private Venda venda;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

}
