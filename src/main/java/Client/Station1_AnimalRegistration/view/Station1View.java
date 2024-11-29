package Client.Station1_AnimalRegistration.view;

import Client.Station1_AnimalRegistration.viewModel.Station1ViewModel;

public class Station1View
{
  private final Station1ViewModel viewModel;

  public Station1View(Station1ViewModel viewModel){
    this.viewModel = viewModel;
  }

  public void run() {
    System.out.println("\nSTATION 1: Animal Registration (Command Line Interface)\nThis CLI is for debugging purposes!");

    while (true) {
      String input = null;

      while (!viewModel.validateCommand(input)) {
        System.out.println("\nAvailable 'Animal' commands: 'Add', 'Remove', 'Update', 'ViewOne' & 'ViewAll'");
        System.out.print(": ");
        input = viewModel.getUserInput();
        if (viewModel.validateCommand(input))
          break;
        else
          System.out.println("\nPlease enter a valid command");
      }

      viewModel.performCommand(input);
    }
  }
}
