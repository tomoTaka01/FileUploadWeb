
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author tomo
 */
@javax.ws.rs.Path("/upload")
public class UploadResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String uploadFile(UploadInfo info) throws IOException{
        String returnMsg = "";
        String fileType = info.getFileType();
        Path path = Paths.get(info.getDestination() + File.separator 
                + info.getFileName());
        if (fileType.equals("text/plain")){
            // ファイルに保存
            saveFile(path, info.getFile());
            returnMsg = String.format("%sに保存しました", path.toAbsolutePath().toString());
        } else if(fileType.startsWith("image")) {
            // イメージをして保存
            saveImage(path, info.getFile());
            returnMsg = String.format("%sに保存しました", path.toAbsolutePath().toString());
        } else {
            // 保存エラー
            returnMsg = "保存に失敗しました。";
        }
        return returnMsg;
    }

    private void saveFile(Path path, String base64String)
            throws IOException {
        Files.deleteIfExists(path);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(base64String);
        try (BufferedWriter writer = Files.newBufferedWriter(path, 
                StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);){
            writer.write(new String(decode));
        } 
    }

    private void saveImage(Path path, String base64String) throws IOException {
        Files.deleteIfExists(path);
        byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64String);
        try (FileOutputStream x = new FileOutputStream(path.toFile());){
            x.write(bytes);
        }
    }
}
