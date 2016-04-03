package ch.fadre.sandhills.output;

import java.io.File;


class ResultUtil {

    static File getResultDir(){
        File resultsDir = new File("results");
        if(!resultsDir.exists()){
            resultsDir.mkdir();
        }
        return resultsDir;
    }

}
