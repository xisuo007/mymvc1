package com.king.java8.stream;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ljq on 2019/7/19 9:07
 */
public class TestStream {
    public static void main(String[] args) {
        TestStream test = new TestStream();
        test.test0();
        //test.test1();
        //test.test2();
        //test.test3();
        //test.test4();
        //test.test5();
        //test.test6();
        //test.test7();
    }

    public void test0(){
        /**
         * 流的创建
         * 1)Collection的默认方法stream()和parallelStream()  parallelStream()与stream()区别是parallelStream()使用多线程去并发遍历，而stream()是单线程
         * 2)Arrays.stream()
         * 3)Stream.of()
         * 4)Stream.iterate()//迭代无限流(1, n->n +1)
         * 5)Stream.generate()//生成无限流(Math::random)
         */

        //1.Collection的默认方法stream()和parallelStream()
        List<String> nullList = new ArrayList();
        List<String> list = Arrays.asList("a","b","c","d","e","f");
        Map<Boolean, List<String>> collect = nullList.stream().filter(in->in.getBytes() != null).collect(Collectors.partitioningBy(o -> list.contains(o)));
        List<String> strings = collect.get(true);
        List<String> strings1 = collect.get(false);
        System.out.println(strings);

        list.stream();//获取顺序流---单线程操作
        list.stream().map(s -> s.toUpperCase()).forEach(System.out::println);
        list.parallelStream();//多线程操作  但是它不是线程安全的
        list.parallelStream().forEach(System.out::println);//输出顺序不能保证

        //2.Arrays.stream()
        IntStream stream2 = Arrays.stream(new int[]{1, 23, 3, 4, 5, 6});
        Stream<Integer> stream22 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5});

        //3.Stream.of()
        Stream<Integer> stream3 = Stream.of(1, 2, 3, 4, 5, 6);
        IntStream stream33 = IntStream.of(1, 2, 3);
        System.out.println("-----3-------");
        Stream.of(list).forEach(System.out::println);

        //4.Stream.iterate()//迭代无限流
        Stream.iterate(1,n -> n+1).limit(10).forEach(System.out::println);

        //5.Stream.generate()//生成无限流
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    public void test1(){
        /**
         * 流的常用处理
         * 过滤------filter(Predicate<T> p)：过滤(根据传入的Lambda返回的ture/false 从流中过滤掉某些数据(筛选出某些数据))
         * 去重------distinct()：去重(根据流中数据的 hashCode和 equals去除重复元素)
         * 截取------limit(long n)：限定保留n个数据
         * 跳过------skip(long n)：跳过n个数据
         */
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 3, 4, 3, 6, 7, 8, 9, 11, 14);
        System.out.println("============过滤filter============");
        list1.stream().filter(i->i%2 ==0).forEach(System.out::println);
        System.out.println("============去重distinct============");
        list1.stream().distinct().forEach(System.out::println);
        System.out.println("============截取limit============");
        list1.stream().distinct().limit(3).forEach(System.out::println);
        System.out.println("============跳过skip============");
        list1.stream().distinct().skip(2).forEach(System.out::println);
    }


    public void test2(){
        /**
         * 映射
         * map(Function<T, R> f)：接收一个函数作为参数，该函数会被应用到流中的每个元素上，并将其映射成一个新的元素。
         * flatMap(Function<T, Stream<R>> mapper)：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
         */
        System.out.println("-------------test1--------------------");
        Stream<String> stream1 = Stream.of("i", "love", "java", "not", "really");
        stream1.map(s -> s.toUpperCase()).forEach(System.out::println);

        List<String> lists = Arrays.asList("i", "love", "java", "not", "really");
        lists.stream().map(s ->{
            int length = s.length();
            return length+2;
        }).collect(Collectors.toList()).forEach(System.out::println);

        Stream<List<String>> stream2 = Stream.of(Arrays.asList("H","E"),Arrays.asList("L","L","O"));
        stream2.flatMap(list -> list.stream()).forEach(System.out::println);
    }

    public void test3(){
        /**
         * 排序
         * sorted()：自然排序使用Comparable<T>的int compareTo(T o)方法
         * sorted(Comparator<T> com)：定制排序使用Comparator的int compare(T o1, T o2)方法
         * 统计
         * count():返回流中元素的总个数
         * max(Comparator<T>):返回流中最大值
         * min(Comparator<T>):返回流中最小值
         */
        System.out.println("============自然排序============");
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 3, 4, 3, 6, 7, 8, 9, 11, 14);
        list1.stream().sorted().forEach(System.out::println);
        System.out.println("============自定义排序============");
        list1.stream().sorted((x,y) -> y.compareTo(x)).forEach(System.out::println);
        System.out.println("============总数============");
        System.out.println(list1.stream().count());
        System.out.println("============最大值和最小值============");
        System.out.println(list1.stream().max((x,y)->x.compareTo(y)).get());
        System.out.println(list1.stream().min((x,y)->x.compareTo(y)).get());

    }

    public void test4(){
        /**
         * 收集流  查找匹配
         * allMatch:检查是否匹配所有元素
         * anyMatch:检查是否至少匹配一个元素
         * noneMatch:检查是否没有匹配的元素
         * findFirst:返回第一个元素(返回值为Optional<T>)
         * findAny:返回当前流中的任意元素(一般用于并行流)
         */
        System.out.println("======================检查是否匹配所有==========================");
        List<Integer> list1 = Arrays.asList(2, 3, 4, 5, 3, 4, 3, 6, 7, 8, 9, 11, 14);
        System.out.println(list1.stream().allMatch(x -> x > 0));
        System.out.println("======================检查是否至少匹配一个元素==========================");
        System.out.println(list1.stream().anyMatch(x -> x>9));
        System.out.println("======================检查是否没有匹配的元素==========================");
        System.out.println(list1.stream().noneMatch(x -> x>9));
        System.out.println(list1.stream().noneMatch(x -> x>19));
        System.out.println("======================返回第一个元素==========================");
        System.out.println(list1.stream().findFirst().get());
        System.out.println("======================返回当前流中的任意元素==========================");
        Optional<Integer> any = list1.stream().findAny();
        System.out.println(any.get());
    }

    public void test5(){
        /**
         * 归约
         * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) :将流中元素挨个结合起来，得到一个值。
         */
        System.out.println("===============reduce:将流中的元素反复结合起来，得到一个值===============");
        System.out.println(Stream.iterate(1,x->x+1).limit(100).reduce(0,(x,y)->x+y));
        System.out.println(Stream.iterate(1,x->x+1).limit(10).reduce(1,(x,y)->x*y));
    }

    public void test6(){
        /**
         * 汇总
         * collect(Collector<T, A, R>):将流转换为其他形式。
         * collect:将流转换为其他形式:list
         * collect:将流转换为其他形式:set
         * collect:将流转换为其他形式:TreeSet
         * collect:将流转换为其他形式:map
         * collect:将流转换为其他形式:sum
         * collect:将流转换为其他形式:avg
         * collect:将流转换为其他形式:max
         * collect:将流转换为其他形式:min
         */
        System.out.println("=====collect:将流转换为其他形式:list");
        Stream.of(1,2,3,4,5,6,7,8,9).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("=====collect:将流转换为其他形式:set");
        Stream.iterate(1,x->x+1).limit(10).collect(Collectors.toSet()).forEach(System.out::println);
        System.out.println("=====collect:将流转换为其他形式:TreeSet");
        Stream.generate(Math::random).limit(5).collect(Collectors.toCollection(TreeSet::new)).forEach(System.out::println);
        System.out.println("=====collect:将流转换为其他形式:map");//相当于第一个是key第二个是value一次类推如果数量不够会报错！
        Map<Integer, String> stringMap = Arrays.asList(3, 4, 5, 2, 2, 3, 4, 5, 6, 7, 8, 8, 9,10,11).stream().distinct().collect(Collectors.toMap(Integer::intValue, integer -> integer + 10 + ""));
        Iterator<Integer> iterator = stringMap.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            System.out.println(stringMap.get(iterator.next()));
        }
        System.out.println("=====collect:将流转换为其他形式:sum");
        Integer sum = Arrays.asList(3, 2, 5, 6, 7, 8, 9, 4, 3, 1, 2, 4).parallelStream().collect(Collectors.summingInt(Integer::intValue));
        System.out.println(sum);
        System.out.println("=====collect:将流转换为其他形式:avg");
        System.out.println(Stream.iterate(1,x->x+1).limit(100).collect(Collectors.averagingInt(Integer::intValue)));
        System.out.println("=====collect:将流转换为其他形式:max");
        List<Double> list = Stream.generate(Math::random).limit(10).sorted((x,y) -> x.compareTo(y)).collect(Collectors.toList());
        list.forEach(System.out::println);
        Optional<Double> max = list.stream().collect(Collectors.maxBy(Double::compareTo));
        System.out.println(max.get());
        System.out.println("=====collect:将流转换为其他形式:min");
        System.out.println(Stream.iterate(1,x -> x+1).limit(100).collect(Collectors.minBy((x,y) -> x.compareTo(y))).get());
    }

    public void test7(){
        /**
         * 分组和分区
         * Collectors.groupingBy()对元素做group操作。
         * Collectors.partitioningBy()对元素进行二分区操作
         */
        List<Product> lists = Arrays.asList(new Product(1,"苹果手机",5888.88,"手机"),
                                            new Product(2,"华为手机",6899.33,"手机"),
                                            new Product(3,"联想笔记本",9899.33,"电脑"),
                                            new Product(4,"机械键盘",499.33,"键盘"),
                                            new Product(5,"雷蛇鼠标",222.22,"鼠标"));
        //1.根据商品分类名称进行分组
        //2.根据商品价格范围多级分组
        //3.根据商品价格是否大于1000进行分区
        System.out.println("====================根据商品分类名称进行分组=====================");
        Map<String, List<Product>> map = lists.stream().collect(Collectors.groupingBy(Product::getDirName));
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("====================根据商品价格范围多级分组=====================");
        Map<Double, Map<String, List<Product>>> map1 = lists.stream().collect(Collectors.groupingBy(Product::getPrice, Collectors.groupingBy(p -> {
            if (p.getPrice() > 1000) {
                return "高级货";
            }else {
                return "便宜货";
            }
        })));
        System.out.println(map1);
        System.out.println("====================根据商品价格是否大于1000进行分区=====================");
        Map<Boolean, List<Product>> listMap = lists.stream().collect(Collectors.partitioningBy(p -> p.getPrice() > 1000));
        System.out.println(listMap);

    }

}

@Accessors(chain = true)
@Data
class Product{
    public int id;
    public String name;
    public double price;
    public String dirName;

    public Product(int id, String name, double price, String dirName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dirName = dirName;
    }
}
