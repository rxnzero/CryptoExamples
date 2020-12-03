/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.rte.fdl.cryptography.impl.aria;

public class AnsiX923Padding implements CryptoPadding {
	/** �е� ��Ģ �̸� */
	private String name = "ANSI-X.923-Padding";

	private static final byte PADDING_VALUE = 0x00;

	/**
	 * ��û�� Block Size�� ���߱� ���� Padding�� �߰��Ѵ�.
	 *
	 * @param source byte[] �е��� �߰��� bytes
	 * @param blockSize int block size
	 * @return byte[] �е��� �߰� �� ��� bytes
	 */
	public byte[] addPadding(byte[] source, int blockSize) {
		int paddingCnt = source.length % blockSize;
		byte[] paddingResult = null;

		if (paddingCnt != 0) {
			paddingResult = new byte[source.length + (blockSize - paddingCnt)];

			System.arraycopy(source, 0, paddingResult, 0, source.length);

			// �е��ؾ� �� ���� - 1 (�������� ����)���� 0x00 ���� �߰��Ѵ�.
			int addPaddingCnt = blockSize - paddingCnt;
			for (int i = 0; i < addPaddingCnt; i++) {
				paddingResult[source.length + i] = PADDING_VALUE;
			}

			// ������ �е� ���� �е� �� Count�� �߰��Ѵ�.
			paddingResult[paddingResult.length - 1] = (byte) addPaddingCnt;
		} else {
			paddingResult = source;
		}

		//print(paddingResult);

		return paddingResult;
	}

	/**
	 * ��û�� Block Size�� ���߱� ���� �߰� �� Padding�� �����Ѵ�.
	 *
	 * @param source byte[] �е��� ������ bytes
	 * @param blockSize int block size
	 * @return byte[] �е��� ���� �� ��� bytes
	 */
	public byte[] removePadding(byte[] source, int blockSize) {
		byte[] paddingResult = null;
		boolean isPadding = false;

		// �е� �� count�� ã�´�.
		int lastValue = source[source.length - 1];
		if (lastValue < (blockSize - 1)) {
			int zeroPaddingCount = lastValue - 1;

			for (int i = 2; i < (zeroPaddingCount + 2); i++) {
				if (source[source.length - i] != PADDING_VALUE) {
					isPadding = false;
					break;
				}
			}

			isPadding = true;
		} else {
			// ������ ���� block size ���� Ŭ ��� �е� �Ȱ��� ����.
			isPadding = false;
		}
		
		//padding length 1�� ���� ���� 2019.01.02 modify by jdh
		if((blockSize%lastValue) == 1) {
			isPadding = true;
		}else if (isPadding && lastValue <= 0) { // minus, 0 and ���� 1 skip(padding length +1�� ���� ���� )  2019.01.02 modify by jdh
			isPadding = false;
		}

		if (isPadding) {
			for (int index = source.length - lastValue; index < source.length - 1; index++) {
				if (source[index] != (byte) 0) {
					isPadding = false;
					break;
				}
			}
		}

		if (isPadding) {
			paddingResult = new byte[source.length - lastValue];
			//System.out.println("source.length = " + source.length + ", lastValue = " + lastValue);
			//print(source);
			try {
				System.arraycopy(source, 0, paddingResult, 0, paddingResult.length);
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.out.println("removePadding Exception.....");
				return source;
			}
		} else {
			paddingResult = source;
		}

		return paddingResult;
	}

	public String getName() {
		return name;
	}

	public void print(byte[] data) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("[").append(data.length).append("] ");

		for (int i = 0; i < data.length; i++) {
			buffer.append(data[i]).append(" ");
		}

		System.out.println(buffer.toString());
	}
}
