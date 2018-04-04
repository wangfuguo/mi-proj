package com.hoau.miser.module.api.itf.server.util;

import java.util.Vector;

/**
 * �ַ�ʵ�ù��߼�
 * @author hsw
 */
public class StrUtil 
{

	/**
	 * ����ORACLE�е�LDAP������������ַ�
	 * @param str �������ַ�
	 * @param len ָ���ĳ���
	 * @param c ����ַ�
	 * @return ����곤�ȵ��ַ�
	 */
	public static String lpad(String str, int len, char c)
	{
		if(str == null)
		{
			str = "";
		}
		while(str.length() < len)
		{
			str = c + str;
		}
		return str;
	}
	
	/**
	 * �ж�һ���ַ��Ƿ�Ϊ��, null ���� �㳤��
	 * ����û�п��ǿ��ַ�����
	 * @param str ���жϵ��ַ�
	 * @return �Ƿ�Ϊ�մ�
	 */
	public static boolean isEmpty(String str)
	{
		return str == null || str.length() == 0;
	}
	
	/**
	 * �ж�һ���ַ��Ƿ�Ϊ��, null ���� �㳤��
	 * �����˿��ַ�����
	 * @param str
	 * @return
	 */
	public static boolean isEmptyConsideBlank(String str)
	{
		boolean flag = false;
		if(isEmpty(str))
		{
			flag = true;
		}
		else if(str.indexOf(' ') != -1)
		{
			flag = isEmpty(str.trim());
		}
		return flag;
	}
	
	/**
	 * ָ������ַ��Ƿ����һ�����ַ�
	 * @param str �ַ�����
	 * @return ����һ��Ϊ�յľͷ���true
	 */
	public static boolean isEmptyAny(String... str)
	{
		for (int i = 0; i < str.length; i++) {
			if(isEmpty(str[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ָ������ַ��Ƿ�ȫ��Ϊ��
	 * @param str �ַ�
	 * @return ȫ��Ϊ�շ���true
	 * */
	public static boolean isEmptyAll(String ... str)
	{
		for (int i = 0; i < str.length; i++) {
			if (!isEmpty(str[i])) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * �ж������ַ��Ƿ���ȣ�nullҲ��ʾ���ַ�
	 */
	public static boolean equalsString(String str1, String str2)
	{
		//����һ���ַ�Ϊ�գ�ֻ�еڶ����ַ�ͬʱΪ�յ��������Ϊ����ȵģ�
		if(isEmpty(str1))
		{
			return isEmpty(str2);
		}
		return str1.equals(str2);
	}

	/**
	 * ��ȫ�İ�һ���ַ�ת��Ϊ����������ת������ֱ�ӷ���0
	 * @param str ��ת�����ַ�
	 * @return ��Ӧ������
	 */
	public static int toInt(String str)
	{
		int value = 0;
		if(!isEmptyConsideBlank(str))
		{
			try{
				value = Integer.parseInt(str);
			}
			catch(Exception e){}
		}
		return value;
	}

	/**
	 * ��ȫ�İ�һ���ַ�ת��Ϊ������������ת������ֱ�ӷ���0
	 * @param str ��ת�����ַ�
	 * @return ��Ӧ�ĸ�����
	 */
	public static double toDouble(String str)
	{
		double value = 0;
		if(!isEmptyConsideBlank(str))
		{
			try{
				value = Double.parseDouble(str);
			}
			catch(Exception e){}
		}
		return value;
	}
	
	

	/**
	 * ���컺���Key����Ϊ�ɱ������ɹ�����������
	 * @param params
	 * @return
	 */
	public static String toKey(String... params)
	{
		//û�в����null
		if(params == null || params.length == 0)
		{
			return null;
		}
		//ֻ��һ������ֱ�ӷ��ؼ���
		if(params.length == 1)
		{
			return params[0];
		}
		//���������Ҫ����key
		StringBuilder sb = new StringBuilder(params[0]==null?"":params[0]);
		for (int i = 1; i < params.length; i++)
		{
			sb.append("_").append(params[i]==null?"":params[i]);
		}
		return sb.toString();
	}

	

	/**
	 * ��ld.server.SysLogUtil.maybeNeedCutStr�и��ƹ����ġ�
	 * ���ָ���ĳ��ȶ��ַ���н�ȡ(���byte���������н�ȡ)
	 * @param str ԭʼ�ַ�
	 * @param byteLen ָ������,Byte���ȣ������ַ����
	 * @return ��ȡ����ַ�
	 */
	public static String maybeNeedCutStr(String str, int byteLen)
	{
		if(str == null || byteLen < 0)
		{
			return null;
		}
		if(str.getBytes().length <= byteLen)
		{
			return str;
		}
		int size = str.length();
		//strȫ�������ĵ�ʱ��byteLen/2Ҳ��byte����
		StringBuffer sb = new StringBuffer(str.substring(0, byteLen/2));
		int pos = sb.toString().length();
		if(pos <= size)
		{
			char c = str.charAt(pos++);
			while(pos <= size && sb.toString().getBytes().length+
					String.valueOf(c).getBytes().length <= byteLen)
			{
				sb.append(c);
				c = str.charAt(pos++);
			}
		}
		return sb.toString();
	}


	public static Vector getSplitedValue(String str, char ch)
    {
		Vector ret = new Vector();
        if(str != null && str.length() > 0)
        {
            int point = 0;
            int start = 0;
            for(; point < str.length(); point++)
                if(str.charAt(point) == ch)
                {
                    ret.add(str.substring(start, point).trim());
                    start = point + 1;
                }
            ret.add(str.substring(start, point).trim());
        }
        return ret;
    }


	/**
	 * ���ַ��е��ض��ַ��滻Ϊָ�����ַ�
	 * @param value String ���滻���ַ�
	 * @param oldStr String ����ԭʼ���ַ�
	 * @param newStr String ��Ҫ�滻���µ��ַ�
	 * @return String �滻��ɵ��ַ�
	 */
	public static String replaceAll(String value, String oldStr, String newStr)
	{
		int pos = -1;
		if(value != null && (pos=value.indexOf(oldStr)) != -1)
		{
			int start = 0;
			StringBuffer newValue = new StringBuffer();
			while(pos != -1)
			{
				newValue.append(value.substring(start, pos));
				newValue.append(newStr);
				start = pos + oldStr.length();
				pos = value.indexOf(oldStr, start);
			}
			newValue.append(value.substring(start, value.length()));
			return newValue.toString();
		}
		return value;
	}
	
	/**
	 * ��ԭ�������Ļ��������µ�����
	 * @param originFilter ԭ�ȵ�����������Ϊ��
	 * @param newFilter �µ�������һ�㲻Ϊ��
	 * @return ����õ�����
	 */
	public static String appendFilter(String originFilter, String newFilter)
	{
		if(isEmptyConsideBlank(originFilter))
		{
			return newFilter;
		}
		else
		{
			return originFilter+" AND "+newFilter;
		}
	}
	
	/**
	 * ���lenλ����������ּ���ĸ���ַ�(0~9+A~Z).
	 * ע:��������ĸ�ĳ���Ҳ������.
	 * @param len int����ַ���
	 * @return String ��������ַ�
	 */
	public static String getRandomStr(int len)
	{
		java.util.Random random = new java.util.Random();
		StringBuilder sb = new StringBuilder();
		do
		{
			//�����������ַ���ȡ���ֻ�����ĸ
			//			int decide=Math.abs(random.nextInt()) % 2;
			//			if(decide == 0)
			if(true)
			{
				//����48��57�������(0-9�ļ�λֵ)
				sb.append(Math.abs(random.nextInt()) % 10);
			}
			else
			{
				//����97��122�������(A-Z�ļ�λֵ)
				sb.append((char) (Math.abs(random.nextInt()) % 26 + (int) 'A'));
			}
		} while (sb.length() < len);

		return sb.toString();
	}
	
	/**
	 * ע�⣺�˷���ֻ��oracle10g֮��汾ʹ�ã�
	 * ���±��ֶΣ��ڱ�û�м�¼������£��������һ����¼�����Դ˷���������һ��merge���
	 * @param sqls ��紫��Ĵ�ִ�е�SQL����
	 * @param values ��紫��Ĵ�ִ�е�Value����
	 * @param table ��Ҫ����/����ı����
	 * @param keyFields �˱�������ֶ�����
	 * @param keyValues �����Ӧ��ֵ
	 * @param updateFields ��Ҫ���µ��ֶ�
	 * @param updateValues ��Ҫ���¶�Ӧ��ֵ
	 * @param updateOtherCondition ������������
	 */
	public static void updateAndMerge(Vector sqls, Vector values, String table, String keyField, Object keyValue
			, String updateField, String updateValue, String updateOtherCondition)
	{
		updateAndMerge(sqls, values, table, new String[]{keyField}, new Object[]{keyValue},  
				new String[]{updateField}, new Object[]{updateValue}, updateOtherCondition, null, null);
	}
	
	/**
	 * ע�⣺�˷���ֻ��oracle10g֮��汾ʹ�ã�
	 * ���±��ֶΣ��ڱ�û�м�¼������£��������һ����¼�����Դ˷���������һ��merge���
	 * @param sqls ��紫��Ĵ�ִ�е�SQL����
	 * @param values ��紫��Ĵ�ִ�е�Value����
	 * @param table ��Ҫ����/����ı����
	 * @param keyFields �˱�������ֶ�����
	 * @param keyValues �����Ӧ��ֵ
	 * @param updateFields ��Ҫ���µ��ֶ�
	 * @param updateValues ��Ҫ���¶�Ӧ��ֵ
	 * @param updateOtherCondition ������������
	 * @param insertDefaultFields ������Ļ���������Ҫ��ֵ���ֶ�
	 * @param insertDefaultValues ������Ļ���������Ҫ��ֵ��Ӧ��ֵ
	 */
	public static void updateAndMerge(Vector sqls, Vector values, String table, String[] keyFields, Object[] keyValues, 
			String[] updateFields, Object[] updateValues, String updateOtherCondition, String[] insertDefaultFields, Object[] insertDefaultValues)
	{
		StringBuffer sb = new StringBuffer();
		//insertʱ���valuesֵ
		StringBuffer insertSb = new StringBuffer();
		//values������
		Object[] valueObj = new Object[insertDefaultValues == null ? (keyValues.length + updateValues.length) : (keyValues.length + updateValues.length + insertDefaultValues.length)];
		sb.append("MERGE INTO ").append(table).append(" A USING ");
		sb.append("(SELECT ");
		//querySQL�Ȳ�ѯkeyvalues
		for (int i = 0; i < keyValues.length; i++) {
			sb.append("? AS F").append(i).append(",");
			valueObj[i] = keyValues[i];
		}
		//querySQL�ٲ�ѯupdatevalues
		for (int i = 0; i < updateValues.length; i++) {
			sb.append("? AS F").append(keyValues.length + i).append(",");
			valueObj[keyValues.length + i] = updateValues[i]; 
		}
		//querySQL����ѯinsertDefaultValues
		if (insertDefaultValues != null) {
			for (int i = 0; i < insertDefaultValues.length; i++) {
				sb.append("? AS F").append(keyValues.length + updateValues.length + i).append(",");
				valueObj[keyValues.length + updateValues.length + i] = insertDefaultValues[i];
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" FROM DUAL) B ON (");
		//�����ѯ�ֶ�
		for (int i = 0; i < keyValues.length; i++) {
			sb.append("A.").append(keyFields[i]).append(" = F").append(i).append(" AND ");
		}
		sb.delete(sb.length() - 4, sb.length());
		sb.append(")");
		//ƥ��update
		sb.append("WHEN MATCHED THEN UPDATE SET ");
		for (int i = 0; i < updateFields.length; i++) {
			sb.append("A.").append(updateFields[i]).append(" = F").append(keyValues.length + i).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		if (!isEmpty(updateOtherCondition)) {
			sb.append(" WHERE ").append(updateOtherCondition);
		}
		//��ƥ��insert
		sb.append(" WHEN NOT MATCHED THEN INSERT ( ");
		for (int i = 0; i < keyFields.length; i++) {
			sb.append("A.").append(keyFields[i]).append(",");
			insertSb.append("F").append(i).append(",");
		}
		for (int i = 0; i < updateFields.length; i++) {
			sb.append("A.").append(updateFields[i]).append(",");
			insertSb.append("F").append(keyFields.length + i).append(",");
		}
		if (insertDefaultFields != null) {
			for (int i = 0; i < insertDefaultValues.length; i++) {
				sb.append("A.").append(insertDefaultFields[i]).append(",");
				insertSb.append("F").append(keyFields.length + updateFields.length + i).append(",");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		insertSb.deleteCharAt(insertSb.length() - 1);
		sb.append(") VALUES (");
		sb.append(insertSb).append(")");
		sqls.add(sb.toString());
		values.add(valueObj);
	}
}
