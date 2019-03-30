package parresoft9.wumpus.services;

import java.io.IOException;
import java.util.List;

import parresoft9.wumpus.entities.Direccion;
import parresoft9.wumpus.entities.MovimientosCazador;
import parresoft9.wumpus.entities.PercepcionesCazador;

public class JuegoBO {

	/**
	 * Arranca el juego
	 */
	public void start(int celdas, int pozos, int flechas) {
		Juego wumpus = new Juego(celdas, pozos, flechas);

		mostrarPercepciones(wumpus.init());

		int numMaxIteraciones = 100;
		while (numMaxIteraciones > 0) {
			
			
			mostrarAcciones(wumpus);
			
			//Pintar tablero
			pintarTablero(wumpus);
			MovimientosCazador accion = null;
			while (accion == null) {
				accion = leerAccion(wumpus);
			}

			List<PercepcionesCazador> percepciones = wumpus.accion(accion);
			mostrarPercepciones(percepciones);
			if (!wumpus.cazadorVivo()) {
				System.out.println("El cazador está muerto. FIN");
				System.exit(0);
			}
			if (percepciones != null && percepciones.contains(PercepcionesCazador.FIN)) {
				System.out.println(PercepcionesCazador.FIN.toString());
				System.exit(0);
			}

			numMaxIteraciones--;
		}
	}

	/**
	 * Muestra al usuario las percepciones del cazador
	 * 
	 * @param percepciones
	 */
	private void mostrarPercepciones(List<PercepcionesCazador> percepciones) {
		if (percepciones == null || percepciones.isEmpty()) {
			System.out.println(PercepcionesCazador.VACIA.toString());
		}
		else {
			for (PercepcionesCazador percepcion : percepciones) {
				System.out.println(percepcion.toString());
			}
		}
	}

	/**
	 * Muestra las acciones que puede ejecutar el usuario
	 * 
	 * @param wumpus
	 */
	private void mostrarAcciones(Juego wumpus) {
		String mensaje = "Acción: A-Avanzar D-Derecha I-Izquierda ";
		if (wumpus.cazadorTieneFlechas()) {
			mensaje += "F-Lanzar Flecha ";
		}
		if (wumpus.cazadorEstaEnCasillaSalida()) {
			mensaje += "S-Salir";
		}
		System.out.println(mensaje);
	}
	
	private void pintarTablero(Juego wumpus) {
		System.out.println();
		System.out.println("#####################################################################");
		for (int v=wumpus.getCells()-1; v>=0; v--) {
			StringBuffer fila = new StringBuffer();
			for (int h=0; h<wumpus.getCells(); h++) {
				if (wumpus.getPosCazador().getX()==h && wumpus.getPosCazador().getY() == v) {
					if (Direccion.DERECHA.equals(wumpus.getDireccion())){
						fila.append((wumpus.getTablero()[h][v]).toString()).append("@->").append(" , ");
					} else if (Direccion.IZQUIERDA.equals(wumpus.getDireccion())){
						fila.append((wumpus.getTablero()[h][v]).toString()).append("<-@").append(" , ");
					} else if (Direccion.ARRIBA.equals(wumpus.getDireccion())){
						fila.append((wumpus.getTablero()[h][v]).toString()).append("@¡").append(" , ");
					} else if (Direccion.ABAJO.equals(wumpus.getDireccion())){
						fila.append((wumpus.getTablero()[h][v]).toString()).append("@!").append(" , ");
					}
					
				}else {
					fila.append((wumpus.getTablero()[h][v]).toString()).append(" , ");
				}
				
			}
			
			System.out.println(fila);
		}
		System.out.println("#####################################################################");
		if (wumpus.isWumpusMuerto()) {
			System.out.println("El wumpus está muerto, puede ocupar su celda sin miedo. Recoge el oro y sal lo antes posible!!!");
		}
		if (wumpus.isEncontradoOro()) {
			System.out.println("¡¡¡Oro encontrado. Intenta salir lo antes posible para ganar la partida!!!");
		}
		System.out.println();
	}

	/**
	 * Lee la accion que ha elegido el usuario
	 * 
	 * @param wumpus
	 * @return Accion a ejecutar o null si accion no valida
	 */
	private MovimientosCazador leerAccion(Juego wumpus) {
		try {
			char c = (char) System.in.read();
			if (c == 'A' || c == 'a') {
				return MovimientosCazador.AVANZAR;
			}
			else if (c == 'D' || c == 'd') {
				return MovimientosCazador.GIRAR_DERECHA;
			}
			else if (c == 'I' || c == 'i') {
				return MovimientosCazador.GIRAR_IZQUIERDA;
			}
			else if ((c == 'F' || c == 'f') && wumpus.cazadorTieneFlechas()) {
				return MovimientosCazador.LANZAR_FLECHA;
			}
			else if ((c == 'S' || c == 's') && wumpus.cazadorEstaEnCasillaSalida()) {
				return MovimientosCazador.SALIR;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
