package pontocristao.modelo;

import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class MovimentacaoCaixaDeposito extends MovimentacaoCaixaEntrada {

    @Column(nullable = false)
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
