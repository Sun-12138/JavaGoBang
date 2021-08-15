package pers.gobang.method;

public class AIChess {

    private final int[][] score;
    private final int[][] chess_Data;
    private final int chess_Size;
    public AIChess(int[][] chess_Data, int chess_Size) {
        this.chess_Data = chess_Data;
        this.chess_Size = chess_Size;
        score = new int[chess_Size][chess_Size];
    }
    public int[] searchLocation() {
        //每次都初始化下score评分数组
        for (int i = 0; i < chess_Size; i++) {
            for (int j = 0; j < chess_Size; j++) {
                score[i][j] = 0;
            }
        }

        //每次机器找寻落子位置，评分都重新算一遍（虽然算了很多多余的，因为上次落子时候算的大多都没变）
        //先定义一些变量
        int humanChessmanNum = 0;//五元组中的黑棋数量
        int machineChessmanNum = 0;//五元组中的白棋数量
        int tupleScoreTmp;//五元组得分临时变量

        int goalX = -1;//目标位置x坐标
        int goalY = -1;//目标位置y坐标
        int maxScore = -1;//最大分数

        int[] location = new int[2];

        //1.扫描横向的15个行
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                int k = j;
                while (k < j + 5) {

                    if (chess_Data[i][k] == -1) machineChessmanNum++;
                    else if (chess_Data[i][k] == 1) humanChessmanNum++;

                    k++;
                }
                tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                //为该五元组的每个位置添加分数
                for (k = j; k < j + 5; k++) {
                    score[i][k] += tupleScoreTmp;
                }
                //置零
                humanChessmanNum = 0;//五元组中的黑棋数量
                machineChessmanNum = 0;//五元组中的白棋数量
            }
        }

        //2.扫描纵向15行
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                int k = j;
                while (k < j + 5) {
                    if (chess_Data[k][i] == -1) machineChessmanNum++;
                    else if (chess_Data[k][i] == 1) humanChessmanNum++;

                    k++;
                }
                tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                //为该五元组的每个位置添加分数
                for (k = j; k < j + 5; k++) {
                    score[k][i] += tupleScoreTmp;
                }
                //置零
                humanChessmanNum = 0;//五元组中的黑棋数量
                machineChessmanNum = 0;//五元组中的白棋数量
            }
        }

        //3.扫描右上角到左下角上侧部分
        for (int i = 14; i >= 4; i--) {
            for (int k = i, j = 0; j < 15 && k >= 0; j++, k--) {
                int m = k;
                int n = j;
                while (m > k - 5 && k - 5 >= -1) {
                    if (chess_Data[m][n] == -1) machineChessmanNum++;
                    else if (chess_Data[m][n] == 1) humanChessmanNum++;

                    m--;
                    n++;
                }
                //注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
                if (m == k - 5) {
                    tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                    //为该五元组的每个位置添加分数
                    for (m = k, n = j; m > k - 5; m--, n++) {
                        score[m][n] += tupleScoreTmp;
                    }
                }

                //置零
                humanChessmanNum = 0;//五元组中的黑棋数量
                machineChessmanNum = 0;//五元组中的白棋数量

            }
        }

        //4.扫描右上角到左下角下侧部分
        for (int i = 1; i < 15; i++) {
            for (int k = i, j = 14; j >= 0 && k < 15; j--, k++) {
                int m = k;
                int n = j;
                while (m < k + 5 && k + 5 <= 15) {
                    if (chess_Data[n][m] == -1) machineChessmanNum++;
                    else if (chess_Data[n][m] == 1) humanChessmanNum++;

                    m++;
                    n--;
                }
                //注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
                if (m == k + 5) {
                    tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                    //为该五元组的每个位置添加分数
                    for (m = k, n = j; m < k + 5; m++, n--) {
                        score[n][m] += tupleScoreTmp;
                    }
                }
                //置零
                humanChessmanNum = 0;//五元组中的黑棋数量
                machineChessmanNum = 0;//五元组中的白棋数量

            }
        }

        //5.扫描左上角到右下角上侧部分
        for (int i = 0; i < 11; i++) {
            for (int k = i, j = 0; j < 15 && k < 15; j++, k++) {
                int m = k;
                int n = j;
                while (m < k + 5 && k + 5 <= 15) {
                    if (chess_Data[m][n] == -1) machineChessmanNum++;
                    else if (chess_Data[m][n] == 1) humanChessmanNum++;

                    m++;
                    n++;
                }
                //注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
                if (m == k + 5) {
                    tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                    //为该五元组的每个位置添加分数
                    for (m = k, n = j; m < k + 5; m++, n++) {
                        score[m][n] += tupleScoreTmp;
                    }
                }

                //置零
                humanChessmanNum = 0;//五元组中的黑棋数量
                machineChessmanNum = 0;//五元组中的白棋数量

            }
        }

        //6.扫描左上角到右下角下侧部分
        for (int i = 1; i < 11; i++) {
            for (int k = i, j = 0; j < 15 && k < 15; j++, k++) {
                int m = k;
                int n = j;
                while (m < k + 5 && k + 5 <= 15) {
                    if (chess_Data[n][m] == -1) machineChessmanNum++;
                    else if (chess_Data[n][m] == 1) humanChessmanNum++;

                    m++;
                    n++;
                }
                //注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
                if (m == k + 5) {
                    tupleScoreTmp = tupleScore(humanChessmanNum, machineChessmanNum);
                    //为该五元组的每个位置添加分数
                    for (m = k, n = j; m < k + 5; m++, n++) {
                        score[n][m] += tupleScoreTmp;
                    }
                }

                //置零
                humanChessmanNum = 0;//五元组中的黑棋数量
                machineChessmanNum = 0;//五元组中的白棋数量

            }
        }

        //从空位置中找到得分最大的位置
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (chess_Data[i][j] == 0 && score[i][j] > maxScore) {
                    goalX = i;
                    goalY = j;
                    maxScore = score[i][j];
                }
            }
        }

        if (goalX != -1) {
            location[0] = goalX;
            location[1] = goalY;
            return location;
        }

        location[0] = -1;
        location[1] = -1;
        //没找到坐标说明平局了，笔者不处理平局
        return location;
    }

    //各种五元组情况评分表
    public int tupleScore(int humanChessmanNum, int machineChessmanNum) {
        //1.既有人类落子，又有机器落子，判分为0
        if (humanChessmanNum > 0 && machineChessmanNum > 0) {
            return 0;
        }
        //2.全部为空，没有落子，判分为7
        if (humanChessmanNum == 0 && machineChessmanNum == 0) {
            return 7;
        }
        //3.机器落1子，判分为35
        if (machineChessmanNum == 1) {
            return 35;
        }
        //4.机器落2子，判分为800
        if (machineChessmanNum == 2) {
            return 800;
        }
        //5.机器落3子，判分为15000
        if (machineChessmanNum == 3) {
            return 15000;
        }
        //6.机器落4子，判分为800000
        if (machineChessmanNum == 4) {
            return 800000;
        }
        //7.人类落1子，判分为15
        if (humanChessmanNum == 1) {
            return 15;
        }
        //8.人类落2子，判分为400
        if (humanChessmanNum == 2) {
            return 400;
        }
        //9.人类落3子，判分为1800
        if (humanChessmanNum == 3) {
            return 1800;
        }
        //10.人类落4子，判分为100000
        if (humanChessmanNum == 4) {
            return 100000;
        }
        return -1;//若是其他结果肯定出错了。这行代码根本不可能执行
    }
}
