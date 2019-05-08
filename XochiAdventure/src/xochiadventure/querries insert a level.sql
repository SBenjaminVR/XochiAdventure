Create Table Levelg (
    levelID		    int	    NOT NULL,
    width           int     NOT NULL,
    height          int     NOT NULL,
	PRIMARY KEY (levelID),
    
);

CREATE TABLE Fountain (
    fountainID      int     NOT NULL,
    levelID         int     NOT NULL,
    posX            int     NOT NULL,
    posY            int     NOT NULL,
    PRIMARY KEY (fountainID)
);

CREATE TABLE Player (
    playerID		int		NOT NULL,
	levelID			int		NOT NULL,
	posX			int		NOT NULL,
	posY			int		NOT NULL,
	width			int		NOT NULL,
	height			int		NOT NULL,
    speedX          int     NOT NULL,
    lives           int     NOT NULL,
	PRIMARY KEY (playerID)
);

CREATE TABLE Platform (
    platformID		int		NOT NULL,
	levelID			int		NOT NULL,
	posX			int		NOT NULL,
	posY			int		NOT NULL,
	width			int		NOT NULL,
	height			int		NOT NULL,
	PRIMARY KEY (platformID)
);

CREATE TABLE Chile (
    enemyID		    int		NOT NULL,
	levelID			int		NOT NULL,
	posX			int		NOT NULL,
	posY			int		NOT NULL,
	width			int		NOT NULL,
	height			int		NOT NULL,
    speedX          int     NOT NULL,
    leftLimit       INT     NOT NULL,
    rightLimit      INT     NOT NULL,  
	PRIMARY KEY (enemyID)
);

CREATE TABLE Comida (
    comidaID		int		NOT NULL,
	levelID			int		NOT NULL,
	posX			int		NOT NULL,
	posY			int		NOT NULL,
	width			int		NOT NULL,
	height			int		NOT NULL,
	PRIMARY KEY (comidaID)
);

CREATE TABLE Letrero (
    letreroID       int     NOT NULL,
    tipo            int     NOT NULL,
    nivel           INT     NOT NULL,
    posX            int     NOT NULL,
    posY            int     NOT NULL,
    PRIMARY KEY (letreroID)
);

CREATE TABLE Pico (
    letreroID       int     NOT NULL,
    dir             CHAR(1) NOT NULL,
    nivel           INT     NOT NULL,
    posX            int     NOT NULL,
    posY            int     NOT NULL,
    PRIMARY KEY (letreroID)
);

-- Level --------------------------------------------------------------
-- Nivel 1
INSERT INTO LevelG VALUES (1, 3100, 2100);

-- Nivel 2
INSERT INTO LevelG VALUES (2, 3100, 2000);

-- Nivel 3
INSERT INTO LevelG VALUES (3, 0, 0);

-- Fuente --------------------------------------------------------------
-- Nivel 1
INSERT INTO Fountain VALUES (1, 1, 1500, 600);

-- Nivel 2
INSERT INTO Fountain VALUES (2, 2, 1400, 500);

-- Nivel 3
INSERT INTO Fountain VALUES (3, 3, 1400, 500);

-- Plataformas --------------------------------------------------------------
-- Nivel 1
INSERT INTO Platform VALUES (1, 1, 0, 250, 500, 100);
INSERT INTO Platform VALUES (1, 1, 1300, 250, 500, 100);
INSERT INTO Platform VALUES (3, 1, 2600, 250, 500, 100);

INSERT INTO Platform VALUES (4, 1, 650, 500, 500, 100);
INSERT INTO Platform VALUES (5, 1, 1950, 500, 500, 100);
INSERT INTO Platform VALUES (6, 1, 300, 800, 500, 100);

INSERT INTO Platform VALUES (7, 1, 800, 800, 500, 100);
INSERT INTO Platform VALUES (8, 1, 1300, 800, 500, 100);
INSERT INTO Platform VALUES (9, 1, 1800, 800, 500, 100);

INSERT INTO Platform VALUES (10, 1, 2300, 800, 500, 100);
INSERT INTO Platform VALUES (11, 1, 0, 1100, 150, 30);
INSERT INTO Platform VALUES (12, 1, 1550 - 75, 1100, 150, 30);

INSERT INTO Platform VALUES (13, 1, 2950, 1100, 150, 30);
INSERT INTO Platform VALUES (14, 1, 0, 1350, 500, 100);
INSERT INTO Platform VALUES (15, 1, 950, 1350, 500, 100);

INSERT INTO Platform VALUES (16, 1, 1650, 1350, 500, 100);
INSERT INTO Platform VALUES (17, 1, 2600, 1350, 500, 100);
INSERT INTO Platform VALUES (18, 1, 650, 1650, 150, 30);

INSERT INTO Platform VALUES (19, 1, 2300, 1650, 150, 30);
INSERT INTO Platform VALUES (20, 1, 0, 1900, 500, 100);
INSERT INTO Platform VALUES (21, 1, 950, 1900, 500, 100);

INSERT INTO Platform VALUES (22, 1, 1450, 1900, 500, 100);
INSERT INTO Platform VALUES (23, 1, 1650, 1900, 500, 100);
INSERT INTO Platform VALUES (24, 1, 2600, 1900, 500, 100);

-- Nivel 2
INSERT INTO Platform VALUES (25, 2, 0, 250, 500, 100);
INSERT INTO Platform VALUES (26, 2, 1300, 250, 500, 100);
INSERT INTO Platform VALUES (27, 2, 2600, 250, 500, 100);

INSERT INTO Platform VALUES (28, 2, 650, 500, 500, 100);
INSERT INTO Platform VALUES (29, 2, 1950, 500, 500, 100);

INSERT INTO Platform VALUES (30, 2, -400, 800, 500, 100);
INSERT INTO Platform VALUES (31, 2, 300, 800, 500, 100);
INSERT INTO Platform VALUES (32, 2, 1000, 800, 500, 100);
INSERT INTO Platform VALUES (33, 2, 1500, 800, 500, 100);
INSERT INTO Platform VALUES (34, 2, 1600, 800, 500, 100);
INSERT INTO Platform VALUES (35, 2, 2300, 800, 500, 100);
INSERT INTO Platform VALUES (36, 2, 3000, 800, 500, 100);

INSERT INTO Platform VALUES (36, 2, 800, 1100, 500, 100);
INSERT INTO Platform VALUES (37, 2, 1800, 1100, 500, 100);

INSERT INTO Platform VALUES (38, 2, 550, 1400, 150, 30);
INSERT INTO Platform VALUES (39, 2, 1475, 1400, 150, 30);
INSERT INTO Platform VALUES (40, 2, 2400, 1400, 150, 30);

INSERT INTO Platform VALUES (41, 2, 0, 1700, 400, 100);
INSERT INTO Platform VALUES (42, 2, 575, 1700, 300, 100);
INSERT INTO Platform VALUES (43, 2, 1050, 1700, 300, 100);
INSERT INTO Platform VALUES (44, 2, 1500, 1700, 100, 20);
INSERT INTO Platform VALUES (45, 2, 1750, 1700, 300, 100);
INSERT INTO Platform VALUES (46, 2, 2225, 1700, 300, 100);
INSERT INTO Platform VALUES (47, 2, 2700, 1700, 400, 100);

-- Nivel 3
INSERT INTO Platform VALUES (48, 3, 225, 250, 500, 100);
INSERT INTO Platform VALUES (49, 3, 725, 250, 500, 100);
INSERT INTO Platform VALUES (50, 3, 925, 250, 500, 100);
INSERT INTO Platform VALUES (51, 3, 1875, 250, 500, 100);
INSERT INTO Platform VALUES (52, 3, 2375, 250, 500, 100);
INSERT INTO Platform VALUES (53, 3, 2575, 250, 500, 100);

INSERT INTO Platform VALUES (54, 3, 1575, 500, 150, 30);

INSERT INTO Platform VALUES (55, 3, 1300, 700, 150, 30);
INSERT INTO Platform VALUES (56, 3, 1850, 700, 150, 30);

INSERT INTO Platform VALUES (57, 3, 300, 950, 50, 50);
INSERT INTO Platform VALUES (58, 3, 450, 900, 500, 100);
INSERT INTO Platform VALUES (59, 3, 1150, 900, 50, 50);
INSERT INTO Platform VALUES (60, 3, 1400, 900, 500, 100);
INSERT INTO Platform VALUES (61, 3, 2100, 900, 50, 50);
INSERT INTO Platform VALUES (62, 3, 2350, 900, 500, 100);
INSERT INTO Platform VALUES (63, 3, 2950, 950, 50, 50);

INSERT INTO Platform VALUES (64, 3, 1100, 1150, 150, 30);
INSERT INTO Platform VALUES (65, 3, 2050, 1150, 150, 30);

INSERT INTO Platform VALUES (66, 3, 50, 1500, 50, 50);
INSERT INTO Platform VALUES (67, 3, 400, 1500, 50, 50);
INSERT INTO Platform VALUES (68, 3, 575, 1450, 500, 100);
INSERT INTO Platform VALUES (69, 3, 875, 1450, 500, 100);
INSERT INTO Platform VALUES (70, 3, 1975, 1450, 800, 100);
INSERT INTO Platform VALUES (71, 3, 2850, 1450, 150, 30);

INSERT INTO Platform VALUES (73, 3, 1575, 1650, 150, 30);

INSERT INTO Platform VALUES (74, 3, 0, 2000, 500, 100);
INSERT INTO Platform VALUES (75, 3, 500, 2000, 500, 100);
INSERT INTO Platform VALUES (76, 3, 1250, 2000, 50, 50);
INSERT INTO Platform VALUES (77, 3, 1600, 2000, 100, 100);
INSERT INTO Platform VALUES (78, 3, 2000, 2000, 50, 50);
INSERT INTO Platform VALUES (79, 3, 2300, 2000, 500, 100);
INSERT INTO Platform VALUES (80, 3, 2800, 2000, 500, 100);


-- Comida --------------------------------------------------------------
-- Nivel 1
INSERT INTO Comida VALUES (1, 1, 225, 200, 50, 50);
INSERT INTO Comida VALUES (2, 1, 1525, 200, 50, 50);
INSERT INTO Comida VALUES (3, 1, 2925, 200, 50, 50);
INSERT INTO Comida VALUES (4, 1, 1525, 1050, 50, 50);
INSERT INTO Comida VALUES (5, 1, 225, 1850, 50, 50);
INSERT INTO Comida VALUES (6, 1, 1525, 1850, 50, 50);
INSERT INTO Comida VALUES (7, 1, 2925, 1850, 50, 50);

-- Nivel 2
INSERT INTO Comida VALUES (8, 2, 1525, 200, 50, 50);
INSERT INTO Comida VALUES (9, 2, 2900, 200, 50, 50);
INSERT INTO Comida VALUES (10, 2, 200, 825, 50, 50);
INSERT INTO Comida VALUES (11, 2, 2900, 825, 50, 50);
INSERT INTO Comida VALUES (12, 2, 1525, 1100, 50, 50);
INSERT INTO Comida VALUES (13, 2, 50, 1650, 50, 50);
INSERT INTO Comida VALUES (14, 2, 1525, 1650, 50, 50);


-- Nivel 3
INSERT INTO Comida VALUES (15, 3, 800, 200, 50, 50);
INSERT INTO Comida VALUES (16, 3, 2450, 200, 50, 50);
INSERT INTO Comida VALUES (17, 3, 300, 750, 50, 50);

INSERT INTO Comida VALUES (18, 3, 2950, 750, 50, 50);
INSERT INTO Comida VALUES (19, 3, 500, 1950, 50, 50);
INSERT INTO Comida VALUES (20, 3, 2800, 1950, 50, 50);

INSERT INTO Comida VALUES (21, 3, 1625, 1950, 50, 50);



-- Player --------------------------------------------------------------
-- Nivel 1
INSERT INTO Player VALUES (1, 1, 1475, 650, 100, 100, 5, 3);

-- Nivel 2
INSERT INTO Player VALUES (2, 2, 1475, 650, 100, 100, 5, 3);

-- Nivel 3
INSERT INTO Player VALUES (3, 3, 1600, 700, 100, 100, 5, 3); -- falta el id de la plataforma

-- Chiles --------------------------------------------------------------
-- Nivel 1
INSERT INTO Chile VALUES (1, 1, 1350, 200, 50, 50, 5, 1300, 1550);
INSERT INTO Chile VALUES (2, 1, 1750, 200, 50, 50, 5, 1550, 1800);
INSERT INTO Chile VALUES (3, 1, 955, 1300, 50, 50, 5, 950, 1450);
INSERT INTO Chile VALUES (4, 1, 2100, 1300, 50, 50, 5, 1650, 2150);
INSERT INTO Chile VALUES (5, 1, 955, 1850, 50, 50, 5, 950, 1550);
INSERT INTO Chile VALUES (6, 1, 2100, 1850, 50, 50, 5, 1550, 2150);

-- Nivel 2
INSERT INTO Chile VALUES (7, 2, 10, 200, 50, 50, 5, 0, 500);
INSERT INTO Chile VALUES (8, 2, 3040, 200, 50, 50, 5, 2600, 3100);
INSERT INTO Chile VALUES (9, 2, 310, 750, 50, 50, 5, 300, 800);
INSERT INTO Chile VALUES (10, 2, 2740, 750, 50, 50, 5, 2300, 2800);
INSERT INTO Chile VALUES (11, 2, 810, 1050, 50, 50, 5, 800, 1300);
INSERT INTO Chile VALUES (12, 2, 2240, 1050, 50, 50, 5, 1800, 2300);
INSERT INTO Chile VALUES (13, 2, 10, 1650, 50, 50, 5, 0, 400,);
INSERT INTO Chile VALUES (14, 2, 3040, 1650, 50, 50, -1, 5, 2700, 3100);


-- Nivel 3
INSERT INTO Chile VALUES (15, 3);

-- Letrero --------------------------------------------------------------

-- Nivel 1

INSERT INTO Letrero VALUES (1, 'aviso', 1, 650, 450);
INSERT INTO Letrero VALUES (2, 'aviso', 1, 2200, 450);
INSERT INTO Letrero VALUES (3, 'peligro', 1, 450, 1850);
INSERT INTO Letrero VALUES (4, 'peligro', 1, 950, 1850);
INSERT INTO Letrero VALUES (5, 'peligro', 1, 2150, 1850);
INSERT INTO Letrero VALUES (6, 'peligro', 1, 2600, 1850);

-- Nivel 2

INSERT INTO Letrero VALUES (7, 'peligro', 2, 2600, 1850);

-- Nivel 3

INSERT INTO Letrero VALUES (18, 'peligro', 3, 2600, 1850);

-- Pico --------------------------------------------------------------

-- Nivel 1
-- no hay picos

-- Nivel 2
INSERT INTO Pico VALUES (1, '', 2, 650, 450);

-- Nivel 1
INSERT INTO Pico VALUES (1, '', 3, 650, 450);