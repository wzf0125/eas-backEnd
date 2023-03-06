//package org.quanta.eas;
//
//
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.config.OutputFile;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * 代码生成器
// */
//public class CodeGenerator {
//    public static void main(String[] args) {
//        /* ************ 配置部分 ************ */
//        String url = "jdbc:mysql://?&useSSL=false&serverTimezone=Asia/Shanghai"; // 数据库url
//        String username = "root"; // 数据库用户名
//        String password = ""; // 数据库密码
//        List<String> tables = new ArrayList<>();
//        // 添加要生成的表名
////        tables.add("admin");
////        tables.add("class");
////        tables.add("course_major");
////        tables.add("course_major_year");
////        tables.add("class_student");
////        tables.add("class_time");
////        tables.add("course");
////        tables.add("course_class");
////        tables.add("major");
////        tables.add("student");
////        tables.add("student_major");
////        tables.add("teacher");
////        tables.add("teacher_major");
//
//        String parent = "org.quanta"; // 项目包名 com.quanta
//        String mode = "eas"; // 模块名 archetype
//        String author = "wzf mqh hzq zjx";
//
//        /* ************ 配置部分 ************ */
//        String projectPath = System.getProperty("user.dir");
//        String outPutDir = projectPath + "/src/main/java";
//        FastAutoGenerator.create(url, username, password)
//                .globalConfig(builder -> {
//                    builder.author(author) // 设置作者
////                            .fileOverride() // 覆盖已生成文件
//                            .outputDir(outPutDir); // 指定输出目录
//                })
//                .packageConfig(builder -> {
//                    builder.parent(parent)
//                            .moduleName(mode)
//                            .entity("entity")
//                            .service("service")
//                            .serviceImpl("service.serviceImpl")
//                            .controller("controller")
//                            .mapper("mapper")
//                            .xml("mapper")
//                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "\\src\\main\\resources\\mapper"));
//                })
//                .strategyConfig(builder -> {
//                    builder.addInclude(tables)
//                            .serviceBuilder()
//                            .formatServiceFileName("%sService")
//                            .formatServiceImplFileName("%sServiceImpl")
//                            .entityBuilder()
//                            .enableLombok()
//                            .logicDeleteColumnName("deleted")
//                            .enableTableFieldAnnotation()
//                            .controllerBuilder()
//                            .formatFileName("%sController")
//                            .enableRestStyle()
//                            .mapperBuilder()
//                            .enableBaseResultMap()  //生成通用的resultMap
//                            .formatMapperFileName("%sMapper")
//                            .enableMapperAnnotation()
//                            .formatXmlFileName("%sMapper");
//                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .execute();
//    }
//}