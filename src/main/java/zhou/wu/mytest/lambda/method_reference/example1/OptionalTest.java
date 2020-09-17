package zhou.wu.mytest.lambda.method_reference.example1;

import java.util.Optional;

/**
 * 为什么使用这个
 * 1、使用Optional除了简化粗鲁的if(null == object)、降低函数的复杂度、增加可读性之外，它是一种傻瓜式的防护，Optional引导编码人员主动的思考引用为null的情况。
 * 2、轻率地使用null可能会导致很多令人惊愕的问题。
 * 3、此外，Null的含糊语义让人很不舒服。Null很少可以明确地表示某种语义，例如，Map.get(key)返回Null时，可能表示map中的值是null，亦或map中没有key对应的值。
 *  Null可以表示失败、成功或几乎任何情况。使用Null以外的特定值，会让你的逻辑描述变得更清晰。
 * 4、但相对于底层库来说，在应用级别的代码中，Null往往是导致混乱，疑难问题和模糊语义的元凶，就如同我们举过的Map.get(key)的例子
 * @author lin.xc
 * @date 2020/9/17
 *
 * 创建的三种方式
 * public static void testBase(){
 *     //one
 *     Optional<Integer> possible = Optional.fromNullable(5);  //创建允许null值的Optional
 *
 *     //two
 *     Integer nubmerone=4;
 *     Optional<Integer> integerOptional = Optional.of(nubmerone);//若引用为null则快速失败触发java.lang.NullPointerException
 *
 *     //three
 *     Optional<Integer> nullOptional=Optional.absent();//创建引用缺失的Optional实例,就是为NULL的
 * }
 **/
public class OptionalTest {
    public static void main(String[] args) {
        // Optional类已经成为Java 8类库的一部分，在Guava中早就有了，可能Oracle是直接拿来使用了
        // Optional用来解决空指针异常，使代码更加严谨，防止因为空指针NullPointerException对代码造成影响
        String msg = null;
        Optional<String> optional = Optional.ofNullable(msg);
        // 判断是否有值，不为空
        boolean present = optional.isPresent();
        System.out.println("该值是否不为空："+present);
        // 如果有值，则返回值，如果等于空则抛异常
//        String value = optional.get();
//        System.out.println("获取该值为："+value);
        // 如果为空，返回else指定的值
        String elseValue = optional.orElse("hi");
        System.out.println("若不为空，该值为原值；若为空，处理该值。结果为："+elseValue);
        // 如果值不为空，就执行Lambda表达式
        optional.ifPresent(opt -> System.out.println(opt));
    }
}

/*
// 1.8中Optional
public final class Optional<T> {
    private static final Optional<?> EMPTY = new Optional<>();

    private final T value;

    private Optional() {
        this.value = null;
    }

    // 返回一个空的 Optional实例
    public static<T> Optional<T> empty() {
        @SuppressWarnings("unchecked")
        Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }

    private Optional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    // 返回具有 Optional的当前非空值的Optional
    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    // 返回一个 Optional指定值的Optional，如果非空，则返回一个空的 Optional
    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    // 如果Optional中有一个值，返回值，否则抛出 NoSuchElementException 。
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    // 返回true如果存在值，否则为 false
    public boolean isPresent() {
        return value != null;
    }

    // 如果存在值，则使用该值调用指定的消费者，否则不执行任何操作。
    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null)
            consumer.accept(value);
    }

    // 如果一个值存在，并且该值给定的谓词相匹配时，返回一个 Optional描述的值，否则返回一个空的 Optional
    public Optional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(value) ? this : empty();
    }

    // 如果存在一个值，则应用提供的映射函数，如果结果不为空，则返回一个 Optional结果的 Optional 。
    public<U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Optional.ofNullable(mapper.apply(value));
        }
    }

    // 如果一个值存在，应用提供的 Optional映射函数给它，返回该结果，否则返回一个空的 Optional 。
    public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    // 如果值存在，就返回值，不存在就返回指定的其他值
    public T orElse(T other) {
        return value != null ? value : other;
    }


    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }
}
*/
