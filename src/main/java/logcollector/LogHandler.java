package logcollector;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.bytes.ByteArrayDecoder;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import java.io.*;

public class LogHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 2. 데이터 순신 이벤트 처리 메서드. 클라이언트로부터 데이터의 수신이 이루어졌을때 네티가 자동으로 호출하는 이벤트 메소드

		// String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());

		ByteBuf buf = (ByteBuf) msg;

		byte[] bytes;
		int offset;
		int length = buf.readableBytes();

		if (buf.hasArray()) {
			bytes = buf.array();
			offset = buf.arrayOffset();

		} else {
			bytes = new byte[length];
			buf.getBytes(buf.readerIndex(), bytes);
			offset = 0;
		}

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(bis);
			LoggingEvent aaa = (LoggingEvent) ois.readObject();

			System.out.println(aaa.getMessage().toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}

				if (ois != null) {
					ois.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// System.out.println(readMessage);

		// String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());

		// String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
		// 3. 수신된 데이터를 가지고 있는 네티의 바이트 버퍼 객체로 부터 문자열 객체를 읽어온다.
		// System.out.println("수신한 문자열 ["+readMessage +"]");

		// ctx.write(msg);
		// 4.ctx는 채널 파이프라인에 대한 이벤트를 처리한다. 여기서는 서버에 연결된 클라이언트 채널로 입력받은 데이터를 그대로 전송한다.

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		// 6. channelRead 이벤트의 처리가 완료된 후 자동으로 수행되는 이벤트 메서드

		//ctx.flush();
		// 7. 채널 파이프 라인에 저장된 버퍼를 전송
		
		//ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
