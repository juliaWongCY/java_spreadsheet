package spreadsheet;

import spreadsheet.api.CellLocation;
import spreadsheet.api.SpreadsheetInterface;
import spreadsheet.api.value.LoopValue;
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

  @Override
  public void recompute(){
    Iterator iterator = recomputedCell.iterator();
    while(iterator.hasNext()){
      Cell cell = (Cell) iterator.next();
      StringValue stringValue = new StringValue(cell.getCellExpression());
      cell.setCellValue(stringValue);

      iterator.remove();

      recomputeCell(cell);
    }


  }

  private void recomputeCell(Cell c){
    StringValue stringValue = new StringValue(c.getCellExpression());
    c.setCellValue(stringValue);
    recomputedCell.remove(c);

    LinkedHashSet<Cell> cellsSeen = new LinkedHashSet<>();
    checkLoops(c, cellsSeen);

  }

  private void checkLoops(Cell c, LinkedHashSet<Cell> cellsSeen){
    if(cellsSeen.contains(c)) {
      markAsLoop(c, cellsSeen);
    } else {
      cellsSeen.add(c);

      for(Cell cell : c.referenceExp){
         checkLoops(cell, cellsSeen);
      }
      cellsSeen.remove(c);
    }

  }

  private void markAsLoop(Cell startCell, LinkedHashSet<Cell> cells){
    //Iterator iterator = cells.iterator();

    recompute();
    LoopValue loopValue = LoopValue.INSTANCE;
    for(Cell cellRef : cells){
      cellRef.setCellValue(loopValue);

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



/*Iterator iterator = cellsSeen.iterator();
      if (iterator.hasNext()) {

      }*/