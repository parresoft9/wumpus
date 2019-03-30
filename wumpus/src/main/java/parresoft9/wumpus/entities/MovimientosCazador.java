package parresoft9.wumpus.entities;

public enum MovimientosCazador {

	AVANZAR, //avanzar una posicion en celdas, depedendiendo de la direccion (orientacion) del cazador
	GIRAR_DERECHA, //giramos 90 grados a la derecha pero nos mantenemos en la misma celda, cambiamos la orientacion 
	GIRAR_IZQUIERDA, //idem hacia la izquierda
	LANZAR_FLECHA,
	SALIR //accion a realizar una vez hemos encontrado el oro; encontrandonos en la casilla de inicio para ganar el juego.
}
