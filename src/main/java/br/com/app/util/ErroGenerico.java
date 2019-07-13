package br.com.app.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroGenerico {

	private String errorMessage;
	private String errorCode;

}
