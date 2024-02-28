import java.io.File;

public class Main{
    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        
        // Создание объекта File для текущей директории
        File directory = new File(currentDirectory);
        
        // Получение списка файлов в текущей директории
        File[] files = directory.listFiles();
        
        // Проверка, что список файлов не равен null
            // Вывод названий файлов
            for (File file : files) {
                if (file.getName() == "pom.xml"){
                System.out.println(file.getName());
               }
            }
               System.out.println("igor");}

}