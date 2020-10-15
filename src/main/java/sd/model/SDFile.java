package sd.model;

import java.io.File;

public class SDFile extends File {

    public SDFile(String pathname) {
        super(pathname.replaceAll("\\.\\.", ""));
    }

}
