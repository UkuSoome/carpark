CREATE TABLE IF NOT EXISTS CARS (
    id IDENTITY NOT NULL,
    uuid varchar(36),
    weight INT,
    height INT,
    priceperminute NUMERIC(4,2)
);

CREATE TABLE IF NOT EXISTS PARKINGLOT (
    id IDENTITY NOT NULL,
    weightlim INT,
    heightlim INT,
    pricemultiplier NUMERIC(4,2)
);

CREATE TABLE IF NOT EXISTS PARKINGSPACES (
    id IDENTITY NOT NULL,
    floorid INT,
    carid varchar(36)
);