package parresoft9.wumpus.controller;

import parresoft9.wumpus.services.JuegoBO;

public class Principal {

	public static void main(String[] args) {
		if (args.length != 3) {
			throw new RuntimeException("Se necesitan tres parametros celdas pozos  y flechas");
		}

		int celdas;
		int pozos;
		int flechas;
		try {
			celdas = Integer.parseInt(args[0]);
			pozos = Integer.parseInt(args[1]);
			flechas = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Parametros incorrectos. Ejecute con: wumpus n celdas (para un tablero de nxn) pozos flechas. Ejemplo: wumpus 10 3 3");
		}

		// Verificaciones
		if (celdas < 2) {
			throw new RuntimeException("Al menos el tablero será 2x2");
		}
		if (pozos < 1) {
			throw new RuntimeException("Configure al menos 1 pozo");
		}
		if (pozos + 3 >= celdas*celdas) {
			throw new RuntimeException("El tablero es demasiado pequeño. Intentelo de nuevo");
		}

		JuegoBO juego = new JuegoBO();
		juego.start(celdas, pozos, flechas);
	}

}
