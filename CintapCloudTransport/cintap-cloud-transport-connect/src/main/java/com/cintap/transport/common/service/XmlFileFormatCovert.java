package com.cintap.transport.common.service;

import java.io.InputStream;
import java.nio.ByteBuffer;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class XmlFileFormatCovert {

	public String validateFileDataAndRemoveBom(InputStream in, InputStream is) throws Exception {
		String data = null;
		byte[] bom = new byte[3];
		try {
			// read 3 bytes of a file.
			in.read(bom);
			// BOM encoded as ef bb bf
			String content = new String(Hex.encodeHex(bom));
			if ("efbbbf".equalsIgnoreCase(content)) {
				log.info("File is BOM file");
				byte[] bytes = IOUtils.toByteArray(is);
				ByteBuffer bb = ByteBuffer.wrap(bytes);
				// get the first 3 bytes
				bb.get(bom, 0, bom.length);
				// remaining
				byte[] contentAfterFirst3Bytes = new byte[bytes.length - 3];
				bb.get(contentAfterFirst3Bytes, 0, contentAfterFirst3Bytes.length);
				data = new String(contentAfterFirst3Bytes);
				/*if (data.contains("~P")) {
					data = data.replace("~P", "");
				}*/
			} else {
				data = new String(IOUtils.toByteArray(is));
				/*if (data.contains("~P")) {
					data = data.replace("~P", "");
				}*/
			}

		} catch (Exception e) {
			throw e;
		}
		return data;
	}

}
