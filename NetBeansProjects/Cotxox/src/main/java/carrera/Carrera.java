
package carrera;


public class Carrera {
    
    private int tiempoEsperado = 0;
    private int tiempoCarrera = 0;
    private int costeTotal = 0;
    private String conductor = null;
    
    
    public Carrera(int tiempoEsperado, int tiempoCarrera, int costeTotal, String conductor) {
        
        this.tiempoEsperado = tiempoEsperado;
        this.tiempoCarrera = tiempoCarrera;
        this.costeTotal = costeTotal;
        this.conductor = conductor;
    }
    
    public int getTiempoEsperado () {
        return tiempoEsperado;
    }
    
    public int getTiempoCarrera () {
        return tiempoCarrera;
    }
    
    public int getCosteTotal () {
        return costeTotal;
    }
    
    public String getCondutor() {
        return conductor;
    }
    
    //LÓGICA
    
    
}
