package spreadsheet;

import spreadsheet.api.CellLocation;
import spreadsheet.api.ExpressionUtils;
import spreadsheet.api.SpreadsheetInterface;
import spreadsheet.api.value.*;

import java.util.*;

public class Spreadsheet implements SpreadsheetInterface {

  private Map<CellLocation, Cell>locationCell;
  //private Deque<Cell> recomputedCell;
  private Set<Cell> recomputedCell;


  public Spreadsheet(){
    locationCell = new HashMap<>();
    //recomputedCell = new ArrayDeque<>();
    recomputedCell = new HashSet<>();
  }

  @Override
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

  @Override
  public String getExpression(CellLocation location){
    if(locationCell.containsKey(location)){
      Cell cell = locationCell.get(location);
      return cell.getCellExpression();
    }
    return "";
  }

  @Override
  public Value getValue(CellLocation location){
    if(locationCell.containsKey(location)) {
      Cell cell = locationCell.get(location);
      return cell.getCellValue();
    }
    return null;
  }

  @Override
  public void recompute(){

    while(!recomputedCell.isEmpty()){
      //Cell cell = recomputedCell.peekFirst();
      Iterator iterator = recomputedCell.iterator();
      Cell cell = (Cell) iterator.next();
      recomputeCell(cell);
      //recomputedCell.removeFirst();

      recomputedCell.remove(cell);
    }
  }

  private void recomputeCell(Cell c){
    LinkedHashSet<Cell> cellsSeen = new LinkedHashSet<>();
    Deque<Cell> queue = new ArrayDeque<>();

    if(needToRecompute(c)) {
      StringValue stringValue = new StringValue(c.getCellExpression());
      c.setCellValue(stringValue);
    }
    checkLoops(c, cellsSeen);

    //boolean dependRecompute = false;
    while(!queue.isEmpty()){
      Cell currentCell = queue.getFirst();

      for(Cell reCompute : recomputedCell){
        if(dependentRecompute(currentCell)){
          queue.addLast(currentCell);
        } else {
          calculateCellValue(currentCell);
          recomputedCell.remove(currentCell);
        }
      }




    }
  }

  private void checkLoops(Cell c, LinkedHashSet<Cell> cellsSeen){
    //System.out.println("check loop is called");

    if(cellsSeen.contains(c)) {
      markAsLoop(c, cellsSeen);
    } else {
      cellsSeen.add(c);

      for(Cell cell : c.referenceExp) {
        //System.out.println("Current Reference: " + cell.getCellLoc());
        checkLoops(cell, cellsSeen);
      }
    }
  }

  private void markAsLoop(Cell startCell, LinkedHashSet<Cell> cells){
    //System.out.println("markAsLoop is called");
    LoopValue loopValue = LoopValue.INSTANCE;

    boolean startSeen = false;

    for(Cell cell: cells){
      if(cell.equals(startCell) || startSeen){
        cell.setCellValue(loopValue);
        startSeen = true;
      } else {
        InvalidValue invalidValue
          = new InvalidValue(cell.getCellExpression());
        cell.setCellValue(invalidValue);
      }
      recomputedCell.remove(cell);
    }
  }

  private void calculateCellValue(Cell cell){
    Map<CellLocation, Double> dependents = new HashMap<>();
    Value value
      = ExpressionUtils.computeValue(cell.getCellExpression(), dependents);
    new ValueVisitor() {

      @Override
      public void visitDouble(double value){

      }

      @Override
      public void visitLoop(){

      }

      @Override
      public void visitString(String expression){

      }

      @Override
      public void visitInvalid(String expression){

      }
    };
    cell.setCellValue(value);
    recomputedCell.remove(cell);
  }

  public boolean needToRecompute(Cell cell){
    return recomputedCell.contains(cell);
  }

  public void addToRecompute(Cell cell){
    recomputedCell.add(cell);
  }

  private boolean dependentRecompute(Cell cell){
    LoopValue loopValue = LoopValue.INSTANCE;
    return needToRecompute(cell) && cell.getCellValue() == loopValue;
  }

 /* private boolean dependRecompute(){
    LinkedHashSet<Cell> cellsSeen = new LinkedHashSet<>();
    for(Cell cell : cellsSeen){
      if(needToRecompute(cell)){
        return true;
      }
    }
    return false;
  }
  */

  public Cell getCell(CellLocation location){
    if(locationCell.containsKey(location)) {
      return locationCell.get(location);
    } else {
      Cell cell = new Cell(location, this);
      locationCell.put(location, cell);
      return cell;
    }
  }
}



/*Iterator iterator = cellsSeen.iterator();
      if (iterator.hasNext()) {

      }*/