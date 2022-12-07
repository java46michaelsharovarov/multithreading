package telran.net.test_example;

import java.io.Closeable;
import java.io.IOException;
import static telran.net.test_example.CalculatorApi.*;

import telran.net.client.NetworkClient;

public class NetCalculatorProxy implements Closeable, CalculatorService {
	
	private NetworkClient networkHandler;

	public NetCalculatorProxy(NetworkClient networkHandler) {
		this.networkHandler = networkHandler;
	}
	
	@Override
	public double add(double op1, double op2) {
		return networkHandler.send(CMD_ADD, new double[]{op1, op2});
	}

	@Override
	public double subtract(double op1, double op2) {
		return networkHandler.send(CMD_SUBTRACT, new double[]{op1, op2});
	}

	@Override
	public double divide(double op1, double op2) {
		return networkHandler.send(CMD_DIVIDE, new double[]{op1, op2});
	}

	@Override
	public double multiply(double op1, double op2) {
		return networkHandler.send(CMD_MULTIPLY, new double[]{op1, op2});
	}

	@Override
	public void close() throws IOException {
		networkHandler.close();
	}

}
