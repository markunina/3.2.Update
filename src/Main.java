import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("C:/Games/savegames/save.dat");
        list.add("C:/Games/savegames/save1.dat");
        list.add("C:/Games/savegames/save2.dat");
        GameProgress gp1 = new GameProgress(1, 1, 1, 1);
        GameProgress gp2 = new GameProgress(2, 2, 2, 2);
        GameProgress gp3 = new GameProgress(3, 2, 2, 2);
        saveGame(list.get(0), gp1);
        saveGame(list.get(1), gp2);
        saveGame(list.get(2), gp3);
        zipFiles("C:/Games/savegames/zip.zip",list);
        for(String path : list){
            File file = new File(path);
            file.delete();
        }
    }

    public static void saveGame(String path, GameProgress gp) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (int i = 0; i < list.size();i++) {
                try (FileInputStream fis = new FileInputStream(list.get(i))) {
                    ZipEntry entry = new ZipEntry("save_" + i + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
