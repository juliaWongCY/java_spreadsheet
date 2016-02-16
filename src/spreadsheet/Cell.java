package spreadsheet;


import spreadsheet.api.CellLocation;
import spreadsheet.api.value.Value;

public class Cell {

  private CellLocation cellLocation;
  private Spreadsheet spreadSheet;
  private Value value;
  private String expression;

  public Cell(CellLocation cellLocation, Spreadsheet spreadSheet){
    this.cellLocation = cellLocation;
    this.spreadSheet = spreadSheet;
    this.value = null;
    this.expression = "";
  }

  public String getCellExpression() {
    return expression;
  }

  public Value getCellValue(){
    return value;
  }

  public void setCellExpression(String newExpression){
    this.expression = newExpression;
  }
}
