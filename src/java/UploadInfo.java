
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ファイルアップ情報.
 * 
 * @author tomo
 */
@XmlRootElement
public class UploadInfo {
    String destination;
    String fileName;
    String fileType;
    String file;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
}
