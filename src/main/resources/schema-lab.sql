CREATE TABLE IF NOT EXISTS CARS (
    id IDENTITY NOT NULL,
    carid varchar(36),
    weight INT,
    height INT,
    priceperminute NUMERIC(4,2),
    starttime DATETIME
);

CREATE TABLE IF NOT EXISTS PARKINGLOT (
    id IDENTITY NOT NULL,
    weightlim INT,
    heightlim INT,
    pricemultiplier NUMERIC(4,2),
    numberofspaces INT

);

CREATE TABLE IF NOT EXISTS PARKINGSPACES (
    spaceid INT NOT NULL,
    floorid INT NOT NULL,
    carid varchar(36) NOT NULL,
    constraint composite_pk primary key (spaceid, floorid)
);