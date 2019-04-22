package cn.clouddisk.utils.TestFunction;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.WString;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.NativeLongByReference;

import java.util.HashMap;
import java.util.Map;

public class XLDownLoader {
    String FileName;
    String Url;
    long task;

    /**
     *
     * @param FileName  saved file path
     * @param Url  pre-downloaded url
     * @param task  taskid
     */

    public XLDownLoader( String FileName,String Url,long task){
        this.FileName=FileName;
        this.Url=Url;
        this.task=task;
    }

    public interface XLDownload extends Library {
        public boolean XLInitDownloadEngine();
        public int XLURLDownloadToFile(WString pszFileName, WString pszUrl, WString pszRefUrl, NativeLongByReference lTaskId);
        public int XLQueryTaskInfo(NativeLong lTaskId, NativeLongByReference plStatus, LongByReference pullFileSize, LongByReference pullRecvSize);
        public int XLPauseTask(long lTaskId, NativeLongByReference lNewTaskId);
        public int XLContinueTask(long lTaskId);
        public int XLContinueTaskFromTdFile(WString pszTdFileFullPath, NativeLongByReference lTaskId);
        public void XLStopTask(long lTaskId);
        public boolean XLUninitDownloadEngine();
    }
    /**
     *
     * @return 268435467 pre-downloaded file exist in specified file path
     *         -1 Fail to initialize(Fail to find DLL)
     *         12 Succeed in downloading file of specified url
     */

    public int startTask(String link){
        XLDownload xldownload = Native.load(link,XLDownload.class);
        if(!xldownload.XLInitDownloadEngine()){
            System.out.println("Initialize download engine failed");
            return -1;
        }
        WString pszFileName=new WString(FileName);
        WString pszUrl=new WString(Url);
        WString pszRefUrl=new WString("");
        NativeLong TaskId=new NativeLong(task);
        NativeLongByReference lTaskId=new NativeLongByReference(TaskId);

        int dwRet=xldownload.XLURLDownloadToFile(pszFileName,pszUrl ,pszRefUrl, lTaskId);
        do{
            LongByReference ullFileSize =new LongByReference(0);
            LongByReference ullRecvSize =new LongByReference(0);
            NativeLongByReference  lStatus=new NativeLongByReference(new NativeLong(-1L));
            dwRet=xldownload.XLQueryTaskInfo(TaskId,lStatus,ullFileSize,ullRecvSize);
            if(0==dwRet){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ( 0 != ullFileSize.getValue() )
                {
                    double  douProgress = (double)ullRecvSize.getValue()/(double)ullFileSize.getValue();
                    douProgress *= 100.0;
                    System.out.println(FileName+"Download progress:"+douProgress+"%");
                }
                else
                {
                    System.out.println("File size is zero.\n");
                }

                if (11==lStatus.getValue().intValue())
                {
                    System.out.println("Download successfully.\n");
                    break;
                }

                if ( 12==lStatus.getValue().intValue() )
                {
                    System.out.println("Download failed.\n");
                    break;
                }
            }
        }while(0==dwRet);
        xldownload.XLStopTask(this.task);
        xldownload.XLUninitDownloadEngine();
        return 12;
    }

    public static void main(String[] args){
        Map<String,String> info = new HashMap<String, String>();
        info.put("e:\\temp\\18ec54e736d12f2ee6d124c84dc2d56284356846.jpg","http://h.hiphotos.bdimg.com/wisegame/pic/item/18ec54e736d12f2ee6d124c84dc2d56284356846.jpg");
//        info.put("e:\\temp\\9bdcd100baa1cd11d028f888bb12c8fcc2ce2df8.jpg","http://c.hiphotos.bdimg.com/wisegame/pic/item/9bdcd100baa1cd11d028f888bb12c8fcc2ce2df8.jpg");
//        info.put("e:\\temp\\2410b912c8fcc3cef9f7d0ab9045d688d43f2076.jpg","http://c.hiphotos.bdimg.com/wisegame/pic/item/2410b912c8fcc3cef9f7d0ab9045d688d43f2076.jpg");
//        info.put("e:\\temp\\579b033b5bb5c9eadd791d7bd739b6003bf3b3a4.jpg","http://a.hiphotos.bdimg.com/wisegame/pic/item/579b033b5bb5c9eadd791d7bd739b6003bf3b3a4.jpg");
//        info.put("e:\\temp\\aaea15ce36d3d5397db7115b3887e950342ab0a4.jpg","http://d.hiphotos.bdimg.com/wisegame/pic/item/aaea15ce36d3d5397db7115b3887e950342ab0a4.jpg");
//        info.put("e:\\temp\\abfcc3cec3fdfc03ae4ffa20d63f8794a4c22676.jpg","http://e.hiphotos.bdimg.com/wisegame/pic/item/abfcc3cec3fdfc03ae4ffa20d63f8794a4c22676.jpg");
//        info.put("e:\\temp\\f145d688d43f8794e4c851b6d01b0ef41bd53a76.jpg","http://a.hiphotos.bdimg.com/wisegame/pic/item/f145d688d43f8794e4c851b6d01b0ef41bd53a76.jpg");
        //   final AtomicInteger ai = new AtomicInteger(1);
        //    ExecutorService service = Executors.newFixedThreadPool(1);
        //    int i=1;
        //   List<Future> futures = new ArrayList<Future>();
        for(final Map.Entry<String,String> entry: info.entrySet()){
            //i++;
            XLDownLoader instance=new XLDownLoader(entry.getKey(),entry.getValue(),0);
//            instance.startTask(String.format("XLDownload",1));
            instance.startTask(XLDownload.class.getResource("").getPath().replaceFirst("/","")+ "XLDownload.dll");

            /*service.execute(new Runnable() {
                @Override
                public void run() {
                    com.leunpha.XLDownLoader instance=new com.leunpha.XLDownLoader(entry.getKey(),entry.getValue(),ai.get());
                    instance.startTask("E:\\xl\\"+ai.getAndIncrement()+"\\XLDownload.dll");
                }
            });
            futures.add(service.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    return null;
                }
            }));*/
        }
     /*   service.shutdown();
        for(Future future:futures){
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }*/

    }

}
