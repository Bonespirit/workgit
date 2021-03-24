package com.pang.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VerifyCode {
	private String code;
	private byte[] imgBytes;
	private long expireTime;
}
