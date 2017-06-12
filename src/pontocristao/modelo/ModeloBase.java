package pontocristao.modelo;

import java.io.*;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@MappedSuperclass()
public abstract class ModeloBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(nullable = false, columnDefinition = "tinyint default false")
    private Boolean excluido = false;

    public long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }
}
