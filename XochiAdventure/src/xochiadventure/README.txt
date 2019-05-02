T-Bird Studios

Melba Geraldine Consuelos Fernández 		A01410921
Humberto González Sánchez			A00822594
Benjamín Valdez Rodríguez			A00822027
Alberto García Viegas				A00822649

Que funciona
- Jugador
	- Salta
	- Se mueve
	- Colisiona con las plataformas
	- Se cae de las plataformas
	- No se sale del nivel
	- Parpadea y pierde una vida cuando es pegado por un chile
	- Dispara una burbuja

- Chiles
	- Se mueven
	- No se salen de su area designada
	- Se muere
	- Pueden soltar un power up cuando mueren, es aletorio

- Comida
	- Se puede recolectar
	- Desaparece

- Powerups
	- Se elige el tipo de power up aletoriamente
	- Se dibuja la imagen correcta dependiendo del tipo

- Fuente
	- Se dibuja
	- Le recupera agua al jugador

- UI
	- Muestra la comida recolectada
	- Muestra la vida

- Render
	- Funciona la camara que sigue al jugador
	- Muestra las cosas correctas
	- Deja de seguir al jugador cuando llega a una de las orillas del nivel

- Logica en general del juego (cargado de niveles, menus, etc)
	- Se puede mover entre los menus
	- Se puede acceder a todos los niveles
	- Se puede ganar y perder el nivel

Que no funciona
- Jugador
	- Se atora en las plataformas cuando las toca de lado
	- Su animación no cubre caminar hacia la derecha

- Comidas
	- Se decide aletoriamente que ingrediente es, por lo que se puede repetir el ingrediente 

- UI
	- No muestra correctamente cuantas burbujas puedes disparar

- Lógica general del juego
	- Aun no se implementa un cargado de nivel correcto, por el momento carga el mismo template y se hace de manera manual, lo único que cambia es el fondo de pantalla

Recomendaciones
- La fuente le regenera el agua al personaje principal, por lo que podrá disparar más. Tiene un tope

