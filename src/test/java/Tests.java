import alignment.Alignment;
import org.junit.Assert;
import org.junit.Test;

public class Tests {

    @Test
    public void test1(){
        String seq1 = "ACATCGC";
        String seq2 = "CTGTACG";
        int existence = 11;
        int extention = 1;

        String expectedResult = "ACAT-CGCCTGTACG-";

        Alignment align = new Alignment(seq1, seq2, existence, extention);
        align.align();

        String actualResult = align.getResultA() + align.getResultB();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test2(){
        String seq1 = "ACATCGC";
        String seq2 = "CTGTACG";
        int existence = 7;
        int extention = 7;

        String expectedResult = "ACA-T-CGC-CTGTACG-";

        Alignment align = new Alignment(seq1, seq2, existence, extention);
        align.align();

        String actualResult = align.getResultA() + align.getResultB();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test3(){
        String seq1 = "CTCTCTCTCT";
        String seq2 = "AGAGAGAGAG";
        int existence = 11;
        int extention = 1;

        String expectedResult = "CTCTCTCTCTAGAGAGAGAG";

        Alignment align = new Alignment(seq1, seq2, existence, extention);
        align.align();

        String actualResult = align.getResultA() + align.getResultB();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test4(){
        String seq1 = "TAGCAGTCGATGCTCGGATCGTAGCGCGTAGCGCGATATAGACAGTCGAGTCGACGT";
        String seq2 = "TAGCAGTCGATGCTCGGATCAGCTGTCATGGCGTAGCGCGATATAGACAGTCGAGTCGACGT";
        int existence = 11;
        int extention = 1;

        String expectedResult = "TAGCAGTCGATGCTCGGATCGTAGC-------GCGTAGCGCGATATAGACAGTCGAGTCGACGTTAGCAGTCG" +
                "ATGCTCGGATC--AGCTGTCATGGCGTAGCGCGATATAGACAGTCGAGTCGACGT";

        Alignment align = new Alignment(seq1, seq2, existence, extention);
        align.align();

        String actualResult = align.getResultA() + align.getResultB();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test5(){
        String seq1 = "TAGCAGTCGATGCTCGGATCGTAGCGCGTAGCGCGATATAGACAGTCGAGTCGACGT";
        String seq2 = "TAGCAGTCGATGCTCGGATCAGCTGTCATGGCGTAGCGCGATATAGACAGTCGAGTCGACGT";
        int existence = 1;
        int extention = 1;

        String expectedResult = "TAGCAGTCGATGCTCGGAT---C-GT-A-GCGCGTAGCGCGATATAGACAGTCGAGTCGAC" +
                "GTTAGCAGTCGATGCTCGGATCAGCTGTCATG-GCGTAGCGCGATATAGACAGTCGAGTCGACGT";

        Alignment align = new Alignment(seq1, seq2, existence, extention);
        align.align();

        String actualResult = align.getResultA() + align.getResultB();

        Assert.assertEquals(expectedResult, actualResult);
    }
}
