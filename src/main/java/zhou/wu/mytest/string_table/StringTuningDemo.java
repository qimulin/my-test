package zhou.wu.mytest.string_table;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou.wu
 * @description: StringTable调优
 * @date 2022/1/14
 **/
public class StringTuningDemo {
    public static void main(String[] args) throws IOException {
        List<String> stringList = new ArrayList<>();
        System.in.read();
        for (int i = 0; i < 10; i++) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("linux.words"),"utf-8"))){
                String line = null;
                // 纳秒单位
                long start = System.nanoTime();
                while (true){
                    line = reader.readLine();
                    if(line==null){
                        break;
                    }
                    stringList.add(line);
                }
                System.out.println("cost："+(System.nanoTime()-start)/100000);
            }
        }
    }
}
