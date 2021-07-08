package com.king.thread.completablefuture;

import org.springframework.util.StopWatch;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 可完成的任务-CompletableFuture             Runtime.getRuntime().availableProcessors()==cpu核数
 *                                          一般线程池大小设置NCPU*UCPU*(1+W/C)  NCPU:处理器核数  UCPU:期望的CPU利用率(介于0-1之间) W/C:等待时间与计算时间的比率
 *                                          一般设置规则：计算密集型：电脑核数     io密集型：电脑核数*2
 * 1.静态工厂方法-->  Async的都是异步任务         completedFuture获取CompletableFuture不是异步的，会等待获取到明确结果再返回
 *                  supplyAsync()异步处理任务，有返回值
 *                  runAsync()异步处理任务，无返回值
 *                  completedFuture()同步处理任务
 *                  allOf()等待所有的异步任务都执行完才会返回一个新的completableFuture
 *                  anyOf()任何一个任务执行完毕就会返回一个新的completableFuture
 *
 * 2.链式调用-->A任务执行完-->执行任务B-->B任务执行完-->执行任务C
 *      join() 和 get()的区别，前者为非检查，有异常进行包装抛出，get()为检查异常可以进行try-catch
 * Created by ljq on 2021-7-7 9:43
 */
public class TestCompletableFuture {

    public static void main(String[] args){
        TestCompletableFuture test = new TestCompletableFuture();
        test.test1();
        //test.test2();
        //test.testThenCompose();
        //test.testThenCombine();
        //test.whenComplete();
        //test.testBoth();
        //test.testEither();
        //test.customExecutor();
    }

    /**
     * 1，静态工厂方法
     */
    private void test1(){
        final CompletableFuture<String> f1 = CompletableFuture.supplyAsync(this::delay2);//异步处理任务有返回值
        final CompletableFuture<String> f11 = CompletableFuture.supplyAsync(this::delay2);//异步处理任务有返回值
        final CompletableFuture<Void> f2 = CompletableFuture.runAsync(this::delay);//异步处理任务无返回值
        final CompletableFuture<Integer> f3 = CompletableFuture.completedFuture(33);//创建一个已经有结果的completableFuture

        StopWatch watch = new StopWatch();
        watch.start("allOf");
        final CompletableFuture<Void> allOf = CompletableFuture.allOf(f1,f11, f2, f3);//allOf没有返回值，
        final Void join1 = allOf.thenRun(() -> System.out.println("allOf is over!")).join();
        System.out.println("allOf result:"+join1);
        watch.stop();

        //存储allOf中任务的返回结果
        CompletableFuture<List> future = CompletableFuture.allOf(f1, f11, f2, f3).thenApply((test) -> {
            //thenApply让future按保存的顺序执行
            List res = new ArrayList();
            try {
                res.add(f1.get());
                res.add(f11.get());
                res.add(f2.get());
                res.add(f3.get());

                //res.add(f1.join());
                //res.add(f11.join());
                //res.add(f2.join());
                //res.add(f3.join());
            } catch (Exception e) {
                throw new RuntimeException("执行错误");
            }
            return res;
        }).exceptionally(e -> new ArrayList<>());
        try {
            List list = future.get();
            System.out.println("有顺序的allOf");
            System.out.println(list.size());
            for (Object o : list) {
                System.out.println(o);
            }
            //可以存储不同的类型值，这里进行强转
            final int i4 = (int) list.get(3);
            System.out.println("强转的第4个元素，值为："+i4+"类型为"+list.get(3).getClass());
            System.out.println("有顺序的allOf over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        watch.start("anyOf");
        final CompletableFuture<Object> anyOf = CompletableFuture.anyOf(f1, f11, f2);//anyOf可以带返回值
        final Object join = anyOf.join();
        System.out.println("anyOf id over,result:"+join);
        watch.stop();
        final String x = watch.prettyPrint();
        System.out.println(x);


    }

    /**
     * 2.链式调用-->对处理结果进行再处理(执行其他逻辑)
     */
    private void test2(){
        final CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> {
            //测试抛出异常后，handle方法接收并进行处理
            int x = 1 / 0;
            return "first";
        }).handle((res, ex) -> {//对返回结果进行处理用BiFunction函数接口  可改变返回值
            if (Objects.nonNull(ex)) {//有错误信息
                System.out.println("handle ex" + ex.getCause().getMessage());
            }
            return Objects.nonNull(ex) ? "true" : "false";
        }).thenApply(res -> {//对结果执行一个Function函数接口处理  依赖上一个线程的结果处理并返回结果
            System.out.println("thenApply res = " + res);
            return Objects.equals("true", res) ? "success" : "error";
        }).thenAccept(res -> System.out.println("thenAccept res = " + res)//对结果执行一个Consumer消费型的函数接口  无返回结果
        ).thenRun(() -> System.out.println("没有参数，异步执行一个没有返回值的任务"));//执行一个Runnable接口  只是等前面任务完成时执行
        fu.join();
    }

    /**
     * 级联操作-->thenCompose:将一个操作的结果当作第二个操作的参数进行操作，返回一个新的future
     */
    private void testThenCompose(){
        final CompletableFuture<String> thenCompose = CompletableFuture.supplyAsync(() -> "f1 result").thenCompose(res -> {//res为f1的返回结果，作为f2的参数进行操作
            System.out.println("f1 = " + res);
            return CompletableFuture.supplyAsync(() -> "f2 result");
        });
        System.out.println("thenCompose result = "+thenCompose.join());
    }

    /**
     * 级联操作-->thenCombine：将两个完全无关的异步请求的结果整合起来，计算出一个新的值并返回
     *           第一个任务中有异常抛出时直接报异常信息
     */
    private void testThenCombine(){
        final CompletableFuture<String> thenCombine = CompletableFuture.supplyAsync(() -> "f1 result").thenCombine(CompletableFuture.supplyAsync(() -> 110), (str, num) -> {
            System.out.println("str = " + str + ", num = " + num);
            return str + num;
        });
        System.out.println(thenCombine.join());
    }

    /**
     * 级联操作-->thenCombine中添加  whenComplete：接收前面future的处理结果或异常，最后再返回原来的结果或抛出异常，类似于final的执行。
     */
    private void whenComplete(){
        final CompletableFuture<String> thenCombine = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("测试异常");
            }
            return "f1 result";
        }).whenComplete((res,ex)->{
            System.out.println("res = "+res);
            if (Objects.nonNull(ex)) {
                System.out.println(ex.getCause().getMessage());
            }
        })
        //        .exceptionally(ex->{//添加exceptionally方法后，会对前面的异常进行处理，并返回结果
        //    System.out.println(ex.getCause().getMessage());
        //    return "error";
        //})
                .thenCombine(CompletableFuture.supplyAsync(() -> 110), (str, num) -> {
            System.out.println("str = " + str + ", num = " + num);
            return str + num;
        });
        System.out.println(thenCombine.join());
    }

    /**
     * Both---->
     * thenAcceptBoth:等待当前的cf和另一个cf执行完成，将他们的返回结果作为入参去执行一个操作，没有返回值
     * runAfterBoth:等待当前cf和另一个cf执行完成，然后去执行一个操作，没有返回值
     */
    private void testBoth(){
        CompletableFuture<Integer> f1 = CompletableFuture.completedFuture(1111);
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::delay2);
        final CompletableFuture<Void> both1 = f1.thenAcceptBoth(f2, (num, str) -> {
            System.out.println("num = " + num + ",str = " + str);
        });
        both1.join();

        CompletableFuture<Void> both2 = f1.runAfterBoth(f2, () -> System.out.println("无参任务"));
        both2.join();
    }

    /**
     * Either---->
     * acceptEither:当前的cf和另一个cf 任意一个执行完成，将他的返回结果作为入参去执行一个操作，没有返回值  前提两个cf返回值类型一致
     * applyToEither:当前的cf和另一个cf 任意一个执行完成，将他的结果作为入参，使用mapping函数转换成一个新值返回
     * runAfterEither:当前的cf和另一个cf 任意一个执行完成，然后去执行一个操作，没有返回值
     */
    private void testEither(){
        CompletableFuture<String> f1 = CompletableFuture.completedFuture("22");
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::delay2);

        CompletableFuture<Void> either1 = f1.acceptEither(f2, System.out::println);
        either1.join();

        CompletableFuture<Integer> either2 = f1.applyToEither(f2, res -> {
            System.out.println("res = " + res);
            return res.length();
        });
        System.out.println(either2.join());

        CompletableFuture<Void> either3 = f1.runAfterEither(f2, () -> {
            System.out.println("无参方法执行");
        });
        either3.join();
    }

    /**
     * 使用自定义执行器(executor),相当于自定义的线程池来提高执行效率
     * CompletableFuture源码中使用ForkJoinPool线程池，默认核心线程数为机器核数-1，任务量大是会有大量线程等待，可以使用自定义线程池进行处理
     */
    private void customExecutor(){
        System.out.println("机器核心数："+Runtime.getRuntime().availableProcessors());//8
        List<String> list = Arrays.asList("111", "222", "333", "444", "555", "666", "777", "888", "999", "000", "1111", "1212", "1313", "1414", "1515", "1616", "1717", "1818");
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(list.size(), 100), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);//守护线程不会阻止程序终止
            return thread;
        });

        StopWatch watch = new StopWatch();
        watch.start("默认执行");
        List<CompletableFuture<String>> futures1 = list.stream().map(e -> {
            return CompletableFuture.supplyAsync(() -> {
                this.delay();
                return e;
            });
        }).collect(Collectors.toList());
        String result1 = futures1.stream().map(CompletableFuture::join).collect(Collectors.joining(",", "[", "]"));
        System.out.println(result1);
        watch.stop();

        watch.start("自定义executor执行");
        List<CompletableFuture<String>> futureList = list.stream().map(e -> {
            return CompletableFuture.supplyAsync(() -> {
                this.delay();
                return e;
            },executor);
        }).collect(Collectors.toList());
        String result2 = futureList.stream().map(CompletableFuture::join).collect(Collectors.joining(",", "[", "]"));
        System.out.println(result2);
        watch.stop();
        System.out.println(watch.prettyPrint());
    }

    /**
     * 延迟一秒无返回值，模拟业务执行逻辑
     */
    private void delay(){
        try {
            Thread.sleep(1000);
            System.out.println("执行延迟无返回值方法");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 延迟一秒有返回值，模拟业务执行逻辑
     */
    private String delay2(){
        try {
            Thread.sleep(1000);
            System.out.println("执行延迟有返回值方法");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "延迟一秒返回";
    }
}
