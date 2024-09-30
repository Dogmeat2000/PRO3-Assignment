package client;

import client.interfaces.AnimalRegistrationSystem;
import client.service.Client;
import client.service.Station1_AnimalRegistration;
import shared.model.entities.Animal;

import java.math.BigDecimal;
import java.util.Scanner;

public class Station1_CLI
{
  private Station1_AnimalRegistration client;

  public static void main(String[] args) throws InterruptedException {

    System.out.println("\nSTATION 1: Animal Registration (Command Line Interface)\nThis CLI is for debugging purposes!");

    AnimalRegistrationSystem station1 = new Station1_AnimalRegistration("localhost", 9090);

    System.out.println("Command Line Interface to ADD Animals to Database!\nEach Animal must have a Weight assigned!");
    System.out.print("\nPlease enter the weight of the Animal you want to register: ");
    Scanner input = new Scanner(System.in);
    Animal animal = station1.registerNewAnimal(new BigDecimal(input.next()));

    System.out.println("Added [" + animal + "] to Database!");

  }
}
