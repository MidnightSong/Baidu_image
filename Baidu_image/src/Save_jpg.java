import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Save_jpg extends Thread {
    BufferedOutputStream bs = null;

    public void run() {
        ArrayList<Byte> jpg = Get_Page.getJpg(Main.jpgUrls.pop());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH时mm分ss秒 SSS");
        Date d = new Date();
        String str = sdf.format(d);
        //System.out.println("当前时间通过 yyyy-MM-dd HH:mm:ss SSS 格式化后的输出: "+str);
        File file = new File("src/JPG/" + str + ".jpg");
        try {
            bs = new BufferedOutputStream(new FileOutputStream(file));
            for (byte i : jpg){
                bs.write(i);
            }
            bs.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Main.countDownLatch.countDown();
            if (bs != null) {
                try {
                    bs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
