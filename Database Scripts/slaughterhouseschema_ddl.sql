--DROP SCHEMA pro3_slaughterhouse CASCADE;

CREATE SCHEMA IF NOT EXISTS pro3_slaughterhouse;

SET SCHEMA 'pro3_slaughterhouse';

CREATE TABLE Animal (
    animal_id BIGSERIAL UNIQUE,
    weight_kilogram DECIMAL(10, 5),
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
    animal_id BIGINT,
    type_id BIGINT,
    tray_id BIGINT,
    product_id BIGINT,
    PRIMARY KEY (part_id, animal_id, type_id, tray_id),
    FOREIGN KEY (animal_id) REFERENCES Animal(animal_id),
    FOREIGN KEY (type_id) REFERENCES PartType(id),
    FOREIGN KEY (tray_id) REFERENCES Tray(tray_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id));

CREATE TABLE TrayToProductTransfer (
    tray_id BIGSERIAL UNIQUE,
    product_id BIGINT,
    PRIMARY KEY (tray_id, product_id),
    FOREIGN KEY (tray_id) REFERENCES Tray (tray_id),
    FOREIGN KEY (product_id) REFERENCES Product (product_id));