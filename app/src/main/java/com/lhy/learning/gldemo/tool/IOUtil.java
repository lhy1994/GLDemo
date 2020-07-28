package com.lhy.learning.gldemo.tool;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
