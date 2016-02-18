package spreadsheet;

import spreadsheet.api.CellLocation;
import spreadsheet.api.SpreadsheetInterface;
import spreadsheet.api.value.StringValue;
import spreadsheet.api.value.Value;

import java.util.*;

public class Spreadsheet implements SpreadsheetInterface {

  private Map<CellLocation, Cell>locationCell;
  private Set<Cell> recomputedCell;

  public Spreadsheet(){
    locationCell = new HashMap<>();
    this.recomputedCell = new HashSet<>();
  }

  public void setExpression(CellLocation location, String expression){
    if(locationCell.containsKey(location)){
      Cell cell = locationCell.get(location);
      cell.setCellExpression(expression);
    } else {
      Cell cell = new Cell(location, this);
      cell.setCellExpression(expression);
      locationCell.put(location, cell);
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
    Iterator iterator = recomputedCell.iterator();
    while(iterator.hasNext()){
      Cell cell = (Cell) iterator.next();
      StringValue strexpr = new StringValue(cell.getCellExpression());
      cell.setCellValue(strexpr);

      iterator.remove();
    }

  }

  public boolean needToRecompute(Cell cell){
    return recomputedCell.contains(cell);
  }

  public void addToRecompute(Cell cell){
    recomputedCell.add(cell);
  }

  public Cell getCell(CellLocation location){
    Cell cell = new Cell(location, this);
    return cell;
  }
}
