# PRO3-Assignment: Main Course Assignment

# Table of Contents:

 - [Case](#Case)
 - [Analysis](#Analysis)
   - [Domain model](#Domain-Model)
   - [Threat models](##Threat-model)
  - [Design](#Design)
    - [EER Diagram](#EER-Diagram)
    - [Relational Schema](#Relational-Schema)
    - [Global Relations Diagraml](#Global-Relations-Diagram)

# Case
Your task is to simulate the operation of a nearby slaughterhouse. In one end of the slaughterhouse living animals arrive and in the other end various products, consisting of one or more animal parts, depart. The slaughterhouse has three stations where smaller parts of the whole process take place.
1.	At the first station, the animals arrive, are weighed and registered.
3.	At the second station, the animals are cut into smaller parts. Each part is weighed and registered, including a reference to which animal it comes from.
The parts are put into trays with each tray containing only one type of parts. Each tray has a maximum weight capacity.
4. At the third station, products are packed for distribution. One product might be a package with a number of the same kind of parts intended for repackaging in supermarkets. Another kind of product might be “half an animal” where all the expected parts are included, but not necessarily coming from the same animal. All products are registered including references back to the trays the parts came from.

If it is later discovered, that there is some kind of trouble with a slaughtered animal, it should be possible to recall all products, which might contain parts of the animal. This function should be accessible outside the slaughterhouse.
It is important for the customer that the stations can work as independently as possible. In particular, Work shouldn’t stop at a station just because the network is down.

# How to run
1. Import/Clone repository
2. Clean and Install with Maven, ensuring to generate sources and documentation
3. Run GrpcServer.java to launch the Server.
4. Run Station1_CLI to launch the debugging CLI with basic commands accessable.



# Analysis

## Domain model

![Domain model](Development%20Documents%20(UML%2C%20etc)/domain-model.svg)

## Architecture
![Architecture model](Development%20Documents%20(UML%2C%20etc)/architecture-model.svg)

## Threat model
### EINOO: Potential Attacker Analysis

![EINOO model](Development%20Documents%20(UML%2C%20etc)/EINOO-Threat-Model.png)

### STRIDE: Security Design Considerations

![EINOO model](Development%20Documents%20(UML%2C%20etc)/STRIDE-Threat-Model.png)


# Design

## EER Diagram

![EER Diagram](Development%20Documents%20(UML%2C%20etc)/eer-diagram.svg)

## Relational Schema

![Relational Schema](Development%20Documents%20(UML%2C%20etc)/relational-schema.png)

## Global Relations Diagram

![Global Relations Diagram](Development%20Documents%20(UML%2C%20etc)/grd-diagram.svg)
