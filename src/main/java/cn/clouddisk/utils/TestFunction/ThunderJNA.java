package cn.clouddisk.utils.TestFunction;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.win32.StdCallLibrary;

public class ThunderJNA {
    static {
        Native.register("XLDownload");
    }

    public interface CLibrary extends StdCallLibrary {
        CLibrary INSTANCE = (CLibrary)
//                Native.loadLibrary("LibSupervisor",
                Native.load((Platform.isWindows() ? "XLDownload" : "c"),
                        CLibrary.class);
        public boolean XLInitDownloadEngine();
//        IntByReference init();
//        void printf(String format, Object... args);
    }

    public static void main(String[] args) {

        try {
            CLibrary.INSTANCE.XLInitDownloadEngine();
//                    printf("Hello, World\n");
//            System.out.println("当前的" + CLibrary.INSTANCE.init());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
