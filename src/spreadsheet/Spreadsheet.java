package spreadsheet;

import spreadsheet.api.CellLocation;
import spreadsheet.api.SpreadsheetInterface;
import spreadsheet.api.value.Value;

public class Spreadsheet implements SpreadsheetInterface {

  public void setExpression(CellLocation location, String expression){

  }

  public String getExpression(CellLocation location){
    return ""; //TODO :Check
  }

  public Value getValue(CellLocation location){
    return null;//TODO: Check
  }

  public void recompute(){

  }


}
