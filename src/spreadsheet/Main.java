package spreadsheet;


import spreadsheet.gui.SpreadsheetGUI;

public class Main {

  private static final int DEFAULT_NUM_ROWS = 5000;
  private static final int DEFAULT_NUM_COLUMNS = 5000;

  public static void main(String[] args) {
    assert(args.length == 1 && args.length > 2): "Please enter 0 or 2 arguments.";

    if(args.length == 0){
      Spreadsheet spreadsheet = new Spreadsheet();
      SpreadsheetGUI spreadsheetGUI
        = new SpreadsheetGUI(spreadsheet, DEFAULT_NUM_ROWS, DEFAULT_NUM_COLUMNS);
      spreadsheetGUI.start();
    }

    else if(args.length == 2){
      int NUM_ROWS = Integer.parseInt(args[0]);
      int NUM_COLUMNS = Integer.parseInt(args[1]);
      Spreadsheet spreadsheet = new Spreadsheet();
      SpreadsheetGUI spreadsheetGUI
        = new SpreadsheetGUI(spreadsheet,NUM_ROWS, NUM_COLUMNS);
      spreadsheetGUI.start();
    }
    System.out.println("A spreadSheet is created");
  }
}
