import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static Stack<String> jpgUrls = new Stack<>();
    public static CountDownLatch countDownLatch;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("输入要查询的关键字：");
        String keyWord = scan.nextLine();
        String url = "https://image.baidu.com/search/index";
        String parm = "tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fr=&sf=1&fmq=1526269427171_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=";
        String s = Get_Page.sendGet(url, parm + keyWord);
        //提取URL
        Get_Page.remixUrl(s);

        int threadCount  = jpgUrls.size();//因为stack的长度会在pop 的时候减少，所以将完整长度保存
        System.out.println("获取到"+threadCount+"张图片，正在保存到本地");
        countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Save_jpg().start();
        }
        try {
            countDownLatch.await();
            System.out.println("运行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}