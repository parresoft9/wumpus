package parresoft9.wumpus.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import parresoft9.wumpus.entities.Celda;
import parresoft9.wumpus.entities.Direccion;
import parresoft9.wumpus.entities.MovimientosCazador;
import parresoft9.wumpus.entities.PercepcionesCazador;
import parresoft9.wumpus.entities.Position;

public class Juego {

	private int cells;
	private int pozos;
	private int flechas;
	private Celda[][] tablero;
	private Position posCazador;
	private boolean encontradoOro;
	private boolean wumpusMuerto;
	private boolean cazadorVivo;
	private Direccion direccion;
	
	
	
	
	public int getCells() {
		return cells;
	}

	public void setCells(int cells) {
		this.cells = cells;
	}


	public Position getPosCazador() {
		return posCazador;
	}

	public void setPosCazador(Position posCazador) {
		this.posCazador = posCazador;
	}

	
	public boolean isEncontradoOro() {
		return encontradoOro;
	}

	public void setEncontradoOro(boolean encontradoOro) {
		this.encontradoOro = encontradoOro;
	}

	
	public boolean isWumpusMuerto() {
		return wumpusMuerto;
	}

	public void setWumpusMuerto(boolean wumpusMuerto) {
		this.wumpusMuerto = wumpusMuerto;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Juego(int celdas, int pozos, int flechas) {
		this.cells = celdas;
		this.pozos = pozos;
		this.flechas = flechas;
	}

	public Celda[][] getTablero() {
		return tablero;
	}

	public void setTablero(Celda[][] tablero) {
		this.tablero = tablero;
	}

	/**
	 * Colocamos al cazador en la Position 0,0 del tablero y situamos a las demás
	 * elementos aleatoriamente de forma que no coincidan
	 * 
	 * @return Las percepciones iniciales del cazador
	 */
	public List<PercepcionesCazador> init() {
		// Inicializamos el tablero con todas las celdas vacias
		tablero = new Celda[cells][cells];
		for (int i = 0; i < cells; i++) {
			for (int j = 0; j < cells; j++) {
				tablero[i][j] = Celda.LIBRE;
			}
		}

		// Para que genere aleatoriamente las Positiones de los elementos del tablero
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		// Position del cazador (inicialmente en la casilla 0,0)
		posCazador = new Position();

		// Position del wumbus
		boolean colocado = false;
		while (!colocado) {
			Position posWumpus = new Position(random.nextInt(cells), random.nextInt(cells));
			if (Celda.LIBRE.equals(tablero[posWumpus.getX()][posWumpus.getY()])) {
				tablero[posWumpus.getX()][posWumpus.getY()] = Celda.WUMPUS;
				colocado = true;
			}
		}

		// Position del oro
		colocado = false;
		while (!colocado) {
			Position posOro = new Position(random.nextInt(cells), random.nextInt(cells));
			if (Celda.LIBRE.equals(tablero[posOro.getX()][posOro.getY()])) {
				tablero[posOro.getX()][posOro.getY()] = Celda.ORO;
				colocado = true;
			}
		}

		// Position de los pozo
		int pozosColocados = 0;
		while (pozosColocados < pozos) {
			colocado = false;
			while (!colocado) {
				Position posPozo = new Position(random.nextInt(cells), random.nextInt(cells));
				if (Celda.LIBRE.equals(tablero[posPozo.getX()][posPozo.getY()])) {
					tablero[posPozo.getX()][posPozo.getY()] = Celda.POZO;
					pozosColocados++;
					colocado = true;
				}
			}	
		}

		// Inicializamos variables
		encontradoOro = false;
		wumpusMuerto = false;
		cazadorVivo = true;
		direccion = Direccion.DERECHA;
		
		

		return obtenerPercepciones();
	}

	/**
	 * Colocamos al cazador en la Position 0,0 del tablero 
	 * y situamos a las demás elementos en las Positiones dadas
	 * 
	 * @param posWumpus
	 * @param posOro
	 * @param listaPosPozos
	 */
	public void init(Position posWumpus, Position posOro, List<Position> listaPosPozos) {
		// Inicializamos el tablero con todas las celdas vacias
		tablero = new Celda[cells][cells];
		for (int i = 0; i < cells; i++) {
			for (int j = 0; j < cells; j++) {
				tablero[i][j] = Celda.LIBRE;
			}
		}

		// Position del cazador (inicialmente en la casilla 0,0)
		posCazador = new Position();

		// Position del wumbus
		tablero[posWumpus.getX()][posWumpus.getY()] = Celda.WUMPUS;

		// Position del oro
		tablero[posOro.getX()][posOro.getY()] = Celda.ORO;

		// Position de los pozo
		int pozosColocados = 0;
		while (pozosColocados < pozos) {
			tablero[listaPosPozos.get(pozosColocados).getX()][listaPosPozos.get(pozosColocados).getY()] = Celda.POZO;
			pozosColocados++;
		}

		// Inicializamos variables
		encontradoOro = false;
		wumpusMuerto = false;
		cazadorVivo = true;
		direccion = Direccion.DERECHA;
	}

	/** 
	 * Obtiene las percepciones del cazador de la Position actual en la que se encuentra
	 * 
	 * @return
	 */
	public List<PercepcionesCazador> obtenerPercepciones() {
		List<PercepcionesCazador> listaPercepciones = new ArrayList();

		// Position Actual
		Celda celda = tablero[posCazador.getX()][posCazador.getY()];
		if (Celda.ORO.equals(celda)) {
			listaPercepciones.add(PercepcionesCazador.BRILLO);
			encontradoOro = true;
		}
		else if (Celda.WUMPUS.equals(celda) && !wumpusMuerto) {
			listaPercepciones.add(PercepcionesCazador.WUMPUS);
			cazadorVivo = false;
		}
		else if (Celda.POZO.equals(celda)) {
			listaPercepciones.add(PercepcionesCazador.POZO);
			cazadorVivo = false;
		}

		// Position Arriba
		if (posCazador.getY() + 1 < cells) {
			celda = tablero[posCazador.getX()][posCazador.getY() + 1];
			PercepcionesCazador percepcion = obtenerPercepcion(celda);
			if (percepcion != null) {
				listaPercepciones.add(percepcion);
			}
		}

		// Position Abajo
		if (posCazador.getY() - 1 >= 0) {
			celda = tablero[posCazador.getX()][posCazador.getY() - 1];
			PercepcionesCazador percepcion = obtenerPercepcion(celda);
			if (percepcion != null) {
				listaPercepciones.add(percepcion);
			}
		}

		// Position Derecha
		if (posCazador.getX() + 1 < cells) {
			celda = tablero[posCazador.getX() + 1][posCazador.getY()];
			PercepcionesCazador percepcion = obtenerPercepcion(celda);
			if (percepcion != null) {
				listaPercepciones.add(percepcion);
			}
		}

		// Position Izquierda
		if (posCazador.getX() - 1 >= 0) {
			celda = tablero[posCazador.getX() - 1][posCazador.getY()];
			PercepcionesCazador percepcion = obtenerPercepcion(celda);
			if (percepcion != null) {
				listaPercepciones.add(percepcion);
			}
		}

		return listaPercepciones;
	}

	/**
	 * Obtiene la percepcion en los cuadros adyacentes
	 * 
	 * @param celda
	 * @return
	 */
	private PercepcionesCazador obtenerPercepcion(Celda celda) {
		PercepcionesCazador percepcion = null;
		if (Celda.WUMPUS.equals(celda)) {
			percepcion = PercepcionesCazador.HEDOR;
		}
		else if (Celda.POZO.equals(celda)) {
			percepcion = PercepcionesCazador.BRISA;
		}
		return percepcion;
	}

	/**
	 * Indica si el cazador está vivo (no esta en la misma casilla que WUMBUS ni POZO)
	 * 
	 * @param listaPercepciones
	 * @return
	 */
	public boolean cazadorVivo() {
		return cazadorVivo;
	}

	/**
	 * Indica si el cazador tiene flechas. En caso afirmativo podrá ejecutar accion de Lanzar Flechas
	 * @return
	 */
	public boolean cazadorTieneFlechas() {
		return flechas > 0;
	}

	/**
	 * Indica si el cazador está en la casilla de salida. 
	 * En caso afirmativo podrá ejecutar accion de Salir
	 * @return
	 */
	public boolean cazadorEstaEnCasillaSalida() {
		return posCazador.getX() == 0 && posCazador.getY() == 0;
	}

	/**
	 * Ejecuta una accion: Avanzar, Derecha, Izquierda, Lanzar una Flecha
	 * 
	 * @param accion
	 * @return
	 */
	public List<PercepcionesCazador> accion(MovimientosCazador accion) {
		PercepcionesCazador percepcion = null;
		if (MovimientosCazador.AVANZAR.equals(accion)) {
			percepcion = avanzar();
		}
		else if (MovimientosCazador.GIRAR_DERECHA.equals(accion)) {
			if (Direccion.DERECHA.equals(direccion)) {
				direccion = Direccion.ABAJO;
			}
			else if (Direccion.ABAJO.equals(direccion)) {
				direccion = Direccion.IZQUIERDA;
			}
			else if (Direccion.IZQUIERDA.equals(direccion)) {
				direccion = Direccion.ARRIBA;
			}
			else if (Direccion.ARRIBA.equals(direccion)) {
				direccion = Direccion.DERECHA;
			}
		}
		else if (MovimientosCazador.GIRAR_IZQUIERDA.equals(accion)) {
			if (Direccion.DERECHA.equals(direccion)) {
				direccion = Direccion.ARRIBA;
			}
			else if (Direccion.ARRIBA.equals(direccion)) {
				direccion = Direccion.IZQUIERDA;
			}
			else if (Direccion.IZQUIERDA.equals(direccion)) {
				direccion = Direccion.ABAJO;
			}
			else if (Direccion.ABAJO.equals(direccion)) {
				direccion = Direccion.DERECHA;
			}
		}
		else if (MovimientosCazador.LANZAR_FLECHA.equals(accion)) {
			flechas--;
			if (Direccion.DERECHA.equals(direccion)) {
				for (int i = posCazador.getX() + 1; i<cells; i++) {
					if (Celda.WUMPUS.equals(tablero[i][posCazador.getY()])) {
						wumpusMuerto = true;
						percepcion = PercepcionesCazador.GRITO;
					}
				}
			}
			else if (Direccion.ARRIBA.equals(direccion)) {
				for (int j = posCazador.getY() + 1; j<cells; j++) {
					if (Celda.WUMPUS.equals(tablero[posCazador.getX()][j])) {
						wumpusMuerto = true;
						percepcion = PercepcionesCazador.GRITO;
					}
				}
			}
			else if (Direccion.IZQUIERDA.equals(direccion)) {
				for (int i= posCazador.getX() - 1; i>=0; i--) {
					if (Celda.WUMPUS.equals(tablero[i][posCazador.getY()])) {
						wumpusMuerto = true;
						percepcion = PercepcionesCazador.GRITO;
					}
				}
			}
			else if (Direccion.ABAJO.equals(direccion)) {
				for (int j = posCazador.getY() - 1; j>=0; j--) {
					if (Celda.WUMPUS.equals(tablero[posCazador.getX()][j])) {
						wumpusMuerto = true;
						percepcion = PercepcionesCazador.GRITO;
					}
				}
			}
		}
		else if (MovimientosCazador.SALIR.equals(accion)) {
			if (cazadorEstaEnCasillaSalida() && encontradoOro && cazadorVivo) {
				percepcion = PercepcionesCazador.FIN;
			}
		}

		// Una vez movido, capta las Percepciones de la nueva Position
		List<PercepcionesCazador> percepciones = obtenerPercepciones();
		if (percepcion != null) {
			percepciones.add(0, percepcion);
		}
		return percepciones;
	}

	/**
	 * Avanza una Position. Devuelve percepcion de PARED si no puede avanzar
	 */
	private PercepcionesCazador avanzar() {
		if (Direccion.DERECHA.equals(direccion)) {
			Position posNew = new Position(posCazador.getX() + 1, posCazador.getY());
			return moverCazador(posNew);
		}
		else if (Direccion.IZQUIERDA.equals(direccion)) {
			Position posNew = new Position(posCazador.getX() - 1, posCazador.getY());
			return moverCazador(posNew);
		}
		else if (Direccion.ARRIBA.equals(direccion)) {
			Position posNew = new Position(posCazador.getX(), posCazador.getY() + 1);
			return moverCazador(posNew);
		}
		else if (Direccion.ABAJO.equals(direccion)) {
			Position posNew = new Position(posCazador.getX(), posCazador.getY() - 1);
			return moverCazador(posNew);
		}
		return null;
	}

	/**
	 * Mueve el cazador desde la Position antigua a la nueva. Puede devolver una percepcion de MURO 
	 * 
	 * @param posNew
	 * @return
	 */
	private PercepcionesCazador moverCazador(Position posNew) {
		PercepcionesCazador percepcion = null;
		if (posNew.getX() == cells || posNew.getX() == -1 || posNew.getY() == cells || posNew.getY() == -1) {
			percepcion = PercepcionesCazador.MURO;
		}
		else {
			posCazador = posNew;
		}
		return percepcion;
	}
}
