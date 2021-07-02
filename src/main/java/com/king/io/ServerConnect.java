package com.king.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by ljq on 2019/9/12 11:37
 */
public class ServerConnect {
    private static final int BUF_SIZE=1024;
    private static final int PORT=8080;
    private static final int TIMEOUT=3000;
    public static void main(String[] args){
        selector();
    }
    public static void handlerAccept(SelectionKey key) throws IOException {
        //SelectionKey.channel()方法返回的通道需要转型成你要处理的类型，如ServerSocketChannel或SocketChannel等
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
        //监听新进来的连接
        SocketChannel sc = ssChannel.accept();
        sc.configureBlocking(false);
        //调用register()会返回一个SelectionKey对象，这个对象代表注册到该Selector的通道
        sc.register(key.selector(),SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }
    public static void handlerRead(SelectionKey key) throws IOException{
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        int read = sc.read(buf);
        while (read > 0) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            System.out.println();
            buf.clear();
            read = sc.read(buf);
        }
        if (read == -1) {
            sc.close();
        }
    }
    public static void handlerWrite(SelectionKey key) throws IOException{
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while (buf.hasRemaining()) {
            sc.write(buf);
        }
        buf.compact();
    }
    public static void selector(){
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try {
            selector = Selector.open();
            //打开ServerSocketChannel
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            ssc.register(selector,SelectionKey.OP_ACCEPT);

            while (true) {
                //有返回值证明有通道就绪了
                if (selector.select(TIMEOUT) == 0) {
                    System.out.println("==");
                    continue;
                }
                //调用selectedKeys()返回已就绪键集中就绪的通道
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        //监听新进来的连接
                        handlerAccept(key);
                    }
                    if (key.isReadable()) {
                        handlerRead(key);
                    }
                    if (key.isWritable() && key.isValid()) {
                        handlerWrite(key);
                    }
                    if (key.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }
                    //每次迭代需要手动移除SelectionKey实例
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (ssc != null) {
                    ssc.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
