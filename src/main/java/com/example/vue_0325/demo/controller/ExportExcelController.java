package com.example.vue_0325.demo.controller;

import com.example.vue_0325.demo.service.SysUserService;
import com.example.vue_0325.demo.utils.Lg;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2019-04-02 21:58
 */
@RestController
public class ExportExcelController {
    @Resource
    private SysUserService sysUserService;
    @RequestMapping("/exportExcel")
    public void export(HttpServletResponse response){
        try{
            response.setContentType("application/octet-stream");//xxx.*
            String filename = "千锋员工信息.xls";
            filename = URLEncoder.encode(filename,"utf-8");//编码
            //标题名
            response.setHeader("content-disposition","attachment;filename="+filename);
            List<Map<String,Object>> list = sysUserService.exportExcel();
            //list--->excel
            Workbook workbook = new HSSFWorkbook();//空的excel文件
            Sheet sheet =  workbook.createSheet("千锋集团员工信息");//工作簿
            String titles = "userId,username,email,mobile,createTime,sex";//excel的头字段
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i); //行
                String t[] = titles.split(",");
                Map<String,Object> map = list.get(i);

                for (int j = 0; j < t.length; j++) {
                    //单元格
                    Cell cell = row.createCell(j);//列
                    row.createCell(j).setCellValue(map.get(t[j])+"");//给单元格赋值
                }
            }
            OutputStream os =  response.getOutputStream();
            workbook.write(os);//把excel文件响应到客户端
            os.flush();
            Lg.log("导出成功");
        }catch(Exception e){
            e.printStackTrace();
            Lg.log("导出失败");
        }
    }
}