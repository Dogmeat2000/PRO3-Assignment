package Client.Station2_Dissection.view;

import Client.Station2_Dissection.viewModel.Station2ViewModel;

public class Station2View
{
  private final Station2ViewModel viewModel;

  public Station2View(Station2ViewModel viewModel){
    this.viewModel = viewModel;
  }

  public void run() {
    System.out.println("\nSTATION 2: Animal Dissection (Command Line Interface)\nThis CLI is for debugging purposes!");

    while(true) {
      String input = null;

      while (!viewModel.validateCommand(input)) {
        System.out.println("\nAvailable 'AnimalPart' commands: 'Add', 'Remove', 'Update', 'ViewOne' & 'ViewAll'");
        System.out.print(": ");
        input = viewModel.getUserInput();
        if(viewModel.validateCommand(input))
          break;
        else
          System.out.println("\nPlease enter a valid command");
      }

      viewModel.performCommand(input);
    }
  }
}
