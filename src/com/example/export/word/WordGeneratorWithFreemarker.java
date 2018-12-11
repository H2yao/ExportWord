package com.example.export.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;

/**
 * word导出帮助类 通过freemarker模板引擎来实现
 * @author Java
 *
 */
public class WordGeneratorWithFreemarker {
	private static Configuration configuration = null;

	static {
		configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassicCompatible(true);
		configuration.setClassForTemplateLoading(
				WordGeneratorWithFreemarker.class,
				"/com/example/export/word/ftl");

	}

	private WordGeneratorWithFreemarker() {

	}

	public static void createDoc(Map<String, Object> dataMap,String templateName, OutputStream out)throws Exception {
		Template t = configuration.getTemplate(templateName);
		t.setEncoding("utf-8");
		WordHtmlGeneratorHelper.handleAllObject(dataMap);

		try {
			Writer w = new OutputStreamWriter(out,Charset.forName("utf-8"));
			t.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public static String test(String remark)throws Exception {
		HashMap<String, Object> data = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		sb.append(remark);
		sb.append("<div>");
		sb.append("<img style='height:100px;width:200px;display:block;' src='F:\\test.png' />");
		sb.append("</br><span>12345678</span>");
		sb.append("<h1><span style='color: rgb(194, 79, 74);'>TEST CONTENT ..............</span></h1></div>");
		RichHtmlHandler handler = new RichHtmlHandler(sb.toString());

		handler.setDocSrcLocationPrex("file:///C:/8595226D");
		handler.setDocSrcParent("file3405.files");
		handler.setNextPartId("01D214BC.6A592540");
		handler.setShapeidPrex("_x56fe__x7247__x0020");
		handler.setSpidPrex("_x0000_i");
		handler.setTypeid("#_x0000_t75");

		handler.handledHtml(false);

		String bodyBlock = handler.getHandledDocBodyBlock();
		System.out.println("bodyBlock:\n"+bodyBlock);

		String handledBase64Block = "";
		if (handler.getDocBase64BlockResults() != null
				&& handler.getDocBase64BlockResults().size() > 0) {
			for (String item : handler.getDocBase64BlockResults()) {
				handledBase64Block += item + "\n";
			}
		}
		data.put("imagesBase64String", handledBase64Block);

		String xmlimaHref = "";
		if (handler.getXmlImgRefs() != null
				&& handler.getXmlImgRefs().size() > 0) {
			for (String item : handler.getXmlImgRefs()) {
				xmlimaHref += item + "\n";
			}
		}
		data.put("imagesXmlHrefString", xmlimaHref);
		data.put("name", "USER");
		data.put("content", bodyBlock);

		String docFilePath = "d:\\document.doc";
		System.out.println(docFilePath);
		File f = new File(docFilePath);
		OutputStream out;
		try {
			out = new FileOutputStream(f);
			WordGeneratorWithFreemarker.createDoc(data, "temp.ftl", out);

		} catch (FileNotFoundException e) {

		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "yes";
		
	}
	/*
	public static void main(String[] args) throws Exception {
		testt(null);
	}*/
}