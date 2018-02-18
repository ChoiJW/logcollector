package logcollector;

import org.apache.log4j.*;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class LogServer {

	//private final Logger logger = Logger.getLogger(LogServer.class);
	
	public static void main(String[] args) throws Exception {

		//LogServer logServer = new LogServer();
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			
			ServerBootstrap b = new ServerBootstrap();
			
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new LogHandler());
						}
					});

			ChannelFuture f = b.bind(4560).sync();
			f.channel().closeFuture().sync();
			
			//logServer.logger.debug("test log test log test log test log test log");
			
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}
}
