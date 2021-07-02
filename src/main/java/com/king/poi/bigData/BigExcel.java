package com.king.poi.bigData;

import com.github.andyczy.java.excel.ExcelUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljq on 2019/5/31 16:35
 */
public class BigExcel {

    public void export(HttpServletResponse response){

        List dataLists = new ArrayList(); //导出的数据
        String[] sheetNameList = new String[]{"今日交易记录","今日交易明细"}; //导出表格sheet名称


        //【推荐使用该方式】【建议大数据量下不要过多设置样式】

        ExcelUtils excelUtils = ExcelUtils.initialization();
        // 必填项--导出数据（参数请看下面的格式）
        excelUtils.setDataLists(dataLists);
        // 必填项--sheet名称（如果是多表格导出、sheetName也要是多个值！）
        excelUtils.setSheetName(sheetNameList);
        // 文件名称(可为空，默认是：sheet 第一个名称)
        excelUtils.setFileName("");

        // web项目response响应输出流：必须填 【ExcelUtils 对象】
        excelUtils.setResponse(response);

        // 输出本地【LocalExcelUtils 对象】
        // excelUtils.setFilePath("F://test.xlsx");

        // 每个表格的大标题（可为空）
        excelUtils.setLabelName(sheetNameList);
        // 自定义：固定表头（可为空）
        excelUtils.setPaneMap(null);
        // 自定义：单元格合并（可为空）
        excelUtils.setRegionMap(null);

        // 自定义：对每个单元格自定义列宽（可为空）
        excelUtils.setMapColumnWidth(null);
        // 自定义：某一行样式（可为空）
        excelUtils.setRowStyles(null);
        // 自定义：某一列样式（可为空）
        excelUtils.setColumnStyles(null);
        // 自定义：每一个单元格样式（可为空）
        excelUtils.setStyles(null);

        // 自定义：对每个单元格自定义下拉列表（可为空）
        excelUtils.setDropDownMap(null);
        // 自定义：忽略边框(可为空：默认是有边框)
        excelUtils.setNotBorderMap(null);

        // 执行导出
        excelUtils.exportForExcelsOptimize();
    }




}
