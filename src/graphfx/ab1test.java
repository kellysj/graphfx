package graphfx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class ab1test {

    String filename = "11-G3.ab1";
    public ArrayList<Integer> L1;
    public ArrayList<Integer> L2;
    public ArrayList<Integer> read;//the "real data" of the ab1 will be determined by a comparison of zscores, this assumes that 0 is a delimiter.
    public ArrayList<Integer> qual;
    public int zscore1;
    public int zscore2;
    public int startTrim;
    public int endTrim;

    public static void main(String args[]) {
        System.out.println("TEST READ");
        ab1test run = new ab1test();
        run.readnprint(true);
        int i = 0;
        /*
         for(Integer n: run.qual){
         if(n!=0){
         System.out.println(i + ":" + n);
         }
         i++;
         }
         System.out.println("size of qual: " + run.qual.size());
         */
    }

    ab1test() {
        startTrim = 443630;// orig = 240
        endTrim = 447577;// orig = 122950,21154,360106
        zscore1 = 0;
        zscore2 = 0;
        initData();
    }

    /**
     * sets the variable endTrim to give a postion to end the read
     *
     * @param start position to start the read at
     */
    public void setStart(int start) {
        startTrim = start;
    }

    /**
     * sets the variable endTrim to give a postion to end the read
     *
     * @param end position to end the read at
     */

    public void setEnd(int end) {
        endTrim = end;
    }

    /**
     * A method to initialze or reset list contents
     *
     */
    public void initData() {
        L1 = new ArrayList();
        L2 = new ArrayList();
        read = new ArrayList();
        qual = new ArrayList();
        zscore1 = 0;
        zscore2 = 0;
    }

    /**
     * A method to read in a PART of an ab1 file and output the contents as
     * single int values
     *
     * @param printout set to true for a verbose read through
     */
    public void readnprint(boolean printout) {
        initData();
        try {
            BufferedReader fileread = new BufferedReader(new FileReader(
                    filename));          
            int n;
            int i = startTrim;
            fileread.skip(startTrim);
            while ((n = fileread.read()) != -1) {
                String out = "" + n;
                if (i % 2 == 0) {
                    out = out + "\t1";
                    L1.add(n);
                    if (n == 0) {
                        zscore1++;//compiling score for R/Q
                    }
                } else {
                    out = out + "\t2";
                    L2.add(n);
                    if (n == 0) {
                        zscore2++;//compiling score for R/Q
                    }
                }
                out = i + ": " + out;
                if (printout) {
                    System.out.println(out);
                }

                i++;
                if (i > endTrim) {
                    setRQ(printout);
                    fileread.close();

                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    /**
     * A method to determine if something is a read or a quality score
     *
     */
    private void setRQ(boolean printsout) {
        if (zscore1 > zscore2) {
            qual = L1;
            read = L2;
        } else {
            qual = L2;
            read = L1;
        }
        if (printsout) {
            Double ratio1 = (double) zscore1 / (double) L1.size();
            Double ratio2 = (double) zscore2 / (double) L2.size();
            String out = "ratio L1: " + ratio1 + "\nratio L2: " + ratio2;
            System.out.println(out);

        }
    }

}
