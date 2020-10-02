package rostyk.stupnytskiy.andromeda.tools;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Component
public class FileTool {

    public static final String PROJECT_DIR =
            System.getProperty("user.home")+ File.separator + "andromeda" + File.separator;

    public static final String CATEGORY_DIR =
            PROJECT_DIR + "category" + File.separator;

    public String saveCategoryImage(String img) throws IOException {
       return saveImage(img,CATEGORY_DIR);
    }

    public String saveUserAvatarImage(String img, String userLogin) throws IOException {
        String userDir = PROJECT_DIR + "user_" + userLogin + File.separator;
        return saveImage(img,userDir);
    }

    public String savePublicationImage(String img, String userLogin) throws IOException {
        String userDir = PROJECT_DIR + "user_" + userLogin + File.separator + "advertisements" + File.separator;
        return saveImage(img,userDir);
    }

    private String saveImage(String img, String dir) throws IOException {
        createDir(dir);//create folder if not exists

        String[] data = img.split(",");
        String metaInfo = data[0];
        String base64File = data[1];

        String fileName = createFileName(null,
                getFileExtensionFromMetaInfo(metaInfo));

        Files.write(
                Paths.get(dir, fileName),
                Base64.getDecoder().decode(base64File.getBytes())
        );
        return fileName;
    }

    private String createFileName(String fileName, String fileExtension) {
        if (fileName == null) {
            fileName = UUID.randomUUID().toString();
        }
        return String.format("%s.%s", fileName, fileExtension);
    }

    //data:image/jpeg;base64
    private String getFileExtensionFromMetaInfo(String metaInfo) {
        return metaInfo.split("/")[1].split(";")[0];
    }

    private void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
