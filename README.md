wumpus
======

A simple test designed for the interview process at Connected Health Services. 

Please fork this repo in your account and develop a Java program that implements the rules for the Wumpus Game (see included Powerpoint). You are expected to write the complete game logic, and use simple text interactions to drive the game play (like the text-based adventure games from the old days, http://en.wikipedia.org/wiki/Text-based_game).

Bonus points for unit tests ;).

Please use Eclipse as your IDE and whatever version of Java you are most confortable with (Scala or Groovy also welcomed). Use whatever framework you prefer for your unit tests.




WUMPUS GAME
===================================================================0

Comandos de lanzamiento.
--------------------------------
java -jar Wumpus.jar #celdas(nxn) #pozos #flechas

Funcionamiento
--------------------------------

El cazador se encuentra al inicio siempre situado en la casilla de salida con orientación-dirección a la derecha (ESTE) que en la matriz esta definida en la (0,0).
Sus acciones son avanzar (moverse una celda en función de la dirección-orientación en que se encuentre),
girar a la derecha(gira 90º hacia la derecha pero mantiene posición en la misma celda)
girar a la izquierda (idem hacia la izquierda)
Disparar flecha (si se encuentra alineado con el wumpus y dispara, lo mata y puede ocupar su celda sin preocupación alguna)
Salir (si ha pasado por la celda donde se encuentra el oro y la posición del cazador es la inicial puede Salir para ganar la partida)

Las posiciones de los pozos y del wumpus se inicializan aleatoriamente.
Se pinta antes de cada acción el estado del tablero para que quede mas claro a la hora de jugar
(se podría haber metido como parámetro si pintarlo o no)

El cazador puede tomar una de entre cuatro representaciones posibles:
@->  :orientacion hacia la derecha-ESTE (es decir esta mirando hacia la DERECHA)
<-@  :orientacion hacia la izquierda-OESTE
@¡  :orientacion hacia arriba-NORTE
@!  :orientacion hacia abajo-SUR

Se incluye también una clase de tests.

