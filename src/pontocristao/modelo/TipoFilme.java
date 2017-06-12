package pontocristao.modelo;

import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class TipoFilme extends ModeloBase {

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "tipoFilme")
    private Set<Filme> filmes = new HashSet<Filme>(0);

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(Set<Filme> filmes) {
        this.filmes = filmes;
    }
}
