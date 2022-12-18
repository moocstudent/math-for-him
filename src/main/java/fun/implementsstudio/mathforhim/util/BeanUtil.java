package fun.implementsstudio.mathforhim.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BeanUtil {

    /**
     * 拷贝非空值属性
     * @param src 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object src,Object target){
        Optional.ofNullable(src)
                .ifPresent(item-> BeanUtils.copyProperties(item,target,getNullPropertiesNames(item)));
    }

    /**
     * 属性差集
     * @param newObject
     * @param oldObject
     * @return
     */
    public static String diffProperties(Object newObject,Object oldObject){
        Map<String,String> resultMap = new HashMap<>(16);
        Arrays.stream(newObject.getClass().getDeclaredFields()).forEach(newField->{
            newField.setAccessible(true);
            Object newValue = null;
            try {
                newValue = newField.get(newObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Object finalNewValue  = newValue;
            Arrays.stream(oldObject.getClass().getDeclaredFields()).filter(
                    oldField->newField.getName().equals(oldField.getName())
            ).forEach(oldField->{
                oldField.setAccessible(true);
                Object oldValue = null;
                try {
                    oldValue = oldField.get(oldObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (oldValue!=null && finalNewValue!=null
                && !oldValue.equals(finalNewValue)){
                    resultMap.put(oldField.getName(),
                            oldValue.toString()+"->"+finalNewValue.toString());
                }
            });
        });
        return resultMap.toString();
    }

    /**
     * 赋值
     * @param tClass 类
     * @param e 对象
     * @param <T> 类泛型
     * @param <E> 对象泛型
     * @return <T,E> T
     */
    public static <T,E> Optional<T> getClass(Class<T> tClass,E e){
        return Optional.ofNullable(e).map(item->{
            T temp = null;
            try {
                temp = tClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException |
                    InvocationTargetException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }
            copyProperties(item,temp);
            return temp;
        });
    }

    /**
     * 获取赋值列表
     * @param tClass
     * @param es
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T,E> List<T> getClass(Class<T> tClass,List<E> es){
        return Optional.ofNullable(es).orElse(new ArrayList<>()).stream()
                .filter(e->getClass(tClass,e).isPresent())
                .map(e->getClass(tClass,e).orElse(null)).collect(Collectors.toList());
    }

    /**
     * 获取对象里的空值属性
     * @param source
     * @return
     */
    private static String[] getNullPropertiesNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays.stream(src.getPropertyDescriptors())
                .filter(pd->src.getPropertyValue(pd.getName())==null)
                .map(PropertyDescriptor::getName).distinct().toArray(String[]::new);
    }
}
