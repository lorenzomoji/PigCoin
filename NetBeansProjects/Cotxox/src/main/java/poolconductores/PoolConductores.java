
package poolconductores;

import carrera.Carrera;
import tarifa.Tarifa;
import conductor.Conductor;

public class PoolConductores {
    
    private Conductor [] poolConductores = null;
    
    public PoolConductores (Conductor [] poolcConductores) {
        this.poolConductores = poolConductores;
    }
    
    public Conductor[] getPoolConductores () {
        return poolConductores;
    }
}
