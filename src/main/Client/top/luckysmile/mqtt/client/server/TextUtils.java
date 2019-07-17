package top.luckysmile.mqtt.client.server;


/**
 * 字符处理类，感觉随机生成，判断长度的也是这里的
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
 * 计算字符串真实长度，汉字2个，英文数字1个
 * @param value 要计算的字符串
 * @return 长度
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
	 * 用 java来截取中文英文字符串，过多的用省略号显示，中文占两个
	 * @param str 要处理的字符串
	 * @param maxLength 长度
	 * @return 处理后的字符串
	 * 互联网
	 */
	public static String subStringCN(final String str, final int maxLength, final int minLength) {
		if (str == null) {
			return str;
		}
		String suffix = "…";
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
