package ladder;

public class Ladder {

    /*좌표값으로 나타내기위해 이차원 배열
    캡슐화, 외부에서 접근 못하게 private로 선언
    매직 넘버 사용으로 불분명한 숫자 의미 확실하게*/
    private final Integer[][] ladder;
    private static final int EMPTY_COLUMN = 2;
    private static final int CREATED_COLUMN = 1;
    private static final int FLAGED_COLUMN = 0;
    /*인스턴스 생성
    좌표값에해당하는 배열의 값에 CREATED_COLUMN이 들어있으면 가로선 존재*/
    public Ladder(int numberOfRows, int numberOfColumns) {
        ladder = new Integer[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                ladder[i][j] = EMPTY_COLUMN;
            }
        }
    }
    public int getLadder(int row,int column){
        return ladder[row][column];
    }
    /* 선택 행 유효성 검사 */
    private boolean isValidRow(int row, int column){
        return row >0 && row <=ladder.length && column >0 &&column <=ladder[0].length;
    }
    /* 선택 열 유효성 검사 */
    private boolean isValidColumn(int column) {
        return column >0 && column <= ladder[0].length;
    }
    /* 현재 들어있는 값이 1인지를 확인*/
    private boolean canMoveRight(int row,int column){
        return column < ladder[0].length && ladder[row][column] == CREATED_COLUMN;
    }
    /* 현재 열 왼쪽에 들어있는 값이 1인지를 확인*/
    private boolean canMoveLeft(int row,int column){
        return column >0 && ladder[row][column-1] == CREATED_COLUMN;
    }
    private boolean canMove(int row,int column){
        return canMoveRight(row,column) || canMoveLeft(row,column);
    }
    /*이동한 가로선 비활성*/
    public void movedFlag(int row,int column){
        ladder[row][column]=FLAGED_COLUMN;
    }
    public boolean leftFlag(int row,int column) {
            return column >0 &&ladder[row][column-1]==FLAGED_COLUMN;
        }
    public boolean rightFlag(int row,int column){
        return  column < ladder[0].length && ladder[row][column]==FLAGED_COLUMN;
    }
    /*가로이동*/
    public int moveColumn(int row,int column){
        /* 오른쪽으로 갈 수 있는 선(1로 표시)이 있다면 오른쪽 열로 이동*/
        if (canMoveRight(row, column)) {
            movedFlag(row,column);
            column++;
        }
        /* 왼쪽으로 갈 수 있는 선(1로 표시)이 있다면 왼쪽 열로 이동*/
        if(canMoveLeft(row,column)) {
            movedFlag(row, column - 1);
            column--;
        }
        return column;
    }
    /*세로 이동 메서드*/
    public int moveRow(int row,int column){
        /* 오른쪽, 왼쪽 모두 이동할 수 없으면 세로로 이동*/
        if(!canMove(row,column)){
            if(leftFlag(row,column)){
                ladder[row][column-1]=CREATED_COLUMN;
            }
            if(rightFlag(row,column)){
                ladder[row][column]=CREATED_COLUMN;
            }
            row++;
        }
        return row;
    }

    /*사다리게임 진행 메서드, IllegalArgumentException을 사용하여 적절하지 못한 값을 매서드가 받았을때 강제로 예외발생
    들어온 값에만 예외를 처리했는데 current도 로직에 들어가서 문제될수있으므로 조건문으로 진입 막기*/
    public int run(int selectedColumn) {
        /* 범위 벗어나면 예외 발생*/
        if (!isValidColumn(selectedColumn)) {
            throw new IllegalArgumentException("Invalid selected column");
        }
        int currentColumn = selectedColumn - 1;
        int currentRow = 0; // 초기 행 설정
        while (currentRow < ladder.length) {
            currentColumn=moveColumn(currentRow,currentColumn);
            currentRow=moveRow(currentRow,currentColumn);
        }
        /* 리턴값은 1을 더한 값을 반환하여 실제 게임 상의 열을 반환*/
        return currentColumn + 1;
    }

    /*가로선 그리는 함수, 가로선은 오른쪽으로만 그려짐 (draw에서 왼쪽오른쪽 선택해서 그리게 하면 코드 구조가 복잡해진다고 생각)*/
    public void drawLine(int row, int column){
        /*범위 벗어나면 예외 발생*/
        if (!isValidRow(row,column)) {
            throw new IllegalArgumentException("Invalid row or position");
        }
        ladder[row - 1][column - 1] = CREATED_COLUMN;
    }
}
