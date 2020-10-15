package sd.tools;

import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Configuration
public class FileTool {

    public void downloadFile(File file, OutputStream os) throws IOException {
        // 目标输入流
        FileInputStream fis = new FileInputStream(file);
        // 写入文件
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        // 关闭流
        fis.close();
    }

    /**
     * 将文本输出到文件
     *
     * @param text 目标文本字符串
     * @param file 目标输出文件
     * @return 操作是否成功
     */
    public boolean saveTextToFile(String text, File file) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new
                    OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)));
            out.write(text);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getStringFromFile(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuilder content = new StringBuilder();                  //记录文本内容
            String line;                                                //保存每行读取的内容
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
            content.deleteCharAt(content.length() - 1);
            br.close();
            fileReader.close();
            return content.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean saveObjectToFile(Serializable s, File file) {
        try {
            ObjectOutputStream fileOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            fileOutputStream.writeObject(s);
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Object getObjectFromFile(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            Object object = objIn.readObject();
            objIn.close();
            return object;
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
