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
    width           int     NOT NULL,
    height          int     NOT NULL,
    PRIMARY KEY (fountainID),
    FOREIGN KEY (levelID) REFERENCES LevelG (levelID)
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
    leftLimit       int     NOT NULL,
    rightLimit      int     NOT NULL,  
	PRIMARY KEY (platformID),
    FOREIGN KEY (levelID) REFERENCES LevelG (levelID)
);

CREATE TABLE Platform (
    platformID		int		NOT NULL,
	levelID			int		NOT NULL,
	posX			int		NOT NULL,
	posY			int		NOT NULL,
	width			int		NOT NULL,
	height			int		NOT NULL,
	PRIMARY KEY (platformID),
    FOREIGN KEY (levelID) REFERENCES LevelG (levelID)
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
	PRIMARY KEY (enemyID),
    FOREIGN KEY (levelID) REFERENCES LevelG (levelID)
);

CREATE TABLE Comida (
    comidaID		int		NOT NULL,
	levelID			int		NOT NULL,
	posX			int		NOT NULL,
	posY			int		NOT NULL,
	width			int		NOT NULL,
	height			int		NOT NULL,
	PRIMARY KEY (comidaID),
    FOREIGN KEY (levelID) REFERENCES LevelG (levelID)
);

-- Level --------------------------------------------------------------
-- Nivel 1
INSERT INTO LevelG VALUES (1, 3100, 2100);

-- Nivel 2
INSERT INTO LevelG VALUES (2, 0, 0);

-- Nivel 3
INSERT INTO LevelG VALUES (3, 0, 0);

-- Fuente --------------------------------------------------------------
-- Nivel 1
INSERT INTO Fountain VALUES (1, 1, 1400, 500, 300, 300);

-- Nivel 2

-- Nivel 3

-- Plataformas --------------------------------------------------------------
-- Nivel 1
INSERT INTO Platform VALUES (1);

-- Nivel 2

-- Nivel 3

-- Comida --------------------------------------------------------------
-- Nivel 1
INSERT INTO Comida VALUES (1);

-- Nivel 2

-- Nivel 3

-- Player --------------------------------------------------------------
-- Nivel 1
INSERT INTO Player VALUES (1);

-- Nivel 2

-- Nivel 3

-- Chiles --------------------------------------------------------------
-- Nivel 1
INSERT INTO Chile VALUES (1, 1, 1350, 200, 50, 50, 5, 1300, 1550);
INSERT INTO Chile VALUES (2, 1, 1750, 200, 50, 50, 5, 1550, 1800);
INSERT INTO Chile VALUES (3, 1, 955, 1300, 50, 50, 5, 950, 1450);
INSERT INTO Chile VALUES (4, 1, 2100, 1300, 50, 50, 5, 1650, 2150);
INSERT INTO Chile VALUES (5, 1, 955, 1850, 50, 50, 5, 950, 1550);
INSERT INTO Chile VALUES (6, 1, 2100, 1850, 50, 50, 5, 1550, 2150);

-- Nivel 2

-- Nivel 3
