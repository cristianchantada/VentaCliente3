package ventana2;

public class Cliente {

    private String nombre;
    private String nif;
    private String tarjetaCredito;

    public Cliente(String nombre, String nif, String tarjetaCredito) {
        this.nombre = nombre;
        this.nif = nif;
        this.tarjetaCredito = tarjetaCredito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setTarjetaCredito(String tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }
}
