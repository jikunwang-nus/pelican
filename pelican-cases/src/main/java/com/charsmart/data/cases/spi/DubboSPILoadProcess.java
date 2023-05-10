package com.charsmart.data.cases.spi;

import com.charsmart.data.spi.api.Print;
import org.apache.dubbo.common.extension.ExtensionDirector;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/16 11:08 AM
 */
public class DubboSPILoadProcess {
    public static void main(String[] args) {
        ExtensionLoader<Print> loader = ExtensionLoader.getExtensionLoader(Print.class);
        Print print = loader.getExtension("print");
        print.print("1");
    }
}
