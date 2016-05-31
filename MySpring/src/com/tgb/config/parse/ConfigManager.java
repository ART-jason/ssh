package com.tgb.config.parse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tgb.config.Bean;
import com.tgb.config.Property;

public class ConfigManager {
	//��ȡ�����ļ��������ؽ��
	public static Map<String, Bean> getConfig( String path){
		
		Map<String, Bean> map =new HashMap<String, Bean>();
		//1����������
		SAXReader saxReader = new SAXReader();
		//2���������ļ�
		InputStream is=ConfigManager.class.getResourceAsStream(path);
		Document document=null;
		try {
			 document = saxReader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("����xml����");
		}
		//3����xpath���ʽȥ������beanԪ��
		String xpath="//bean";
		//4��beanԪ�ؽ��б���
		List<Element> list =document.selectNodes(xpath);
		if (list!=null) {
			for(Element beanFile:list){
				Bean bean = new Bean();
				//��class,name�����Է�װ��bean������
				String name = beanFile.attributeValue("name");
				String className= beanFile.attributeValue("class");
				bean.setName(name);
				bean.setClassName(className);
				//���beanԪ���µ�����propertyԪ�أ����������Է�װ��property��Ԫ����
				List<Element> children = beanFile.elements("property");
				if (children!=null) {
					for(Element child:children){
						Property property = new Property();
						String pName=child.attributeValue("name");
						String pValue=child.attributeValue("value");
						String pRef=child.attributeValue("ref");
						
						property.setName(pName);
						property.setRef(pRef);
						property.setValue(pValue);
						//��property�����װ��bean��
						bean.getProperties().add(property);
						
						
					}
				}
				//��bean�����װ��map�����ڷ��ء�
				
				map.put(name, bean);
			}	
			
		}

		//5����map
		return map;
		
	}
}
