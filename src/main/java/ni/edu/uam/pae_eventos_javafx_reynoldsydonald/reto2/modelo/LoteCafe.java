package ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto2.modelo;

public class LoteCafe {
    private int id;
    private String productor;
    private double quintales;
    private String variedad;

    public LoteCafe(int id, String productor, double quintales, String variedad) {
        this.id = id;
        this.productor = productor;
        this.quintales = quintales;
        this.variedad = variedad;
    }

    public int getId() { return id; }
    public String getProductor() { return productor; }
    public double getQuintales() { return quintales; }
    public String getVariedad() { return variedad; }

    public void setProductor(String productor) { this.productor = productor; }
    public void setQuintales(double quintales) { this.quintales = quintales; }
    public void setVariedad(String variedad) { this.variedad = variedad; }
}