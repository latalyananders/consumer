package alignment;

import java.util.HashMap;
import java.util.Map;

public class Alignment {
    private int existence;
    private int extention;

    int[][] matrix = {
            {10, -9, -3, -4}, //A
            {-9,  7, -5, -3}, //G
            {-3, -5,  9,  0}, //C
            {-4, -3,  0,  8}, //T
    };

    Map key = new HashMap(){{
        put('A', 0);
        put('G', 1);
        put('C', 2);
        put('T', 3);
    }};

    int[][] f;

    private StringBuilder sequenceA;
    private StringBuilder sequenceB;

    private String resultA;
    private String resultB;

    public Alignment(String seqA, String seqB, int existence, int extention){
        sequenceA = new StringBuilder(seqA);
        sequenceB = new StringBuilder(seqB);
        this.existence = existence;
        this.extention = extention;
    }

    private boolean checkTop(int i, int j){
        for(int k = i - 1; k >= 0; k--){
            if(k - 1 == -1){
                break;
            }
            int elem1 = f[k][j];
            int elem2 = f[k-1][j];
            if(elem1 + existence == elem2){
                return true;
            }
            else {
                if (elem1 + extention == elem2) {
                    continue;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkLeft(int i, int j){
        for(int k = j - 1; k >= 0; k--){
            if(k - 1 == -1){
                break;
            }
            int elem1 = f[i][k];
            int elem2 = f[i][k-1];
            if(elem1 + existence == elem2){
                return true;
            }
            else {
                if (elem1 + extention == elem2) {
                    continue;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkLeftBack(int i, int j){
        for(int k = i; k >= 0; k--){
            if(k - 1 == -1){
                break;
            }
            int elem1 = f[j][k];
            int elem2 = f[j][k - 1];
            if(elem1 + existence == elem2){
                return true;
            }
            else {
                if (elem1 + extention == elem2) {
                    continue;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkTopBack(int i, int j){
        for(int k = j; k >= 0; k--){
            if(k - 1 == -1){
                break;
            }
            int elem1 = f[k][i];
            int elem2 = f[k - 1][i];
            if(elem1 + existence == elem2){
                return true;
            }
            else {
                if (elem1 + extention == elem2) {
                    continue;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    public void align(){
        f = new int[sequenceB.length() + 1][sequenceA.length() + 1];

        f[0][0] = 0;
        f[1][0] = -1 * existence;
        for(int i = 2; i < f.length; ++i){
            f[i][0] = f[i - 1][0] - extention;
        }
        f[0][1] = -1 * existence;
        for(int i = 2; i < f[0].length; ++i){
            f[0][i] = f[0][i - 1] - extention;
        }

        for(int i = 1; i < f.length; ++i){
            for(int j = 1; j < f[0].length; ++j){
                int a = (int)key.get(sequenceA.charAt(j - 1));
                int b = (int)key.get(sequenceB.charAt(i - 1));
                int diag = f[i - 1][j - 1] + matrix[a][b];

                int top;
                if(!checkTop(i,j)){
                    top = f[i - 1][j] - existence;
                }
                else {
                    top = f[i - 1][j] - extention;
                }

                int left;
                if(!checkLeft(i,j)){
                    left = f[i][j - 1] - existence;
                }
                else {
                    left = f[i][j - 1] - extention;
                }

                int max = diag;
                if(top > max){
                    max = top;
                }
                if(left > max){
                    max = left;
                }
                f[i][j] = max;
            }
        }

        resultA = "";
        resultB = "";

        int i = f[0].length - 1;
        int j = f.length - 1;
        while (i != 0 || j != 0){
            int value = f[j][i];

            //Если это последний столбец
            if(i == 0){
                resultB = sequenceB.charAt(j - 1) + resultB;
                resultA = "-" + resultA;
                j--;
                continue;
            }

            //если это последняя строка
            if(j == 0){
                resultA = sequenceA.charAt(i - 1) + resultA;
                resultB = "-" + resultB;
                i--;
                continue;
            }

            int a = (int)key.get(sequenceA.charAt(i - 1));
            int b = (int)key.get(sequenceB.charAt(j - 1));
            int r = f[j][i] - matrix[a][b];

            //идём по диагонали
            if(r == f[j - 1][i - 1]){
                resultA = sequenceA.charAt(i - 1)+ resultA;
                resultB = sequenceB.charAt(j - 1) + resultB;
                i--;
                j--;
                continue;
            }

            if(checkTopBack(i, j)){
                resultA = "-" + resultA;
                resultB = sequenceB.charAt(j - 1) + resultB;
                j--;
                continue;
            }


            if(checkLeftBack(i, j)){
                resultA = sequenceA.charAt(i - 1) + resultA;
                resultB = "-" + resultB;
                i--;
                continue;
            }

        }
    }

    public String getResult(){
        return resultA + "\n" + resultB;
    }

    public String getResultA(){
        return resultA;
    }

    public String getResultB(){
        return resultB;
    }

    public String getLength(){
        return resultA.length() + "";
    }

    public String getIdentities(){
        int identities = 0;
        for(int k = 0; k < resultA.length(); ++k){
            if (resultA.charAt(k) == resultB.charAt(k)){
                identities++;
            }
        }
        return identities + "/" + resultA.length() + " (" + (double)identities / (double)resultA.length() + "%)";
    }

    public String getGaps(){
        int gaps = 0;
        for (int k = 0; k < resultA.length(); ++k){
            if(resultA.charAt(k) == '-' || resultB.charAt(k) == '-'){
                gaps++;
            }
        }
        return gaps + "/" + resultA.length() + " (" + (double)gaps / (double)resultA.length() + "%)";
    }

    public String getExistence(){
        return existence + "";
    }

    public String getExtention(){
        return extention + "";
    }
}
