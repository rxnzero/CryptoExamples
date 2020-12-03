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

/**
 * ��ȣȭ���� �� ����� ���߱� ���� ���Ǵ� Padding�� �߻�ȭ �� Interface
 *
 * @author jinuk jung, junducki@naver.com
 * @version 1.0, 2008. 03. 11
 */
/*
 * History -2008. 03. 11, Create, jinuk jung
 */

public interface CryptoPadding {

    /**
     * ��û�� Block Size�� ���߱� ���� Padding�� �߰��Ѵ�.
     * 
     * @param source byte[] �е��� �߰��� bytes
     * @param blockSize int block size
     * @return byte[] �е��� �߰� �� ��� bytes
     */
    public byte[] addPadding(byte[] source, int blockSize);

    /**
     * ��û�� Block Size�� ���߱� ���� �߰� �� Padding�� �����Ѵ�.
     * 
     * @param source byte[] �е��� ������ bytes
     * @param blockSize int block size
     * @return byte[] �е��� ���� �� ��� bytes
     */
    public byte[] removePadding(byte[] source, int blockSize);

}
