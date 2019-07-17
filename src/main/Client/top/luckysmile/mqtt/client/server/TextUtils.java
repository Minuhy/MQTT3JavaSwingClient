package top.luckysmile.mqtt.client.server;


/**
 * �ַ������࣬�о�������ɣ��жϳ��ȵ�Ҳ�������
 * @author minuy
 *
 */
public class TextUtils {

	public static boolean isEmpty(String in) {
		if(in == null) {
			return true;
		}

		if(in.equals("")) {
			return true;
		}

		return false;
	}


/**
 * �����ַ�����ʵ���ȣ�����2����Ӣ������1��
 * @param value Ҫ������ַ���
 * @return ����
 */
	public static int getStringLength(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
	/**
	 * �� java����ȡ����Ӣ���ַ������������ʡ�Ժ���ʾ������ռ����
	 * @param str Ҫ������ַ���
	 * @param maxLength ����
	 * @return �������ַ���
	 * ������
	 */
	public static String subStringCN(final String str, final int maxLength, final int minLength) {
		if (str == null) {
			return str;
		}
		String suffix = "��";
		int suffixLen = suffix.length();
		final StringBuffer sbuffer = new StringBuffer();
		final char[] chr = str.trim().toCharArray();

		int len = 0;
		for (int i = 0; i < chr.length; i++) {
			if (chr[i] >= 0xa1) {
				len += 2;
			} else {
				len++;
			}
		}

		if(len<=maxLength){
			if(len<minLength)
			{
				String newStr = str;
				for(int i=0;i<(minLength-len);i++) {
					newStr = newStr + " ";
				}
				return newStr;
			}else {
				return str;
			}
		}

		len = 0;
		for (int i = 0; i < chr.length; i++) {
			if (chr[i] >= 0xa1) {
				len += 2;
				if (len + suffixLen > maxLength) {
					break;
				}else {
					sbuffer.append(chr[i]);
				}
			} else {
				len++;
				if (len + suffixLen > maxLength) {
					break;
				}else {
					sbuffer.append(chr[i]);
				}
			}
		}

		sbuffer.append(suffix);
		return sbuffer.toString();
	}
}
