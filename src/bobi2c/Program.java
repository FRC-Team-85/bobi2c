package bobi2c;

import com.pi4j.io.i2c.*;
import com.pi4j.util.Console;

public class Program {

	private static int address = (byte)0x70;
	
	
	private static final byte TAKE_READING = (byte)0x51;
	private static final int DATA_MSB = 225;
	private static final int DATA_LSB = 226;
	
	public static void main(String[] args) {
		final Console console = new Console();
		
		try {
			I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
			I2CDevice device = i2c.getDevice(address);
			while(true) {
				try	{
					device.write(224, TAKE_READING);
					Thread.sleep(100);
					int msb = device.read();
					int lsb = device.read();
					console.println("MSB = " + String.format("0x%02x", msb));
					console.println("LSB = " + String.format("0x%02x", lsb));
					Thread.sleep(50);
				} catch (Exception ex) {
					console.println(ex.toString());
				}				
			}
		} catch (Exception ex) {
			console.println(ex.toString());
		}
	}

}
