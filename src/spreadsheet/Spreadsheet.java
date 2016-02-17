package spreadsheet;

import spreadsheet.api.CellLocation;
import spreadsheet.api.SpreadsheetInterface;
import spreadsheet.api.value.Value;

import java.util.HashMap;
import java.util.Map;

public class Spreadsheet implements SpreadsheetInterface {

  private Map<CellLocation, Cell>locationCell;

  public Spreadsheet(){
    locationCell = new HashMap<>();
  }

  public void setExpression(CellLocation location, String expression){
    if(locationCell.containsKey(location)){
      Cell cell = locationCell.get(location);
      cell.setCellExpression(expression);
    } else {  //TODO: Check the spreadSheet bit!!!
      Spreadsheet spreadSheet = new Spreadsheet();
      Cell cell = new Cell(location, spreadSheet);
      cell.setCellExpression(expression);
    }

  }

  public String getExpression(CellLocation location){
    if(locationCell.containsKey(location)){
      Cell cell = locationCell.get(location);
      return cell.getCellExpression();
    }
    return "";
  }

  public Value getValue(CellLocation location){
    if(locationCell.containsKey(location)) {
      Cell cell = locationCell.get(location);
      return cell.getCellValue();
    }
    return null;
  }

  public void recompute(){

  }


}
