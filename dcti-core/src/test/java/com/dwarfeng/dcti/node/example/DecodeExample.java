package com.dwarfeng.dcti.node.example;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * 解码示例。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DecodeExample {

    private static final String DEMO_MESSAGE =
            "{\"point_long_id\":12345,\"value\":\"25.6\",\"happened_date\":1710000000000," +
                    "\"happened_date_nano_offset\":123456}";

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
        System.out.println("这是一个示例, 用于演示 dwarfeng-dcti 的解码功能");
        System.out.println("该示例将会把预设的 JSON 消息文本解码为 DataInfo");
        System.out.print("请按回车键开始...");
        scanner.nextLine();

        // 1. 解码演示。
        System.out.println();
        System.out.println("1. 解码演示...");
        DataInfo dataInfo = decode(dctiHandler, DEMO_MESSAGE);
        System.out.println("原始数据内容:");
        printDataInfo(dataInfo);
        System.out.println("编码内容:");
        System.out.println(DEMO_MESSAGE);
        System.out.print("请按回车键继续...");
        scanner.nextLine();

        System.out.println();
        System.out.println("示例演示完毕, 感谢您测试与使用!");

        ctx.stop();
        ctx.close();
        System.exit(0);
    }

    // 为了代码的可扩展性，此处不做简化。
    @SuppressWarnings("SameParameterValue")
    private static DataInfo decode(DctiHandler dctiHandler, String message) throws HandlerException {
        return dctiHandler.fromMessage(message);
    }

    private static void printDataInfo(DataInfo dataInfo) {
        System.out.printf("pointLongId: %d%n", dataInfo.getPointLongId());
        System.out.printf("value: %s%n", dataInfo.getValue());
        System.out.printf("happenedDate: %s%n", dataInfo.getHappenedDate());
        System.out.printf("happenedDateNanoOffset: %d%n", dataInfo.getHappenedDateNanoOffset());
    }
}
