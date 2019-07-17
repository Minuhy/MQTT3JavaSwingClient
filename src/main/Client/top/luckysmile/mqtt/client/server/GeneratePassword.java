package top.luckysmile.mqtt.client.server;

import java.util.Random;

/**
 * �������һ��ֻ�����ֺ���ĸ���ַ���
 * @author ������
 *
 */
public class GeneratePassword {
	/**
	 * �����������
	 * @param length ����ĳ���
	 * @return �������ɵ�����
	 */
	public String get (int length) {
		// �������ɵ�����
		String password = "";
		Random random = new Random();
		for (int i = 0; i < length; i ++) {
			// �������0��1������ȷ���ǵ�ǰʹ�����ֻ�����ĸ (0��������֣�1�������ĸ)
			int charOrNum = random.nextInt(2);
			if (charOrNum == 1) {
				// �������0��1�������ж��Ǵ�д��ĸ����Сд��ĸ (0�����Сд��ĸ��1�������д��ĸ)
				int temp = random.nextInt(2) == 1 ? 65 : 97;
				password += (char) (random.nextInt(26) + temp);
			} else {
				// �����������
				password += random.nextInt(10);
			}
		}
		return password;
	}
}
