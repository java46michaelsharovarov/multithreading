package telran.net.test_example;

import java.io.Serializable;
import java.util.Arrays;

import telran.net.common.ApplProtocol;
import telran.net.common.Request;
import telran.net.common.Response;
import telran.net.common.ResponseCode;
import static telran.net.test_example.CalculatorApi.*;

public class CalculatorProtocolController implements ApplProtocol {
	
	CalculatorService calculatorService;
	
	public CalculatorProtocolController(CalculatorService calculator) {
		this.calculatorService = calculator;
	}
	
	@Override
	public Response handlRequest(Request request) {
		Response response;
		try {
			switch(request.requestType) {
			case CMD_ADD: response = add(getArguments(request.requestData)); break;
			case CMD_SUBTRACT: response = subtract(getArguments(request.requestData)); break;
			case CMD_DIVIDE: response = divide(getArguments(request.requestData)); break;
			case CMD_MULTIPLY: response = multiply(getArguments(request.requestData)); break;
			default: response = new Response(ResponseCode.WRONG_REQUEST_TYPE, request.requestType);
			}
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
		return response;
	}
	
	Response add(double [] operands) {
		double res = calculatorService.add(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}
	
	Response subtract(double [] operands) {
		double res = calculatorService.subtract(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}
	
	Response divide(double [] operands) {
		double res = calculatorService.divide(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}
	
	Response multiply(double [] operands) {
		double res = calculatorService.multiply(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}
	
	private double[] getArguments(Serializable requestData) throws Exception {
		try {
			double[] res = (double[]) requestData;
			System.out.println(Arrays.toString(res));
			if (res.length != 2) {
				throw new Exception("no two operands");
			}
			return res;
		} catch (ClassCastException e) {
			throw new Exception("no array of doubles");
		}		
	}

}