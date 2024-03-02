CREATE TABLE Rames (
    id int PRIMARY KEY,
    Name varchar(256)
);
CREATE TABLE Computers (
    id int PRIMARY KEY,
    ipAddress varchar(256)
)
CREATE TABLE RameComputerAssociation (
    idRame int,
    idComputer int,
    constraint fk_rame FOREIGN KEY (idRame) REFERENCES Rames(id),
    constraint fk_computer FOREIGN KEY (idComputer) REFERENCES Computers(id)
)
CREATE TABLE breakdownInfos (
    id int,
    code varchar(256),
    designation varchar(256)
)
CREATE TABLE breakdownEventAssociation (
    id int PRIMARY KEY,
    idRame int,
    idCode int,

    ccu varchar(256),
    device varchar(256),
    date date,
    velocity float,
    tension int,
    constraint fk_breakdown_event_rame FOREIGN KEY (idRame) references Rames(id),
    constraint tk_breakdown_event_breakdown FOREIGN KEY (idCode) references breakdownInfos(id)
)
