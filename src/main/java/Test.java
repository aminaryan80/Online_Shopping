import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File(Test.class.getClassLoader().getResource("images/product.jpg").getFile());
        System.out.println(file.getPath());
        File file2 = new File(getAddress("fuck3.png"));
        FileUtils.copyFile(file, file2);
    }

    private static String getAddress(String fileName) {
        String address = String.valueOf(Test.class.getClassLoader().getResource("images/product.jpg"));
        address = address.replace("file:/", "");
        address = address.replace("product.jpg", "products/");
        address = address + fileName;
        address = address.replace("/", "\\");
        return address;
    }
}
