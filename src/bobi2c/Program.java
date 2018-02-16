package bobi2c;

import com.pi4j.io.i2c.*;
import com.pi4j.util.Console;

public class Program {

	private static int writeAddress = (byte)0x70;
	private static final byte TAKE_READING = (byte)0x51;
	private static final byte UNLOCK1 = (byte)0xaa;
	private static final byte UNLOCK2 = (byte)0xa5;
	
	
	public static void main(String[] args) {
		final Console console = new Console();
		byte[] buffer = new byte[2];
		
		try {
			I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
			I2CDevice device = i2c.getDevice(writeAddress);
			
			if (args.length > 0 && args[0] == "--set-address")
			{
				if (args.length < 2) {
					console.println("Please enter the address to set the device to.");
					return;
				}
				
				int newAddress = Integer.parseInt(args[1]);
				if (newAddress <= 0 || newAddress == 80 || newAddress == 164 || newAddress == 170 || newAddress > 255) {
					throw new Exception("Address '" + newAddress + "' is invalid.");
				}
				
				device.write(writeAddress, UNLOCK1);
				device.write(writeAddress, UNLOCK2);
				device.write(writeAddress, (byte)newAddress);
				return;
			}
			
			
			while(true) {
				try	{
					device.write(224, TAKE_READING);
					Thread.sleep(80);
					device.read(225, buffer, 0, 2);
					short msb = (short)(buffer[0] & 0x7F);
					short lsb = (short)(buffer[1] & 0xFF);
					console.println("MSB: " + String.format("0x%02x", buffer[0]) + " (int: " + msb + ")");
					console.println("LSB: " + String.format("0x%02x", buffer[1]) + " (int: " + lsb + ")");
					console.println("Bits: " + Integer.toBinaryString(msb) + " " + Integer.toBinaryString(lsb));
					int range = msb * 256 + lsb;
					console.println("Range: " + range + "cm (" + range / 2.54 + "in)");
					Thread.sleep(10);
				} catch (Exception ex) {
					console.println(ex.toString());
				}				
			}
		} catch (Exception ex) {
			console.println(ex.toString());
		}
	}

}
