package com.charsmart.data.cases.tool;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;

/**
 * <p>
 * CodeGenerator
 * </p>
 *
 * @author lanmi.xin@charsmart.com
 * @since 2020/2/22
 */
public class CodeGenerator {

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();

        generator.setTemplateEngine(new FreemarkerTemplateEngine());


        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/CodeGenerator");
        gc.setAuthor("wonder@charsmart.com");
        gc.setOpen(false);
        gc.setServiceName("%sService");
        gc.setIdType(IdType.INPUT);
        generator.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/biz");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("wonder@123");
        generator.setDataSource(dsc);

        PackageConfig pc = new PackageConfig();
        pc.setParent("com.charsmart.data");
        generator.setPackageInfo(pc);

        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setEntityBuilderModel(true);
        strategy.setEntityColumnConstant(true);

        strategy.setInclude("account","bd_order","bd_storage");
        strategy.setTableFillList(new ArrayList<TableFill>() {{
            add(new TableFill("CREATE_BY", FieldFill.INSERT));
            add(new TableFill("CREATE_DATE", FieldFill.INSERT));
            add(new TableFill("UPDATE_BY", FieldFill.UPDATE));
            add(new TableFill("UPDATE_DATE", FieldFill.UPDATE));
        }});

        generator.setStrategy(strategy);

        generator.execute();
    }
}
