package com.wzc.tencent.task;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class IpUtil {
	public static String getAdd(String ip) {
		String urlstr = "http://whois.pconline.com.cn/ip.jsp?ip=" + ip;
		return ApiUtil.get(urlstr);
	}

	public static String getIp() {
		String uri = "http://www.3322.org/dyndns/getip";
		String ipAddress = "";
		ipAddress = ApiUtil.get(uri);
		int start = ipAddress.indexOf("[");
		int end = ipAddress.indexOf("]");
		if (start > -1 && end > -1 && end > start) {
			ipAddress = ipAddress.substring(start + 1, end);
		}
		return ipAddress;
	}

	public static Boolean openComputer(String ip, String macAddress) throws IOException {
		int port = 20105;
		// 检测 mac 地址,并将其转换为二进制
		byte[] destMac = getMacBytes(macAddress);
		if (destMac == null) {
			return false;
		}
		InetAddress destHost = InetAddress.getByName(ip);
		// construct packet data
		byte[] magicBytes = new byte[102];
		// 将数据包的前6位放入0xFF即 "FF"的二进制
		for (int i = 0; i < 6; i++) {
			magicBytes[i] = (byte) 0xFF;
		}

		// 从第7个位置开始把mac地址放入16次
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < destMac.length; j++) {
				magicBytes[6 + destMac.length * i + j] = destMac[j];
			}
		}
		// create packet
		DatagramPacket dp = new DatagramPacket(magicBytes, magicBytes.length, destHost, port);
		DatagramSocket ds = new DatagramSocket();
		ds.send(dp);
		ds.close();
		return true;
	}

	public static Boolean closeComputer(String ip) throws IOException {
		String command = "shutdown -m \\" + ip + " -f -s -t 0";
		Runtime r = Runtime.getRuntime();
		r.exec(command);
		return true;
	}

	private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
		byte[] bytes = new byte[6];
		String[] hex = macStr.split("(\\:|\\-)");
		if (hex.length != 6) {
			throw new IllegalArgumentException("Invalid MAC address.");
		}
		try {
			for (int i = 0; i < 6; i++) {
				bytes[i] = (byte) Integer.parseInt(hex[i], 16);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid hex digit in MAC address.");
		}
		return bytes;
	}
}
