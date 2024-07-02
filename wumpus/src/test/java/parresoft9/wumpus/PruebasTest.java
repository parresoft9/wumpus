package parresoft9.wumpus;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import parresoft9.wumpus.entities.Direccion;
import parresoft9.wumpus.entities.MovimientosCazador;
import parresoft9.wumpus.entities.PercepcionesCazador;
import parresoft9.wumpus.entities.Position;
import parresoft9.wumpus.services.Juego;

public class PruebasTest{
	private Juego wumpus; 
	private Position posWumpus;
	private Position posOro;
	private List<Position> listaPosPozos;
	
	 @Before 
	    public void setUp() { 
	    	wumpus = new Juego(4, 3, 3); 
	    	posWumpus = new Position(2,3); 
	    	posOro = new Position(2,2);
	    	listaPosPozos = new ArrayList();
	    	listaPosPozos.add(new Position(0,3));
			listaPosPozos.add(new Position(3,1));
			listaPosPozos.add(new Position(1,1));
	    } 
	 
	 @Test
		public void sholudPercepcionBrisa() {
			// Inicializar el tablero y colocar los elementos
			wumpus.init(posWumpus, posOro, listaPosPozos);
			wumpus.accion(MovimientosCazador.AVANZAR);
			assertTrue(wumpus.obtenerPercepciones().contains(PercepcionesCazador.BRISA));
		}
	 @Test
		public void shouldPercepcionHedor() {
			// Inicializar el tablero y colocar los elementos
			
			wumpus.init(posWumpus, posOro, listaPosPozos);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.GIRAR_IZQUIERDA);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			pintarTablero(wumpus);
			assertTrue(wumpus.obtenerPercepciones().contains(PercepcionesCazador.HEDOR));
		}

		@Test
		public void shouldTakeOro() {
			// Inicializar el tablero y colocar los elementos
			
			wumpus.init(posWumpus, posOro, listaPosPozos);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.GIRAR_IZQUIERDA);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			pintarTablero(wumpus);
			
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.GIRAR_DERECHA);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			pintarTablero(wumpus);
			assertTrue(wumpus.obtenerPercepciones().contains(PercepcionesCazador.BRILLO));
		}

		@Test
		public void KillToWumpus() {
			// Inicializar el tablero y colocar los elementos
			
			wumpus.init(posWumpus, posOro, listaPosPozos);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.GIRAR_IZQUIERDA);
			pintarTablero(wumpus);
			assertTrue(wumpus.accion(MovimientosCazador.LANZAR_FLECHA).contains(PercepcionesCazador.GRITO));
		}

		@Test
		public void shouldKilledByWumpus() {
			// Inicializar el tablero y colocar los elementos
			
			wumpus.init(posWumpus, posOro, listaPosPozos);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.GIRAR_IZQUIERDA);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			pintarTablero(wumpus);
			assertTrue(wumpus.obtenerPercepciones().contains(PercepcionesCazador.WUMPUS));
		}

		@Test
		public void shouldKilledByPozo() {
			// Inicializar el tablero y colocar los elementos
			
			wumpus.init(posWumpus, posOro, listaPosPozos);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.GIRAR_IZQUIERDA);
			wumpus.accion(MovimientosCazador.AVANZAR);
			pintarTablero(wumpus);
			assertTrue(wumpus.obtenerPercepciones().contains(PercepcionesCazador.POZO));
		}

		@Test
		public void shouldfoundOro() {
			// Inicializar el tablero y colocar los elementos
			
			wumpus.init(posWumpus, posOro, listaPosPozos);
			pintarTablero(wumpus);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.GIRAR_IZQUIERDA);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.GIRAR_IZQUIERDA);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.GIRAR_IZQUIERDA);
			wumpus.accion(MovimientosCazador.AVANZAR);
			wumpus.accion(MovimientosCazador.AVANZAR);
			pintarTablero(wumpus);
			assertTrue(wumpus.accion(MovimientosCazador.SALIR).contains(PercepcionesCazador.FIN));
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

}
