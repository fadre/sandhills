package ch.fadre.sandhills.output;

import java.io.File;


public class ResultUtil {

    public static File getResultDir(){
        File resultsDir = new File("results");
        if(!resultsDir.exists()){
            resultsDir.mkdir();
        }
        return resultsDir;
    }

}
