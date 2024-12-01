# PRO3-Assignment: Main Course Assignment

# Table of Contents:

 - [Case](#Case)
 - [How To Run](#How-to-run)
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
## Preparation:
1. Import/Clone repository
2. Clean and install with maven, ensuring to generate sources and documentation
3. Install RabbitMQ on the local machine.
4. Update the path defined inside the 'RunAllServers.java' Spring Boot Service launcher. This needs to point to the shortcut that launches your RabbitMQ service.
![ScreenShot](Development%20Documents%20(UML%2C%20etc)/RunAllServers_UpdatePath.jpg)
6. Run 'RunAllServers' {src -> main -> java -> 'RunAllServers'} to launch both the Database (including REST/WEB endpoints and gRPC controllers) and the RabbitMQ AMQP server.
   
## Using an external HTTP messaging application (Postman, HTTPie, etc.):
1. Use Postman, HTTPie, etc. to send HTTP messages to the Web Server. See pictures below for examples of messages using HTTPie:


### Post (Create) a new Animal:

![ScreenShot](Development%20Documents%20(UML%2C%20etc)/http_postNewAnimalQuery.jpg)


### Get (Read) a single Animal:

![ScreenShot](Development%20Documents%20(UML%2C%20etc)/http_getSingleAnimalById.jpg)


### Get (Read) all Animals:

![ScreenShot](Development%20Documents%20(UML%2C%20etc)/http_getAllAnimalsQuery.jpg)


### Get (Read) all Animals, from a specific Farm (origin):

![ScreenShot](Development%20Documents%20(UML%2C%20etc)/http_getAllAnimalsFromSpecifiedFarmQuery.jpg)


### Get (Read) all Animals, registered on a specific date:

![ScreenShot](Development%20Documents%20(UML%2C%20etc)/http_getAllAnimalsFromSpecifiedDateQuery.jpg)


### Get (Read) all Animals, registered on a specific date and from a specific farm:

![ScreenShot](Development%20Documents%20(UML%2C%20etc)/http_getAllAnimalsFromSpecifiedFarmAtSpecifiedDateQuery.jpg)


### Put (Update) an Animal:

![ScreenShot](Development%20Documents%20(UML%2C%20etc)/http_putUpdatedAnimalQuery.jpg)


### Delete an Animal:

![ScreenShot](Development%20Documents%20(UML%2C%20etc)/http_deleteAnimalQuery.jpg)


## Using built-in CLI operating through gRPC connection:
1. Run 'Station1CLI' {src -> main -> java -> Client -> Station1_AnimalRegistration -> RunStation1CLI} to launch the Animal Management & Registration services.
2. Run 'Station2CLI' {src -> main -> java -> Client -> Station2_Dissection -> RunStation2CLI} to launch the AnimalPart Management & Registration services.
3. Run 'Station3CLI' {src -> main -> java -> Client -> Station3_Packing -> RunStation3CLI} to launch the Product Management & Registration services.
4. Run 'RecallMachineCLI' {src -> main -> java -> Client -> RecallMachine -> RunRecallMachineCLI} to launch a simulation of the machine that can gather provide required information for Product recall (i.e. Which animals did a given product have something to do with?).

![CLI ScreenShot1](Development%20Documents%20(UML%2C%20etc)/Station4_CLI.png)

![CLI Screenshot2](Development%20Documents%20(UML%2C%20etc)/Station1_CLI.png)


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
