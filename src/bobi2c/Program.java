package bobi2c;

import com.pi4j.io.i2c.*;
import com.pi4j.util.Console;

public class Program {

	private static int address = (byte)0x70;
	
	
	private static final byte TAKE_READING = (byte)0x51;
	private static final byte DATA_REGISTER = (byte)0x71;
	
	public static void main(String[] args) {
		final Console console = new Console();
		byte[] buffer = new byte[2];
		
		try {
			I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
			I2CDevice device = i2c.getDevice(address);
			while(true) {
				try	{
					device.write(TAKE_READING);
					Thread.sleep(80);
					device.read(DATA_REGISTER, buffer, 0, 2);
					short msb = (short)(buffer[0] & 0xFF);
					short lsb = (short)(buffer[1] & 0xFF);
					console.println("MSB = " + String.format("0x%02x", buffer[0]) + " (int: " + msb + ")");
					console.println("LSB = " + String.format("0x%02x", buffer[1]) + " (int: " + lsb + ")");
					int range = msb * 256 + lsb;
					console.println("Range = " + range);
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
