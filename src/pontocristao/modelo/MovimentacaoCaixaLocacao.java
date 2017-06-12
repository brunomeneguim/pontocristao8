package pontocristao.modelo;

import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class MovimentacaoCaixaLocacao extends MovimentacaoCaixaEntrada {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Locacao locacao;

    public Locacao getLocacao() {
        return locacao;
    }

    public void setLocacao(Locacao locacao) {
        this.locacao = locacao;
    }
}
