package parresoft9.wumpus.entities;

public enum PercepcionesCazador {
	VACIA("Nada en el horizonte"),
	WUMPUS("En mi casilla se encuentra el wumpus"),
	HEDOR("Puedo percibir el hedor del wumpus"),
	POZO("He entrado en un pozo"),
	BRISA("Puedo percibir la brisa de un pozo"),
	BRILLO("Puedo percibir el brillo del oro"),
	MURO("Puedo percibir una pared"),
	GRITO("Puedo percibir el grito de un wumpus"),
	FIN("He encontrado el oro y he salido vivo");

	private final String percepcion;

	PercepcionesCazador(final String percepcion) {
		this.percepcion = percepcion;
    }

    
    @Override
    public String toString() {
        return percepcion;
    }
}
