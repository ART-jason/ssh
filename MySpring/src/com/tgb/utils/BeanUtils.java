package com.tgb.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanUtils {
	/**
	 * 
	 * @param beanObj bean����
	 * @param name Ҫ���bean�����Ӧ����������
	 * @return
	 */
	public static Method getWriteMethod(Object beanObj, String name) {
		//ʹ����ʡʵ�֣�����java����ר�����ڲ���bean�����Ե�api��
		Method method=null;
			try { 
				//1:����bean����-->BeanInfo
				BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
				//2:����BeanInfo��ȡ�������Ե�������
				PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
				//3������������
					if (pds!=null) {
						for(PropertyDescriptor pd:pds){
							//�жϵ�ǰ�����Ƿ�������Ҫ�ҵ�����
							String pName = pd.getName();
							if (pName.equals(name)) {
								method = pd.getWriteMethod();
								
							}
						}
					}
					
				
				//4�������ҵ���set����
					
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//���û���ҵ�--���׳��쳣����ʾ�û�����Ƿ񴴽����Զ�Ӧ��set����
			if (method==null) {
				throw new RuntimeException("����"+name+"���Ե�set�����Ƿ񴴽�");
			}
		
		return method;
	}

}
