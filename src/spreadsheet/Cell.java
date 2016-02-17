package spreadsheet;


import spreadsheet.api.CellLocation;
import spreadsheet.api.observer.Observer;
import spreadsheet.api.value.InvalidValue;
import spreadsheet.api.value.StringValue;
import spreadsheet.api.value.Value;

import java.util.HashSet;
import java.util.Set;

public class Cell implements Observer<Cell> {

  private CellLocation cellLocation;
  private Spreadsheet spreadSheet;
  private Value value;
  private String expression;

  private Set<Observer<Cell>> observers;
  private Set<Cell> referenceExp;

  public Cell(CellLocation cellLocation, Spreadsheet spreadSheet){
    this.cellLocation = cellLocation;
    this.spreadSheet = spreadSheet;
    this.value = null;
    this.expression = "";
    this.observers = new HashSet<>();
    this.referenceExp = new HashSet<>();
  }

  public String getCellExpression() {
    return expression;
  }

  public Value getCellValue(){
    return value;
  }

  public void setCellExpression(String newExpression){
    this.expression = newExpression;
    StringValue stringValue = new StringValue(expression);
    this.value = stringValue;
  }

  //A cell needed to be updated when the expression is changed
  //Use the helper method -- removeObserver
  public void update(Cell changed){


  }

  private void removeObserver(Observer<Cell> observer){
    observers.remove(observer);
  }

  public void changeCellExpr(String expr){
    for(Cell reference : referenceExp){

    }
    observers.clear();

    /*InvalidValue invalidValue = new InvalidValue(expr);
      setCellExpression(invalidValue);*/


  }
}
