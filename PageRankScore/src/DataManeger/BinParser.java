package DataManeger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class BinParser {
    private static final int BYTES_SHORT = Short  .SIZE / Byte.SIZE;
    private static final int BYTES_INT   = Integer.SIZE / Byte.SIZE;
    private static final int BYTES_FLOAT = Float  .SIZE / Byte.SIZE;
    private static final int BYTES_PRIM = 4;

    private static final int MASK_8BIT  =   0xff;
    private static final int MASK_16BIT = 0xffff;

    private final byte[] readBuffer;

	private PushbackInputStream input;
	private int position;

    

    static{
        assert BYTES_PRIM >= BYTES_FLOAT;
        assert BYTES_PRIM >= BYTES_INT;
        assert BYTES_PRIM >= BYTES_SHORT;
    }
	public BinParser(InputStream is) {
		input = new PushbackInputStream(is, 1);
        this.readBuffer = new byte[BYTES_PRIM];

	}
	public byte[] parseByteArray(int off, int length){
		  int remain = 0;
		  byte[] dst = null;
		if(length != -1){
			remain = length;
		}else{
			
			try {
				remain = input.available();
				if(remain < off){
					return null;
				}
				dst = new byte[remain-off];
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        int offset = off;
        
        while(remain > 0){
            int txSize = 0;
			try {
				txSize = this.input.read(dst, offset, remain);
			} catch (IOException e) {
				e.printStackTrace();
			}
            if(txSize <= 0){
            }
            remain -= txSize;
            offset += txSize;
            this.position += txSize;
        }
        return dst;
	}
	 public void fillBuffer(int fillSize){
	        parseByteArray(0, fillSize);
	 }
}