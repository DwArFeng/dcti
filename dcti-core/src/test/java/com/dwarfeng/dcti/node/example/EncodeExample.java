package com.dwarfeng.dcti.node.example;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.Scanner;

/**
 * 编码示例。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class EncodeExample {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-context*.xml"
        );
        ctx.registerShutdownHook();
        ctx.start();

        DctiHandler dctiHandler = ctx.getBean(DctiHandler.class);

        Scanner scanner = new Scanner(System.in);

        // 显示欢迎信息。
        System.out.println("开发者您好!");
        System.out.println("这是一个示例, 用于演示 dwarfeng-dcti 的编码功能");
        System.out.println("该示例将会把预设的 DataInfo 编码为 JSON 消息文本");
        System.out.print("请按回车键开始...");
        scanner.nextLine();

        // 1. 基本编码。
        System.out.println();
        System.out.println("1. 基本编码...");
        DataInfo dataInfoA = new DataInfo(12345L, "25.6", new Date(1710000000000L), 0);
        System.out.println("原始数据内容:");
        printDataInfo(dataInfoA);
        String messageA = encode(dctiHandler, dataInfoA);
        System.out.println("编码内容:");
        System.out.println(messageA);
        System.out.print("请按回车键继续...");
        scanner.nextLine();

        // 2. 带纳秒偏移的编码。
        System.out.println();
        System.out.println("2. 带纳秒偏移的编码...");
        DataInfo dataInfoB = new DataInfo(12345L, "25.6", new Date(1710000000000L), 123456);
        System.out.println("原始数据内容:");
        printDataInfo(dataInfoB);
        String messageB = encode(dctiHandler, dataInfoB);
        System.out.println("编码内容:");
        System.out.println(messageB);
        System.out.print("请按回车键继续...");
        scanner.nextLine();

        System.out.println();
        System.out.println("示例演示完毕, 感谢您测试与使用!");

        ctx.stop();
        ctx.close();
        System.exit(0);
    }

    private static String encode(DctiHandler dctiHandler, DataInfo dataInfo) throws HandlerException {
        return dctiHandler.toMessage(dataInfo);
    }

    private static void printDataInfo(DataInfo dataInfo) {
        System.out.printf("pointLongId: %d%n", dataInfo.getPointLongId());
        System.out.printf("value: %s%n", dataInfo.getValue());
        System.out.printf("happenedDate: %s%n", dataInfo.getHappenedDate());
        System.out.printf("happenedDateNanoOffset: %d%n", dataInfo.getHappenedDateNanoOffset());
    }
}
