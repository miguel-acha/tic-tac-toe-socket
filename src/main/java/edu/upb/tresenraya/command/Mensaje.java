package edu.upb.tresenraya.command;

import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase para representar el comando de mensaje.
 * Código de comando: 0012
 */
@Getter
@Setter
public class Mensaje extends Comando {
    private String contenido;

    public Mensaje(String contenido) {
        super();
        super.setCodigoComando("0012");
        this.contenido = contenido;
    }

    public Mensaje() {}

    @Override
    public void parsear(String mensaje) throws Exception {
        String[] s = mensaje.split(Pattern.quote("|"));
        if (s.length != 2) {
            throw new Exception("Comando inválido");
        }
        super.setCodigoComando(s[0]);
        this.contenido = s[1];
    }

    @Override
    public String getComando() {
        return super.getCodigoComando() + "|" + this.contenido + System.lineSeparator();
    }

    @Override
    public String toString() {
        return "0012|" + contenido;
    }
}
