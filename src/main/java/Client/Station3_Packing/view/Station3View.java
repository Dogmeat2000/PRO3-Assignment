package Client.Station3_Packing.view;

import Client.Station3_Packing.viewModel.Station3ViewModel;

public class Station3View
{
  private final Station3ViewModel viewModel;

  public Station3View(Station3ViewModel viewModel){
    this.viewModel = viewModel;
  }

  public void run() {
    System.out.println("\nSTATION 3: Product Packaging (Command Line Interface)\nThis CLI is for debugging purposes!");
    while(true) {
      String input = null;

      while (!viewModel.validateCommand(input)) {
        System.out.println("\nAvailable 'Product' commands: 'Add', 'Remove', 'Update', 'ViewOne' & 'ViewAll'");
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
