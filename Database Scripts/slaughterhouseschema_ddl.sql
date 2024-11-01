-- DROP SCHEMA pro3_slaughterhouse CASCADE;

CREATE SCHEMA IF NOT EXISTS pro3_slaughterhouse;

SET SCHEMA 'pro3_slaughterhouse';

CREATE TABLE Animal (
    animal_id BIGSERIAL UNIQUE,
    weight_kilogram DECIMAL(10, 5),
    origin VARCHAR(300),
    arrival_date TIMESTAMP,
    PRIMARY KEY (animal_id));

CREATE TABLE PartType (
    id BIGSERIAL UNIQUE,
    "desc" VARCHAR(200),
    PRIMARY KEY (id));

CREATE TABLE Tray (
    tray_id BIGSERIAL UNIQUE,
    weight_kilogram DECIMAL(10,5),
    maxWeight_kilogram DECIMAL(10,5),
    PRIMARY KEY (tray_id));

CREATE TABLE Product (
    product_id BIGSERIAL UNIQUE,
    PRIMARY KEY (product_id));

CREATE TABLE AnimalPart (
    part_id BIGSERIAL UNIQUE,
    weight_kilogram DECIMAL(10,5),
    animal_id BIGINT NOT NULL,
    type_id BIGINT NOT NULL,
    tray_id BIGINT NOT NULL,
    product_id BIGINT,
    PRIMARY KEY (part_id),
    FOREIGN KEY (animal_id) REFERENCES Animal(animal_id),
    FOREIGN KEY (type_id) REFERENCES PartType(id),
    FOREIGN KEY (tray_id) REFERENCES Tray(tray_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id));

CREATE TABLE TrayToProductTransfer (
    transfer_id BIGSERIAL UNIQUE,
    tray_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (transfer_id),
    FOREIGN KEY (tray_id) REFERENCES Tray (tray_id),
    FOREIGN KEY (product_id) REFERENCES Product (product_id));

-- Load some initial dummy data:
insert into parttype ("desc") values ('Pork Shoulder (Boston Butt)');
insert into parttype ("desc") values ('Pork Loin');
insert into parttype ("desc") values ('Pork Belly');
insert into parttype ("desc") values ('Pork Tenderloin');
insert into parttype ("desc") values ('Pork Ribs');
insert into parttype ("desc") values ('Pork Ham');
insert into parttype ("desc") values ('Pork Feet');
insert into parttype ("desc") values ('Pork Tail');
insert into parttype ("desc") values ('Pork Liver');
insert into parttype ("desc") values ('Pork Heart');
insert into parttype ("desc") values ('Pork Bacon');

insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);
insert into tray (weight_kilogram, maxweight_kilogram) values (0, 25);