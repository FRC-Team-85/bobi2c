package bobi2c;

import com.pi4j.io.i2c.*;
import com.pi4j.util.Console;

public class Program {

	private static int address = (byte)0x70;
	
	private static final byte TAKE_READING = (byte)0x51;
	private static final byte DATA_MSB = (byte)0xE1;
	private static final byte DATA_LSB = (byte)0xE2;
	
	public static void main(String[] args) {
		final Console console = new Console();
		
		try {
			I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
			I2CDevice device = i2c.getDevice(address);
			//console.promptForExit();
			while(true) {
				device.write(TAKE_READING);
				Thread.sleep(100);
				int msb = device.read(DATA_MSB);
				int lsb = device.read(DATA_LSB);
				console.println("MSB = " + String.format("0x%02x", msb));
				console.println("LSB = " + String.format("0x%02x", lsb));
			}
		}
		catch (Exception ex) {
			console.println(ex.toString());
		}
	}

}
