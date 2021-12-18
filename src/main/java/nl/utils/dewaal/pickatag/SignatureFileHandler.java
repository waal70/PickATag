package nl.utils.dewaal.pickatag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignatureFileHandler {
private static final Logger log = LogManager.getLogger(SignatureFileHandler.class);
private static String _fileLocationTemplateSignature = "";
private static String _fileLocationWriteLocation = "";


    public SignatureFileHandler() {
    this.init();
    }

    private void init(){
        log.debug("init routine called");
    }
    public void setFileLocationTemplateSignature(String fileLocationTemplateSignature) {
        SignatureFileHandler._fileLocationTemplateSignature = fileLocationTemplateSignature;
    }

    public String getFileLocationTemplateSignature() {
        return _fileLocationTemplateSignature;
    }

    public void setFileLocationWriteLocation(String fileLocationWriteLocation) {
        SignatureFileHandler._fileLocationWriteLocation = fileLocationWriteLocation;
    }

    public String getFileLocationWriteLocation() {
        return _fileLocationWriteLocation;
    }
}
