import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MyCollector<T, K> implements Collector<T, Map<K, List<T>>, Map<K, List<T>>> {
    private final Function<? super T, ? extends K> classifier;

    public MyCollector(Function<? super T, ? extends K> classifier) {
        this.classifier = classifier;
    }

    @Override
    public Supplier<Map<K, List<T>>> supplier() {
        //yangi akumlayator yaratiladi(bo'sh)
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<K, List<T>>, T> accumulator() {
        //elementlarni qanday qilib akumlyatorga saqlash kerakligini aniqlashtiramiz(Map)
        return (map, element) -> {
            K key = classifier.apply(element);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(element);
        };
    }

    @Override
    public BinaryOperator<Map<K, List<T>>> combiner() {
        //ikkita akumlyatorni qanday qilib birlashtirishni aniqlash
        return (map1, map2) -> {
            map2.forEach((key, list) -> {
                map1.merge(key, list, (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
            });
            return map1;
        };
    }

    @Override
    public Function<Map<K, List<T>>, Map<K, List<T>>> finisher(){
        //Shunchaki bu yerda akumlyatorni borligicha qaytaramiz
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics(){
        //Yakuniy tuzulmani qiymatini o'zgartirmasligimizni belgilaymiz
        return Collections.emptySet();
    }
//    public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(
//            Function<? super T, ? extends K> classifier,
//            Collector<? super T, A, D> downstream) {
//
//        // Возвращаем новый коллектор
//        return new Collector<T, Map<K, A>, Map<K, D>>() {
//
//            @Override
//            public Supplier<Map<K, A>> supplier() {
//                // Создаем новый Map для группировки
//                return HashMap::new;
//            }
//
//            @Override
//            public BiConsumer<Map<K, A>, T> accumulator() {
//                // Добавляем элементы в соответствующую группу
//                return (map, element) -> {
//                    K key = classifier.apply(element); // Получаем ключ для группировки
//                    A container = map.computeIfAbsent(key, k -> downstream.supplier().get());
//                    downstream.accumulator().accept(container, element);
//                };
//            }
//
//            @Override
//            public BinaryOperator<Map<K, A>> combiner() {
//                // Объединение двух частей в параллельной среде
//                return (map1, map2) -> {
//                    map2.forEach((key, value) -> {
//                        map1.merge(key, value, (container1, container2) -> {
//                            return downstream.combiner().apply(container1, container2);
//                        });
//                    });
//                    return map1;
//                };
//            }
//
//            @Override
//            public Function<Map<K, A>, Map<K, D>> finisher() {
//                // Применяем завершающую операцию для каждой группы
//                return intermediateMap -> {
//                    intermediateMap.replaceAll((key, acc) -> downstream.finisher().apply(acc));
//                    return (Map<K, D>) intermediateMap;
//                };
//            }
//
//            @Override
//            public Set<Characteristics> characteristics() {
//                return downstream.characteristics();
//            }
//        };
//    }
}
