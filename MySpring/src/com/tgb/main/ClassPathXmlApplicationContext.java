package com.tgb.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tgb.config.Bean;
import com.tgb.config.Property;
import com.tgb.config.parse.ConfigManager;
import com.tgb.utils.BeanUtils;

public class ClassPathXmlApplicationContext implements BeanFactory{
	//������Ϣ
	private Map<String, Bean> config;
	//��һ��map����spring������������spring������Ķ��� 
	private Map<String, Object> context=new HashMap<String,Object>();
	
	
	
	
	//��ClassPathXmlApplicationContextһ�����ͳ�ʼ��spring����
	
	
	@Override
	//����bean���ƻ��beanʵ��
	public Object getBean(String beanName) {
		Object bean =context.get(beanName);
		return bean;
	}
	
	public ClassPathXmlApplicationContext(String path) {
		//1��ȡ�����ļ���ȡ��ʼ����bean��Ϣ
		config = ConfigManager.getConfig(path);
		
		//2�������ã���ʼ��bean
		if (config!=null) {
			for(Entry<String,Bean> en :config.entrySet()){
				//��ȡ�����е�bean��Ϣ
				String beanName=en.getKey();
				Bean bean=en.getValue();
				
				Object exsitBean=context.get(beanName);
				//��ΪcreateBean������Ҳ����context�з���bean�������ڳ�ʼ����ʱ����Ҫ�鿴�Ƿ��Ѿ�����bean
				//����������ٴ���bean
				if (exsitBean==null) {
					//����bean���ô���bean����
					Object beanObj=creatBean(bean);
					//3����ʼ���õ�bean��������
					context.put(beanName, beanObj);
				}

			}
		}
		 
		
		
	}
	//����bean���ô���bean����
	private Object creatBean(Bean bean) {
		// TODO Auto-generated method stub
		//1���Ҫ������bean��class
		String className = bean.getClassName();
		Class clazz=null;
		try {
			clazz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("����bean��class����"+className);
		}
		//��class��Ӧ�Ķ��󴴽�����
		Object beanObj=null;
		try {
			 beanObj= clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("beanû�пղι���"+className);
		} 
		
		//2���bean�����ԣ�����ע��
		
		if (bean.getProperties()!=null) {
			for(Property property:bean.getProperties()){
				//1:��valueע��
				//��ȡҪע�����������
				String name=property.getName();
				//�����������ƻ��ע�����Զ�Ӧ��set����
				Method setMethod = BeanUtils.getWriteMethod(beanObj,name);
				//����һ����Ҫע��bean�е�����
				Object parm=null;
				if (property.getValue()!=null) {
					//��ȡҪע�������ֵ
					String value=property.getValue();
					parm=value;
				
				}
			    //2����bean��ע��
				if (property.getRef()!=null) {
					//�ȴ������в��ҵ�ǰҪע���bean�Ƿ��Ѿ�����������������
					Object exsitBean = context.get(property.getRef());
					if (exsitBean==null) {
						//��������в����ڣ���Ҫ����
						 exsitBean = creatBean(config.get(property.getRef()));
						 //�������õ�bean��������
						 context.put(property.getRef(), exsitBean);
						
					}
					 parm=exsitBean;
				}
				
				//����set����ע��
				try {
					setMethod.invoke(beanObj, parm);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("bean������"+parm+"û�ж�Ӧ��set���������߲�������ȷ"+className);
				} 
			}
		}
		 
		return beanObj;
	}

}
